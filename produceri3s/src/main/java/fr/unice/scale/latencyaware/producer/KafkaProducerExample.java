package fr.unice.scale.latencyaware.producer;

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

        OldWorkload.startWorkload();

    }
    /*new ServerThread() is an instance of a class that implements the Runnable interface. The Runnable interface should implement a public method called run() which contains the code that will be executed in the new thread.

    The start() method is called on the Thread instance to begin the execution of the new thread. The Java Virtual Machine calls the run() method on the Runnable object that was passed to the Thread constructor.

    The new thread will stop when the run() method returns, either because it's completed its work or because an unhandled exception occurred. */
    private static void startServer() {
        Thread server = new Thread  (new ServerThread());
        server.start();
    }
}