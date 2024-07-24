
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class BinPackState2 {
    //TODO give fup and fdown as paramters to the functions.
    private static final Logger log = LogManager.getLogger(BinPackState2.class);
    public static   int size = 1;
    public Instant LastUpScaleDecision = Instant.now();
    
    static double wsla = Double.valueOf(System.getenv("WSLA"));

    static double mu = Double.valueOf(System.getenv("MU"));

    static String action = "none";

    static List<Consumer> assignment = new ArrayList<Consumer>();
    static List<Consumer> currentAssignment = assignment;
    static List<Consumer> tempAssignment = assignment;

    public  static void scaleAsPerBinPack() {
        action = "none";
        log.info("Currently we have this number of consumers group {} {}","testgroup1", size );
        int neededsize = binPackAndScale();
        log.info("We currently need the following consumers for group1 (as per the bin pack) {}", neededsize);
        int replicasForscale = neededsize - size;
        if (replicasForscale > 0) {
            action = "up";
            //TODO IF and Else IF can be in the same logic
            log.info("We have to upscale  group1 by {}", replicasForscale);
            //currentAssignment = assignment;
            return;

        } else {
            int neededsized = binPackAndScaled();
            int replicasForscaled = size - neededsized;
            if (replicasForscaled > 0) {
               action = "down";
                log.info("We have to downscale  group by {} {}", "testgroup1", replicasForscaled);
                //currentAssignment = assignment;
                return;
            }
        }


        if (assignmentViolatesTheSLA2()) {
            action = "REASS";
        }
        log.info("===================================");
    }


    private static int binPackAndScale() {
        log.info(" shall we upscale group {}", "testgroup1");
        List<Consumer> consumers = new ArrayList<>();
        int consumerCount = 1;
        List<Partition> parts = new ArrayList<>(ArrivalProducer.topicpartitions);

        float fup = Float.valueOf(System.getenv("FUP"));

        for (Partition partition : parts) {
            if (partition.getLag() > mu*wsla*fup) {
                log.info("Since partition {} has lag {} higher than consumer capacity times wsla {}" +
                        " we are truncating its lag", partition.getId(), partition.getLag(), mu*wsla* fup);
                partition.setLag((long)(mu*wsla*fup/*dynamicAverageMaxConsumptionRate*wsla*/));
            }
        }
        //if a certain partition has an arrival rate  higher than R  set its arrival rate  to R
        //that should not happen in a well partionned topic
        for (Partition partition : parts) {
            if (partition.getArrivalRate() > mu*fup/*dynamicAverageMaxConsumptionRate*wsla*/) {
                log.info("Since partition {} has arrival rate {} higher than consumer service rate {}" +
                                " we are truncating its arrival rate", partition.getId(),
                        String.format("%.2f", partition.getArrivalRate()),
                        String.format("%.2f", mu*fup /*dynamicAverageMaxConsumptionRate*wsla*/));
                partition.setArrivalRate(mu*fup /*dynamicAverageMaxConsumptionRate*wsla*/);
            }
        }
        //start the bin pack FFD with sort
        Collections.sort(parts, Collections.reverseOrder());

        while (true) {
            int j;
            consumers.clear();
            for (int t = 0; t < consumerCount; t++) {
                consumers.add(new Consumer((String.valueOf(t)),  (long)(mu*wsla*fup),
                        mu*fup));
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
        assignment = consumers;
        tempAssignment = consumers;
        log.info(" The BP up scaler recommended for group {} {}", "testgroup1", consumers.size());
        return consumers.size();
    }

    static  int binPackAndScaled() {
        log.info(" shall we down scale group {} ", "testgroup1");
        List<Consumer> consumers = new ArrayList<>();
        int consumerCount = 1;
        List<Partition> parts = new ArrayList<>(ArrivalProducer.topicpartitions);
        float fdown = Float.valueOf(System.getenv("FDOWN"));
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

        assignment = consumers;
        log.info(" The BP down scaler recommended  for group {} {}", "testgroup1", consumers.size());
        return consumers.size();
    }

    private static boolean assignmentViolatesTheSLA2() {
        float fup = Float.valueOf(System.getenv("FUP"));
        List<Partition> partsReset = new ArrayList<>(ArrivalProducer.topicpartitions);
        for (Consumer cons : currentAssignment) {
            double sumPartitionsArrival = 0;
            double sumPartitionsLag = 0;
            for (Partition p : cons.getAssignedPartitions()) {
                sumPartitionsArrival += partsReset.get(p.getId()).getArrivalRate();
                sumPartitionsLag += partsReset.get(p.getId()).getLag();
            }

            if (sumPartitionsLag  > ( wsla*mu*fup)
                    || sumPartitionsArrival > mu*fup) {
                return true;
            }
        }
        return false;
    }

}
