package fr.unice.scale.latencyaware;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Workload {
    // private static final Logger log = LogManager.getLogger(KafkaProducerConfig.class);
    private static String csvSplitBy = ",";

    private static double targetXPointValue;

    public ArrayList<Double> getDatay() {
        return datay;
    }
    private static ArrayList<Double> datay = new ArrayList<Double>();
    public ArrayList<Double> getDatax() {
        return datax;
    }
    private static ArrayList<Double> datax = new ArrayList<Double>();
    public Workload() throws IOException, URISyntaxException {
        this.loadWorkload();
    }
    private void loadWorkload() throws IOException, URISyntaxException {
        ClassLoader CLDR = this.getClass().getClassLoader();
        InputStream inputStream = CLDR.getResourceAsStream("defaultArrivalRatesm.csv");

        List<String> out = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                out.add(line);
            }
        }
        for (String line : out) {
            String[] workFields = line.split(csvSplitBy);
            //inputXPointValue = Double.parseDouble(workFields[0]);
            targetXPointValue = Double.parseDouble(workFields[1]);
            datax.add(0d);
            datay.add(targetXPointValue);
        }
    }
}



