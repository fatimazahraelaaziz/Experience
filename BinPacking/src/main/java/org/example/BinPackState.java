package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BinPackState {
    private static final Logger log = LogManager.getLogger(BinPackState.class);
    public static int size = 1;
    public Instant lastUpScaleDecision = Instant.now();

    static double wsla = 0.5; // Static value for simulation
    static double mu = 200.0; // Static value for simulation
    static String action = "none";

    static List<Consumer> assignment = new ArrayList<>();
    static List<Consumer> currentAssignment = assignment;
    static List<Consumer> tempAssignment = assignment;

    static int upScaleLoopCounter = 0;
    static int downScaleLoopCounter = 0;
    static int reassignmentLoopCounter = 0;

    public static void scaleAsPerBinPack() {
        Instant start = Instant.now(); // Start timing

        action = "none";
        log.info("Currently we have this number of consumers group: " + size);
        int neededSize = binPackAndScale();
        log.info("We currently need the following consumers (as per the bin pack): " + neededSize);
        int replicasForScale = neededSize - size;
        if (replicasForScale > 0) {
            action = "up";
            log.info("We have to upscale by " + replicasForScale);
            upScaleLoopCounter++;
        } else {
            int neededSizeDown = binPackAndScaled();
            int replicasForScaled = size - neededSizeDown;
            if (replicasForScaled > 0) {
                action = "down";
                log.info("We have to downscale by " + replicasForScaled);
                downScaleLoopCounter++; // Increment down-scale loop counter

            }
        }

        if (assignmentViolatesTheSLA2()) {
            action = "REASS";
            reassignmentLoopCounter++; // Increment reassignment loop counter
        }

        Instant end = Instant.now(); // End timing
        Duration duration = Duration.between(start, end); // Calculate duration
        long micros = duration.toNanos() / 1000; // Convert nanoseconds to microseconds
        log.info("Scaling operation took: {} µs", micros); // Log the duration

        // Log and write loop counters to CSV
        log.info("Up-scale loop counter: {}", upScaleLoopCounter);
        log.info("Down-scale loop counter: {}", downScaleLoopCounter);
        log.info("Reassignment loop counter: {}", reassignmentLoopCounter);

        try (FileWriter fw = new FileWriter("scaling_duration.csv", true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            // Vérifier si le fichier est nouvellement créé ou vide pour écrire les en-têtes
            File f = new File("scaling_duration.csv");
            if (f.length() == 0) {
                bw.write("Timestamp,DurationMicros,UpScaleLoopCounter,DownScaleLoopCounter,ReassignmentLoopCounter\n");
            }

            // Écrire la date actuelle et la durée dans le fichier
            String formattedDate = Instant.now().toString();
            bw.write(formattedDate + "," + micros + "," + upScaleLoopCounter + "," + downScaleLoopCounter + "," + reassignmentLoopCounter + "\n");
        } catch (IOException e) {
            log.error("An error occurred while writing to the file", e);
        }

        log.info("===================================");
    }

    private static int binPackAndScale() {
        log.info("Shall we upscale group {}", "testgroup1");
        List<Consumer> consumers = new ArrayList<>();
        int consumerCount = 1;
        List<Partition> parts = new ArrayList<>(ArrivalProducer.topicpartitions);

        float fup = 0.7f; // Static value for simulation
        for (Partition partition : parts) {
            long capacity = (long) (mu * wsla * fup);
            if (partition.getLag() > capacity) {
                log.info("Since partition {} has lag {} higher than consumer capacity times wsla {}" +
                        " we are truncating its lag", partition.getId(), partition.getLag(), capacity);
                partition.setLag((int) capacity);
            }
        }

        for (Partition partition : parts) {
            double serviceRate = mu * fup;
            if (partition.getArrivalRate() > serviceRate) {
                log.info("Since partition {} has arrival rate {} higher than consumer service rate {}" +
                        " we are truncating son arrivée", partition.getId(),
                        String.format("%.2f", partition.getArrivalRate()), String.format("%.2f", serviceRate));
                partition.setArrivalRate(serviceRate);
            }
        }

        Collections.sort(parts, Collections.reverseOrder());

        while (true) {
            int j;
            consumers.clear();
            for (int t = 0; t < consumerCount; t++) {
                consumers.add(new Consumer((String.valueOf(t)), (long) (mu * wsla * fup), mu * fup));
            }

            for (j = 0; j < parts.size(); j++) {
                int i;
                Collections.sort(consumers, Collections.reverseOrder());
                for (i = 0; i < consumerCount; i++) {
                    if (consumers.get(i).getRemainingLagCapacity() >= parts.get(j).getLag()
                            && consumers.get(i).getRemainingArrivalCapacity() >= parts.get(j).getArrivalRate()) {
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
        log.info("The BP up scaler recommended for group {} {}", "testgroup1", consumers.size());
        return consumers.size();
    }

    private static int binPackAndScaled() {
        log.info("Shall we down scale group {}", "testgroup1");
        List<Consumer> consumers = new ArrayList<>();
        int consumerCount = 1;
        List<Partition> parts = new ArrayList<>(ArrivalProducer.topicpartitions);
        float fdown = 0.2f; // Static value for simulation
        double fractionDynamicAverageMaxConsumptionRate = mu * fdown;

        for (Partition partition : parts) {
            long capacity = (long) (fractionDynamicAverageMaxConsumptionRate * wsla);
            if (partition.getLag() > capacity) {
                log.info("Since partition {} has lag {} higher than consumer capacity times wsla {}" +
                        " we are truncating its lag", partition.getId(), partition.getLag(), capacity);
                partition.setLag((int) capacity);
            }
        }

        for (Partition partition : parts) {
            double serviceRate = fractionDynamicAverageMaxConsumptionRate;
            if (partition.getArrivalRate() > serviceRate) {
                log.info("Since partition {} has arrival rate {} higher than consumer service rate {}" +
                        " we are truncating son arrivée", partition.getId(),
                        String.format("%.2f", partition.getArrivalRate()), String.format("%.2f", serviceRate));
                partition.setArrivalRate(serviceRate);
            }
        }

        Collections.sort(parts, Collections.reverseOrder());
        while (true) {
            int j;
            consumers.clear();
            for (int t = 0; t < consumerCount; t++) {
                consumers.add(new Consumer((String.valueOf(t)), (long) (fractionDynamicAverageMaxConsumptionRate * wsla), fractionDynamicAverageMaxConsumptionRate));
            }

            for (j = 0; j < parts.size(); j++) {
                int i;
                Collections.sort(consumers, Collections.reverseOrder());
                for (i = 0; i < consumerCount; i++) {
                    if (consumers.get(i).getRemainingLagCapacity() >= parts.get(j).getLag()
                            && consumers.get(i).getRemainingArrivalCapacity() >= parts.get(j).getArrivalRate()) {
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
        log.info("The BP down scaler recommended for group {} {}", "testgroup1", consumers.size());
        return consumers.size();
    }

    private static boolean assignmentViolatesTheSLA2() {
        float fup = 0.7f; // Static value for simulation
        List<Partition> partsReset = new ArrayList<>(ArrivalProducer.topicpartitions);
        for (Consumer cons : currentAssignment) {
            double sumPartitionsArrival = 0;
            double sumPartitionsLag = 0;
            for (Partition p : cons.getAssignedPartitions()) {
                sumPartitionsArrival += partsReset.get(p.getId()).getArrivalRate();
                sumPartitionsLag += partsReset.get(p.getId()).getLag();
            }

           

            if (sumPartitionsLag > (wsla * mu * fup) || sumPartitionsArrival > mu * fup) {
                return true;
            }
        }
        return false;
    }
}
