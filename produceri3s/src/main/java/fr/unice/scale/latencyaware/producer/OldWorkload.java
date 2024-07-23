package fr.unice.scale.latencyaware.producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.unice.scale.latencyaware.producer.Customer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.UUID;

//The ArrivalRate variable represents the size of the current batch of messages.

public class OldWorkload {

    static float ArrivalRate;
    public static void  startWorkload() throws IOException, URISyntaxException, InterruptedException {

        final Logger log = LogManager.getLogger(OldWorkload.class);

        Workload wrld = new Workload();

        Random rnd = new Random();
        // over all the workload
        for (int i = 0; i < wrld.getDatax().size(); i++) {
            log.info("sending a batch of authorizations of size:{}",
                    Math.ceil(wrld.getDatay().get(i)));
            ArrivalRate = (float) Math.ceil(wrld.getDatay().get(i));
            //   loop over each sample
            for (long j = 0; j < Math.ceil(wrld.getDatay().get(i)); j++) {
                Customer custm = new Customer(rnd.nextInt(), UUID.randomUUID().toString());
               KafkaProducerExample.
                       producer.send(new ProducerRecord<String, Customer>(KafkaProducerExample.config.getTopic(),
                        null, null, UUID.randomUUID().toString(), custm));
            }

            log.info("sent {} events Per Second ", Math.ceil(wrld.getDatay().get(i)));
            Thread.sleep(KafkaProducerExample.config.getDelay());
        }
    }



    public static void  startWorkloadUniform() throws IOException, URISyntaxException, InterruptedException {

        final Logger log = LogManager.getLogger(OldWorkload.class);

        Workload wrld = new Workload();

        Random rnd = new Random();
        // over all the workload
        for (int i = 0; i < wrld.getDatax().size(); i++) {
            log.info("sending a batch of authorizations of size:{}",
                    Math.ceil(wrld.getDatay().get(i)));
            ArrivalRate = (float) Math.ceil(wrld.getDatay().get(i));
            //   loop over each sample

            double sleep = 1000.0/(ArrivalRate);
            for (long j = 0; j < Math.ceil(wrld.getDatay().get(i)); j++) {
                Customer custm = new Customer(rnd.nextInt(), UUID.randomUUID().toString());
                KafkaProducerExample.
                        producer.send(new ProducerRecord<String, Customer>(KafkaProducerExample.config.getTopic(),
                                null, null, UUID.randomUUID().toString(), custm));
                log.info("sleeping for {}", sleep );
                Thread.sleep((long)sleep);
            }

            log.info("sent {} events Per Second ", Math.ceil(wrld.getDatay().get(i)));
            //Thread.sleep(KafkaProducerExample.config.getDelay());
        }
    }


    //This method divides the workload into mini-batches of 25 messages each.
    //It sends each mini-batch separately, with a sleep period between each mini-batch. 
    public static void  startWorkloadUniformBatch25() throws IOException, URISyntaxException, InterruptedException {
        final Logger log = LogManager.getLogger(OldWorkload.class);
        Workload wrld = new Workload();
        Random rnd = new Random();
        // over all the workload
        for (int i = 0; i < wrld.getDatax().size(); i++) {
            log.info("sending a batch of authorizations of size:{}",
                    Math.ceil(wrld.getDatay().get(i)));
            ArrivalRate = (float) Math.ceil(wrld.getDatay().get(i));
            //   loop over each sample
            double minibatch = ArrivalRate/25;
            double fraction = ArrivalRate%25;
            double sleep;
            sleep = 1000/(minibatch+1);

            while (minibatch > 0) {
                for (long j = 0; j < 25; j++) {
                    Customer custm = new Customer(rnd.nextInt(), UUID.randomUUID().toString());
                    KafkaProducerExample.
                            producer.send(new ProducerRecord<String, Customer>(KafkaProducerExample.config.getTopic(),
                                    null, null, UUID.randomUUID().toString(), custm));
                    log.info("sleeping for {}", sleep );
                }

                log.info("sent {} events Per Second ", 25);
                log.info("sleeping for {}", sleep );
                Thread.sleep((long)sleep);
                minibatch--;
            }


            for (long j = 0; j < fraction; j++) {
                Customer custm = new Customer(rnd.nextInt(), UUID.randomUUID().toString());
                KafkaProducerExample.
                        producer.send(new ProducerRecord<String, Customer>(KafkaProducerExample.config.getTopic(),
                                null, null, UUID.randomUUID().toString(), custm));
            }

            log.info("sent {} events Per Second ", fraction);

            log.info("sleeping for {}", sleep );

            Thread.sleep((long)sleep);
        }
    }


    ///////////////////////////////////

    //This method divides the workload into mini-batches of 100 messages each.
    public static void  startWorkloadUniformBatch100() throws IOException, URISyntaxException, InterruptedException {
        final Logger log = LogManager.getLogger(OldWorkload.class);
        Workload workld = new Workload();
        Random rnd = new Random();
        // over all the workload
        for (int i = 0; i < workld.getDatax().size(); i++) {
            log.info("sending a batch of authorizations of size:{}",
                    Math.ceil(workld.getDatay().get(i)));
            ArrivalRate = (float) Math.ceil(workld.getDatay().get(i));
            //   loop over each sample
            double minibatch = Math.floor(ArrivalRate/100.0);
            double fraction = ArrivalRate % 100.0;
            double sleep;

            if (minibatch == 0) sleep = 1000;
            else if (fraction == 0) sleep = 1000 / minibatch;
            else sleep = 1000 / (minibatch + 1);
            while (minibatch > 0) {
                for (long j = 0; j < 100; j++) {
                    Customer custm = new Customer(rnd.nextInt(), UUID.randomUUID().toString());
                    KafkaProducerExample.
                            producer.send(new ProducerRecord<String, Customer>(KafkaProducerExample.config.getTopic(),
                                    null, null, UUID.randomUUID().toString(), custm));
                }

                log.info("sent {} events Per Second ", 50);
                log.info("sleeping for {}", sleep);
                minibatch--;
                Thread.sleep((long) sleep);
            }
            if (fraction != 0) {
                for (long j = 0; j < fraction; j++) {
                    Customer custm = new Customer(rnd.nextInt(), UUID.randomUUID().toString());
                    KafkaProducerExample.
                            producer.send(new ProducerRecord<String, Customer>(KafkaProducerExample.config.getTopic(),
                                    null, null, UUID.randomUUID().toString(), custm));
                }
                log.info("sent {} events Per Second ", fraction);
                log.info("sleeping for {}", sleep);
                Thread.sleep((long) sleep);
            }
        }
    }

}
