package org.example;

import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide the number of partitions as an argument.");
            System.exit(1);
        }

        int numPartitions;
        try {
            numPartitions = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of partitions. Please provide a valid integer.");
            System.exit(1);
            return; // Just to satisfy the compiler
        }

        Lag.readEnvAndCreateAdminClient(numPartitions);
        ArrivalProducer.callForArrivals();
    }
}
