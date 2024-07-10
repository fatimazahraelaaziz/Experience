import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.Random;


public class KafkaProducerExample {
    private static final Logger log = LogManager.getLogger(KafkaProducerExample.class);

    static KafkaProducerConfig config;
    static KafkaProducer<String, Customer> producer;
    //générateur de nombres aléatoires
    static Random rnd;


    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        rnd = new Random();
        config = KafkaProducerConfig.fromEnv();
        log.info(KafkaProducerConfig.class.getName() + ": {}", config.toString());
        Properties props = KafkaProducerConfig.createProperties(config);
        producer = new KafkaProducer<String, Customer>(props);
        startServer();

        NonUniformWorkload.startWorkload();
        //ConstantWorkload.startWorkload();
        //OldWorkload.startWorkloadUniform();

    }

    private static void startServer() {
        Thread server = new Thread  (new ServerThread());
        server.start();
        
    }
}