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

public class BinPackState {
    public static int size = 1;
    public Instant lastUpScaleDecision = Instant.now();

    static double wsla = 0.5;
    static double mu = 200.0;
    static String action = "none";

    static List<Consumer> assignment = new ArrayList<>();
    static List<Consumer> currentAssignment = new ArrayList<>(assignment);
    static List<Consumer> tempAssignment = new ArrayList<>(assignment);

    static int upScaleLoopCounter = 0;
    static int downScaleLoopCounter = 0;
    static int reassignmentLoopCounter = 0;
    static long totalDurationMicros = 0; // Variable to accumulate durations
    static int numPartitions = 0;

    public static void scaleAsPerBinPack() {
        Instant start = Instant.now();

        action = "none";
        int neededSize = binPackAndScale();
        int replicasForScale = neededSize - size;
        if (replicasForScale > 0) {
            action = "up";
            upScaleLoopCounter++;
        } else {
            int neededSizeDown = binPackAndScaled();
            int replicasForScaled = size - neededSizeDown;
            if (replicasForScaled > 0) {
                action = "down";
                downScaleLoopCounter++;
            }
        }

        if (assignmentViolatesTheSLA2()) {
            action = "REASS";
            reassignmentLoopCounter++;
        }

        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        long micros = duration.toNanos() / 1000;
        totalDurationMicros += micros;
        numPartitions = ArrivalProducer.topicpartitions.size();
    }

    public static void writeAverageDurationToCSV() {
        double averageDurationMicros = totalDurationMicros / 10.0;

        try (FileWriter fw = new FileWriter("scaling_duration.csv", true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            File f = new File("scaling_duration.csv");
            if (f.length() == 0) {
                bw.write("NumPartitions,AverageDurationMicros\n");
            }

            bw.write(numPartitions + "," + averageDurationMicros + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int binPackAndScale() {
        List<Consumer> consumers = new ArrayList<>();
        int consumerCount = 1;
        List<Partition> parts = new ArrayList<>(ArrivalProducer.topicpartitions);

        float fup = 0.7f;
        for (Partition partition : parts) {
            long capacity = (long) (mu * wsla * fup);
            if (partition.getLag() > capacity) {
                partition.setLag((int) capacity);
            }
        }

        for (Partition partition : parts) {
            double serviceRate = mu * fup;
            if (partition.getArrivalRate() > serviceRate) {
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
        return consumers.size();
    }

    private static int binPackAndScaled() {
        List<Consumer> consumers = new ArrayList<>();
        int consumerCount = 1;
        List<Partition> parts = new ArrayList<>(ArrivalProducer.topicpartitions);
        float fdown = 0.2f;
        double fractionDynamicAverageMaxConsumptionRate = mu * fdown;

        for (Partition partition : parts) {
            long capacity = (long) (fractionDynamicAverageMaxConsumptionRate * wsla);
            if (partition.getLag() > capacity) {
                partition.setLag((int) capacity);
            }
        }

        for (Partition partition : parts) {
            double serviceRate = fractionDynamicAverageMaxConsumptionRate;
            if (partition.getArrivalRate() > serviceRate) {
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
        return consumers.size();
    }

    private static boolean assignmentViolatesTheSLA2() {
        float fup = 0.7f;
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
