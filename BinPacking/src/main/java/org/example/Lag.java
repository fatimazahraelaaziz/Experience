package org.example;


public class Lag {
    static int partitions;

    public static void readEnvAndCreateAdminClient(int numPartitions) {
        partitions = numPartitions;
    }
}
