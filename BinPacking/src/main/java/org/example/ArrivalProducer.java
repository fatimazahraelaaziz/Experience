package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArrivalProducer {
    static List<Partition> topicpartitions = new ArrayList<>();
    static double totalArrivalrate;
    private static Random random = new Random();

    public static void initializePartitions(int numPartitions) {
        topicpartitions.clear();
        for (int i = 0; i < numPartitions; i++) {
            topicpartitions.add(new Partition(i, random.nextInt(200), 10.0 + random.nextDouble() * 30.0)); // Random lag and arrival rate
        }
    }

    public static void callForArrivals() {
        for (int cycle = 0; cycle < 10; cycle++) { // Simulate 10 cycles of updates
            initializePartitions(Lag.partitions);
            BinPackState.scaleAsPerBinPack();
        }
        BinPackState.writeAverageDurationToCSV(); // Write average duration to CSV after 10 cycles
    }
}
