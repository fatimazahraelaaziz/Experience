import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;

public class Main {

    private static final Logger log = LogManager.getLogger(Main.class);
    // static BinPackRestructure bp;

    static BinPackRestructureWithLag bp;

    /* static BinPackState bps;
    static BinPackLag bpl;*/


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        initialize();
    }


    private static void initialize() throws InterruptedException, ExecutionException {
       /* bps = new BinPackState();
        bpl = new BinPackLag();*/

       // bp = new BinPackRestructure();
        bp = new BinPackRestructureWithLag();

        Lag.readEnvAndCrateAdminClient();
        log.info("Warming 15  seconds.");
        Thread.sleep(15 * 1000);



        while (true) {
            log.info("Querying Prometheus");
            ArrivalProducer.callForArrivals();
            Lag.getCommittedLatestOffsetsAndLag();
            //scaleLogic();
            log.info("--------------------");
            log.info("--------------------");
            log.info("Sleeping for 500 seconds");
            log.info("******************************************");
            log.info("******************************************");
            //Thread.sleep(1000);

            Thread.sleep(1000);

        }
    }





    private static void scaleLogic() throws InterruptedException, ExecutionException {

        if (Lag.queryConsumerGroup() != BinPackRestructureWithLagLag8p.size) {
            log.info("no action, previous action is not seen yet");
            return;
        }
        BinPackRestructureWithLagLag8p.scaleAsPerBinPackRestructured(); 


    }
}
