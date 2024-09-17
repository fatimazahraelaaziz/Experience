package fr.unice.scale.latencyaware.controller;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ExecutionException;
import fr.unice.scale.latencyaware.controller.Partition;


public class Lag {
    private static final Logger log = LogManager.getLogger(Lag.class);
    public static String CONSUMER_GROUP;
    public static AdminClient admin = null;
    static String topic;
    static String BOOTSTRAP_SERVERS;
    static Map<TopicPartition, OffsetAndMetadata> committedOffsets;
    static long totalLag;
    //////////////////////////////////////////////////////////////////////////////
    static ArrayList<Partition> partitions = new ArrayList<>();


    static Map<String, ConsumerGroupDescription> consumerGroupDescriptionMap;


    public  static void readEnvAndCrateAdminClient() throws ExecutionException, InterruptedException {
        topic = "testtopic1";
        CONSUMER_GROUP = "testgroup1";
        BOOTSTRAP_SERVERS = System.getenv("BOOTSTRAP_SERVERS");
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        admin = AdminClient.create(props);
        for (int i = 0; i < 5; i++) {
            //ArrivalProducer.topicpartitions.get(i).setLag(0L);
            Partition p = new Partition(i,0L,0.0);
            partitions.add(p);
        }
    }


    public static void getCommittedLatestOffsetsAndLag() throws ExecutionException, InterruptedException {
        committedOffsets = admin.listConsumerGroupOffsets(CONSUMER_GROUP)
                .partitionsToOffsetAndMetadata().get();
        Map<TopicPartition, OffsetSpec> requestLatestOffsets = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            requestLatestOffsets.put(new TopicPartition(topic, i), OffsetSpec.latest());
        }
        Map<TopicPartition, ListOffsetsResult.ListOffsetsResultInfo> latestOffsets =
                admin.listOffsets(requestLatestOffsets).all().get();
         totalLag=0L;
         
         for (int i = 0; i < 5; i++) {
            TopicPartition t = new TopicPartition(topic, i);
            Long latestOffset = latestOffsets.get(t) != null ? latestOffsets.get(t).offset() : 0L;
            Long committedOffset = committedOffsets.get(t) != null ? committedOffsets.get(t).offset() : 0L;
            partitions.get(i).setLag(latestOffset - committedOffset);
            ArrivalProducer.topicpartitions.get(i).setLag(latestOffset - committedOffset);
            totalLag += partitions.get(i).getLag();
            log.info("partition {} has lag {}", i, partitions.get(i).getLag());
        }
        
        //addParentLag(totalLag);
        log.info("total lag {}", totalLag);
    }


   /*static  void addParentLag (long totalLag) {

        double childarrivalrate = totalLag/0.5;
        log.info("after adding parent lag : Arrival rate into testtopic2");
        double totalArrivalRate = childarrivalrate + ArrivalProducer.totalArrivalrate;
       for (int i = 0; i < 5; i++) {
           ArrivalProducer.topicpartitions2.get(i).setArrivalRate(totalArrivalRate/5.0);

       }

    }*/


   public  static int queryConsumerGroup() throws ExecutionException, InterruptedException {
        DescribeConsumerGroupsResult describeConsumerGroupsResult =
                admin.describeConsumerGroups(Collections.singletonList("testgroup1"));
        KafkaFuture<Map<String, ConsumerGroupDescription>> futureOfDescribeConsumerGroupsResult =
                describeConsumerGroupsResult.all();

        consumerGroupDescriptionMap = futureOfDescribeConsumerGroupsResult.get();


        int members = consumerGroupDescriptionMap.get("testgroup1").members().size();

        log.info("consumers nb as per kafka {}", members );

        return members;


    }

}
