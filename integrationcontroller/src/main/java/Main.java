import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutionException;



public class Main {

    private static final Logger log = LogManager.getLogger(Main.class);
    static BinPackState bps;
    static BinPackLag bpl;


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        initialize();
    }//


    private static void initialize() throws InterruptedException, ExecutionException {
        bps = new BinPackState();
        bpl = new BinPackLag();
        Lag.readEnvAndCrateAdminClient();
        log.info("Warming 15 seconds.");
        Thread.sleep(15 * 1000);
        while (true) {
            log.info("Querying Prometheus");
            ArrivalProducer.callForArrivals();
            Lag.getCommittedLatestOffsetsAndLag();
            log.info("--------------------");
            log.info("--------------------");



            //scaleLogic();
            scaleLogicTail2();
            double di = Double.valueOf(System.getenv("DI"));
            log.info("Sleeping for 1 seconds");
            log.info("******************************************");
            log.info("******************************************");
            Thread.sleep((long)di);
        }
    }


    private static void scaleLogicTail2() throws InterruptedException, ExecutionException {
        if (Lag.queryConsumerGroup() != BinPackState.size) {
            log.info("no action, previous action is not seen yet");
            return;
        }
            BinPackState.scaleAsPerBinPack();
            if (BinPackState.action.equals("up") || BinPackState.action.equals("down") || BinPackState.action.equals("REASS") ) {
                BinPackLag.scaleAsPerBinPack();
        }
    }


}
