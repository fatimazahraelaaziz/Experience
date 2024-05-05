import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.UUID;

public class OldWorkloadSkewed {
    static float ArrivalRate;
    public static void  startWorkload() throws IOException, URISyntaxException, InterruptedException {

        final Logger log = LogManager.getLogger(OldWorkloadSkewed.class);
        Workload wrld = new Workload();
        double first3Partitions = 0.0;
        double remaining6Partitions = 0.0;

        Random rnd = new Random();
        // over all the workload
        for (int i = 0; i < wrld.getDatax().size(); i++) {
            log.info("sending a batch of authorizations of size:{}",
                    Math.ceil(wrld.getDatay().get(i)));
            ArrivalRate = (float) Math.ceil(wrld.getDatay().get(i));

            first3Partitions = ArrivalRate * 0.5;
            remaining6Partitions = ArrivalRate * 0.5;

            //   sendToFirst 3 partitions
            for (long j = 0; j < first3Partitions/2.0; j++) {
                Customer custm = new Customer(rnd.nextInt(), UUID.randomUUID().toString());
               KafkaProducerExample.
                       producer.send(new ProducerRecord<String, Customer>(KafkaProducerExample.config.getTopic(),
                        0, null, UUID.randomUUID().toString(), custm));
                KafkaProducerExample.
                        producer.send(new ProducerRecord<String, Customer>(KafkaProducerExample.config.getTopic(),
                                1, null, UUID.randomUUID().toString(), custm));
            }

            //   sendTo remaining partitions
            for (long j = 0; j <remaining6Partitions /7.0; j++) {

                Customer custm = new Customer(rnd.nextInt(), UUID.randomUUID().toString());
                KafkaProducerExample.
                        producer.send(new ProducerRecord<String, Customer>(KafkaProducerExample.config.getTopic(),
                                2, null, UUID.randomUUID().toString(), custm));

                KafkaProducerExample.
                        producer.send(new ProducerRecord<String, Customer>(KafkaProducerExample.config.getTopic(),
                                3, null, UUID.randomUUID().toString(), custm));
                KafkaProducerExample.
                        producer.send(new ProducerRecord<String, Customer>(KafkaProducerExample.config.getTopic(),
                                4, null, UUID.randomUUID().toString(), custm));
                KafkaProducerExample.
                        producer.send(new ProducerRecord<String, Customer>(KafkaProducerExample.config.getTopic(),
                                5, null, UUID.randomUUID().toString(), custm));

                KafkaProducerExample.
                        producer.send(new ProducerRecord<String, Customer>(KafkaProducerExample.config.getTopic(),
                                6, null, UUID.randomUUID().toString(), custm));
                KafkaProducerExample.
                        producer.send(new ProducerRecord<String, Customer>(KafkaProducerExample.config.getTopic(),
                                7, null, UUID.randomUUID().toString(), custm));

                KafkaProducerExample.
                        producer.send(new ProducerRecord<String, Customer>(KafkaProducerExample.config.getTopic(),
                                8, null, UUID.randomUUID().toString(), custm));

            }
            log.info("sent {} events Per Second ", Math.ceil(wrld.getDatay().get(i)));
            Thread.sleep(KafkaProducerExample.config.getDelay());
        }
    }

}
