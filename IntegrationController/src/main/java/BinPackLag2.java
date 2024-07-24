
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class BinPackLag2 {
    //TODO give fup and fdown as paramters to the functions.

    private static final Logger log = LogManager.getLogger(BinPackLag2.class);

    static Instant LastUpScaleDecision = Instant.now();
    
    static double wsla = Double.valueOf(System.getenv("WSLA"));

    static double rebTime= Double.valueOf(System.getenv("REB_TIME"));

    static List<Consumer> assignment = new ArrayList<Consumer>();
    static List<Consumer> currentAssignment = assignment;

    private static KafkaConsumer<byte[], byte[]> metadataConsumer;

    static double mu = Double.valueOf(System.getenv("MU"));

    static {
        float fup = Float.valueOf(System.getenv("FUP"));
        currentAssignment.add(new Consumer("0", (long) (mu * wsla * fup),
                mu * fup));
        for (Partition p : ArrivalProducer.topicpartitions) {
            currentAssignment.get(0).assignPartition(p);
        }
    }


    public static  void scaleAsPerBinPack() {

        log.info("Currently we have this number of consumers group {} {}","testgroup1", BinPackState2.size );

        for (int i = 0; i < 5; i++) {
            ArrivalProducer.topicpartitions.get(i).setLag(ArrivalProducer.topicpartitions.get(i).getLag()
                    + (long) ((ArrivalProducer.totalArrivalrate * rebTime)/5.0));
        }

        if (BinPackState2.action.equals("up") || BinPackState2.action.equals("REASS")) {
            int neededsize = binPackAndScale();
            log.info("We currently need the following consumers for group1 (as per the bin pack) {}", neededsize);
            int replicasForscale = neededsize - BinPackState2.size;
            if (replicasForscale > 0) {
                //TODO IF and Else IF can be in the same logic
                log.info("We have to upscale  group1 by {}", replicasForscale);
                BinPackState2.size = neededsize;
                LastUpScaleDecision = Instant.now();
                currentAssignment = assignment;
                try (final KubernetesClient k8s = new KubernetesClientBuilder().build() ) {
                k8s.apps().deployments().inNamespace("default").withName("latency").scale(neededsize);
                log.info("I have Upscaled group {} you should have {}", "testgroup1", neededsize);
            }
            } else if (replicasForscale == 0) {
                if (metadataConsumer == null) {
                    KafkaConsumerConfig config = KafkaConsumerConfig.fromEnv();
                    Properties props = KafkaConsumerConfig.createProperties(config);
                    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                            "org.apache.kafka.common.serialization.StringDeserializer");
                    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                            "org.apache.kafka.common.serialization.StringDeserializer");
                    metadataConsumer = new KafkaConsumer<>(props);
                    metadataConsumer.enforceRebalance();
                }
                currentAssignment = assignment;
                metadataConsumer.enforceRebalance();
            }
        } else if (BinPackState2.action.equals("down")) {
            int neededsized = binPackAndScaled();
            int replicasForscaled =  BinPackState2.size - neededsized;
            if (replicasForscaled <  BinPackState2.size ) {
                log.info("We have to downscale  group by {} {}", "testgroup1", replicasForscaled);
                BinPackState2.size = neededsized;
                LastUpScaleDecision = Instant.now();
                try (final KubernetesClient k8s = new KubernetesClientBuilder().build()) {
                    k8s.apps().deployments().inNamespace("default").withName("latency").scale(neededsized);
                    log.info("I have downscaled group {} you should have {}", "testgroup1", neededsized);
                }
                currentAssignment = assignment;
            }
        }
        log.info("===================================");
    }


    private  static int binPackAndScale() {
        log.info(" shall we upscale group {}", "testgroup1");
        List<Consumer> consumers = new ArrayList<>();
        int consumerCount = 1;
        List<Partition> parts = new ArrayList<>(ArrivalProducer.topicpartitions);

        float fup = Float.valueOf(System.getenv("FUP"));

        for (Partition partition : parts) {
            if (partition.getLag() > mu*wsla*fup) {
                log.info("Since partition {} has lag {} higher than consumer capacity times wsla {}" +
                        " we are truncating its lag", partition.getId(), partition.getLag(), mu*wsla*fup);
                partition.setLag((long)(mu*wsla*fup));
            }
        }
        //if a certain partition has an arrival rate  higher than R  set its arrival rate  to R
        //that should not happen in a well partionned topic
        for (Partition partition : parts) {
            if (partition.getArrivalRate() > mu*fup) {
                log.info("Since partition {} has arrival rate {} higher than consumer service rate {}" +
                                " we are truncating its arrival rate", partition.getId(),
                        String.format("%.2f", partition.getArrivalRate()),
                        String.format("%.2f", mu*fup ));
                partition.setArrivalRate(mu*fup );
            }
        }
        //start the bin pack FFD with sort
        Collections.sort(parts, Collections.reverseOrder());

        while (true) {
            int j;
            consumers.clear();
            for (int t = 0; t < consumerCount; t++) {
                consumers.add(new Consumer((String.valueOf(t)),  (long)(200*wsla*fup),
                        200*fup/*dynamicAverageMaxConsumptionRate*wsla*/));
            }

            for (j = 0; j < parts.size(); j++) {
                int i;
                Collections.sort(consumers, Collections.reverseOrder());
                for (i = 0; i < consumerCount; i++) {
                    if (consumers.get(i).getRemainingLagCapacity() >= parts.get(j).getLag() &&
                            consumers.get(i).getRemainingArrivalCapacity() >= parts.get(j).getArrivalRate()) {
                        consumers.get(i).assignPartition(parts.get(j));
                        break;
                    }
                }
                if (i == consumerCount) {
                    consumerCount++;
                    break;
                }
            }
            if (j == parts.size())
                break;
        }
        log.info(" The BP up scaler recommended for group {} {}", "testgroup1", consumers.size());
        return consumers.size();
    }

    private static  int binPackAndScaled() {
        log.info(" shall we down scale group {} ", "testgroup1");
        List<Consumer> consumers = new ArrayList<>();
        int consumerCount = 1;
        float fdown = Float.valueOf(System.getenv("FDOWN"));
        List<Partition> parts = new ArrayList<>(ArrivalProducer.topicpartitions);
        double fractiondynamicAverageMaxConsumptionRate = mu*fdown;
        for (Partition partition : parts) {
            if (partition.getLag() > fractiondynamicAverageMaxConsumptionRate*wsla) {
                log.info("Since partition {} has lag {} higher than consumer capacity times wsla {}" +
                                " we are truncating its lag", partition.getId(), partition.getLag(),
                        fractiondynamicAverageMaxConsumptionRate*wsla);
                partition.setLag((long)(fractiondynamicAverageMaxConsumptionRate *wsla));
            }
        }

        //if a certain partition has an arrival rate  higher than R  set its arrival rate  to R
        //that should not happen in a well partionned topic
        for (Partition partition : parts) {
            if (partition.getArrivalRate() > fractiondynamicAverageMaxConsumptionRate) {
                log.info("Since partition {} has arrival rate {} higher than consumer service rate {}" +
                                " we are truncating its arrival rate", partition.getId(),
                        String.format("%.2f", partition.getArrivalRate()),
                        String.format("%.2f", fractiondynamicAverageMaxConsumptionRate));
                partition.setArrivalRate(fractiondynamicAverageMaxConsumptionRate);
            }
        }
        //start the bin pack FFD with sort
        Collections.sort(parts, Collections.reverseOrder());
        while (true) {
            int j;
            consumers.clear();
            for (int t = 0; t < consumerCount; t++) {
                consumers.add(new Consumer((String.valueOf(consumerCount)),
                        (long)(fractiondynamicAverageMaxConsumptionRate*wsla),
                        fractiondynamicAverageMaxConsumptionRate));
            }

            for (j = 0; j < parts.size(); j++) {
                int i;
                Collections.sort(consumers, Collections.reverseOrder());
                for (i = 0; i < consumerCount; i++) {

                    if (consumers.get(i).getRemainingLagCapacity() >= parts.get(j).getLag() &&
                            consumers.get(i).getRemainingArrivalCapacity() >= parts.get(j).getArrivalRate()) {
                        consumers.get(i).assignPartition(parts.get(j));
                        break;
                    }
                }
                if (i == consumerCount) {
                    consumerCount++;
                    break;
                }
            }
            if (j == parts.size())
                break;
        }
        log.info(" The BP down scaler recommended  for group {} {}", "testgroup1", consumers.size());
        return consumers.size();
    }



   /* private static  boolean assignmentViolatesTheSLA() {
        for (Consumer cons : currentAssignment) {
            if (cons.getRemainingLagCapacity() <  (long) (wsla*200*.9f)||
                    cons.getRemainingArrivalCapacity() < 200f*0.9f){
                return true;
            }
        }
        return false;
    }*/


   /* private static boolean assignmentViolatesTheSLA2() {

        List<Partition> partsReset = new ArrayList<>(ArrivalProducer.topicpartitions);
        for (Consumer cons : currentAssignment) {
            double sumPartitionsArrival = 0;
            double sumPartitionsLag = 0;
            for (Partition p : cons.getAssignedPartitions()) {
                sumPartitionsArrival += partsReset.get(p.getId()).getArrivalRate();
                sumPartitionsLag += partsReset.get(p.getId()).getLag();
            }

            if (sumPartitionsLag  > ( wsla * 200  * .9f)
                    || sumPartitionsArrival > 200* 0.9f) {
                return true;
            }
        }
        return false;
    }*/


}
