package fr.unice.scale.latencyaware.producer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigLoader {
    public static List<Integer> loadPartitionWeights() {
        String weights = System.getenv("PARTITION_WEIGHTS");
        if (weights == null || weights.isEmpty()) {
            throw new IllegalArgumentException("Environment variable PARTITION_WEIGHTS is not set or is empty");
        }
        return Arrays.stream(weights.split(","))
                     .map(Integer::parseInt)
                     .collect(Collectors.toList());
    }
}
