
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;

public class ConstantWorkload {
    static float ArrivalRate;

    static Instant start = Instant.now();
    public static void  startWorkload() throws IOException, URISyntaxException, InterruptedException {

        final Logger log = LogManager.getLogger(OldWorkload.class);

        Random rnd = new Random();
        // over all the workload

        while (Duration.between(start, Instant.now()).getSeconds() < 60 * 10) {

            //   loop over each sample
            for (long j = 0; j < 150; j++) {
                Customer custm = new Customer(rnd.nextInt(), UUID.randomUUID().toString());
                KafkaProducerExample.
                        producer.send(new ProducerRecord<String, Customer>(KafkaProducerExample.config.getTopic(),
                                null, null, UUID.randomUUID().toString(), custm));
            }

            log.info("sent {} events Per Second ", 150 );
            Thread.sleep(KafkaProducerExample.config.getDelay());
        }
    }
}
