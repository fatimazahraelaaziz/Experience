import org.apache.commons.math3.distribution.ParetoDistribution;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.TimeZone;

public class ConsumerMain {
    private static final Logger log = LogManager.getLogger(ConsumerMain.class);
    public static KafkaConsumer<String, Customer> consumer = null;
    static double eventsViolating = 0;
    static double eventsNonViolating = 0;
    static double totalEvents = 0;
    static float maxConsumptionRatePerConsumer = 0.0f;
    static float ConsumptionRatePerConsumerInThisPoll = 0.0f;
    static float averageRatePerConsumerForGrpc = 0.0f;
    static long pollsSoFar = 0;

    static ArrayList<TopicPartition> tps;
    static KafkaProducer<String, Customer> producer;
    static Double maxConsumptionRatePerConsumer1 = 0.0d;

    static double scale = Double.valueOf(System.getenv("SCALE"));
    static double shape = Double.valueOf(System.getenv("SHAPE"));
    static ParetoDistribution dist = new ParetoDistribution(scale, shape);

    public ConsumerMain() throws IOException, URISyntaxException, InterruptedException {
    }

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        PrometheusUtils.initPrometheus();
        KafkaConsumerConfig config = KafkaConsumerConfig.fromEnv();
        log.info(KafkaConsumerConfig.class.getName() + ": {}", config.toString());
        Properties props = KafkaConsumerConfig.createProperties(config);

        props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, StickyAssignor.class.getName());

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(config.getTopic()));
        log.info("Subscribed to topic {}", config.getTopic());

        addShutDownHook();

        double max = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy'T'HH:mm:ss.SSSSSS");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));  // Définir le fuseau horaire sur UTC

        Logger logger = LogManager.getLogger(ConsumerMain.class);
 
        try {
            while (true) {
                ConsumerRecords<String, Customer> records = consumer.poll(Duration.ofMillis(Long.MAX_VALUE));

                if (records.count() != 0) {
                    for (ConsumerRecord<String, Customer> record : records) {
                        totalEvents++;
                        try {
                            double sleep = dist.sample();
                            if (max < sleep) {
                                max = sleep;
                            }

                            log.info("sleep is {}", sleep);
                            log.info("long sleep  {}", (long) sleep);

                            Thread.sleep((long) sleep);

                            PrometheusUtils.processingTime.setDuration(sleep);
                            PrometheusUtils.totalLatencyTime.setDuration(System.currentTimeMillis() - record.timestamp());

                            if (System.currentTimeMillis() - record.timestamp() <= 500) {
                                eventsNonViolating++;
                            } else {
                                eventsViolating++;
                            }

                            long currentTimeMillis = System.currentTimeMillis();
                            Date currentDate = new Date(currentTimeMillis);

                            Timestamp timestamp = new Timestamp(record.timestamp());
                            Date insertionDate = new Date(timestamp.getTime());

                            logger.info("latency is {}, insertion time is {}, processing time is {}",
                                    currentTimeMillis - record.timestamp(), simpleDateFormat.format(insertionDate), simpleDateFormat.format(currentDate));

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                PrometheusUtils.processingTime.setDuration(max);
                max = 0;
                consumer.commitSync();
                log.info("In this poll, received {} events", records.count());
            }
        } catch (WakeupException e) {
            // Handle exception
        } finally {
            consumer.close();
            log.info("Closed consumer and we are done");
        }
    }

    private static void addShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                log.info("Starting exit...");
                consumer.wakeup();
                try {
                    this.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
