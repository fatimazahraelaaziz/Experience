package fr.unice.scale.latencyaware.producer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.kafka.clients.producer.ProducerRecord;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class BiasedWorkload {
    static float ArrivalRate;

    public static void startWorkload() throws IOException, URISyntaxException, InterruptedException {
        final Logger log = LogManager.getLogger(NonUniformWorkload.class);
        Workload wrld = new Workload();

        Random rnd = new Random();
        List<Integer> partitionWeights = ConfigLoader.loadPartitionWeights();
        int totalWeight = partitionWeights.stream().mapToInt(Integer::intValue).sum();

        // Map to keep track of the number of messages sent to each partition
        Map<Integer, Long> partitionMessageCounts = new HashMap<>();

                // Initialize the count map
        for (int i = 0; i < partitionWeights.size(); i++) {
                    partitionMessageCounts.put(i, 0L);
        }
        
        for (int i = 0; i < wrld.getDatax().size(); i++) {
            log.info("sending a batch of authorizations of size:{}",
                    Math.ceil(wrld.getDatay().get(i)));
            ArrivalRate = (float) Math.ceil(wrld.getDatay().get(i));

            // Calcul du nombre total de messages à envoyer
            long totalMessages = Math.round(ArrivalRate);
            
            
            for (int partitionIndex = 0; partitionIndex < partitionWeights.size() ; partitionIndex++) {
                int partition = partitionIndex;
                int weight = partitionWeights.get(partitionIndex);
                long messagesPerPartition = totalMessages * weight / totalWeight;

 
                for (long j = 0; j < messagesPerPartition; j++) {
                    Customer custm = new Customer(rnd.nextInt(), UUID.randomUUID().toString());
                    KafkaProducerExample.producer.send(new ProducerRecord<String, Customer>(KafkaProducerExample.config.getTopic(),
                            partition, null, UUID.randomUUID().toString(), custm));
                    partitionMessageCounts.put(partition, partitionMessageCounts.get(partition) + 1);
                }

                log.info("sent {} messages to partition {}", messagesPerPartition, partition);

            }

                        
            // Envoi des messages restants pour équilibrer
            long remainingMessages = totalMessages % totalWeight;
            for (long j = 0; j < remainingMessages; j++) {
                int partition = (int) (j % partitionWeights.size());
                Customer custm = new Customer(rnd.nextInt(), UUID.randomUUID().toString());
                KafkaProducerExample.producer.send(new ProducerRecord<String, Customer>(KafkaProducerExample.config.getTopic(),
                        partition, null, UUID.randomUUID().toString(), custm));
                partitionMessageCounts.put(partition, partitionMessageCounts.get(partition) + 1);
                log.info("sent 1 remaining message to partition {}", partition);
            }

            log.info("sent {} events Per Second ", Math.ceil(wrld.getDatay().get(i)));
            Thread.sleep(KafkaProducerExample.config.getDelay());
        }

                // Log the total number of messages sent to each partition
                log.info("Total number of messages sent to each partition:");
                for (Map.Entry<Integer, Long> entry : partitionMessageCounts.entrySet()) {
                    log.info("Partition {}: {} messages", entry.getKey(), entry.getValue());
                }
    

    }
}
