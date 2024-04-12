import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;


public class ArrivalProducer {
    private static final Logger log = LogManager.getLogger(ArrivalProducer.class);
    static ArrayList<Partition> topicpartitions;
    static float totalArrivalrate;
    static {
        topicpartitions = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            topicpartitions.add(new Partition(i, 0, 0));
        }
    }

    public static void callForArrivals() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("arrivalservice",
                        5002)
                .usePlaintext()
                .build();
        ArrivalServiceGrpc.ArrivalServiceBlockingStub arrivalServiceBlockingStub =
                ArrivalServiceGrpc.newBlockingStub(managedChannel);
        ArrivalRequest request = ArrivalRequest.newBuilder()
                .setArrivalrequest("Give me the arrival rate please").build();
        ArrivalResponse reply = arrivalServiceBlockingStub.consumptionRate(request);
        log.info("Arrival from the producer is {}", reply);
        totalArrivalrate = reply.getArrival();
        double partitionArrival = reply.getArrival()/5.0;
        log.info("Arrival into each partition is {}", partitionArrival);
        for (int i = 0; i < 5; i++) {
            topicpartitions.get(i).setArrivalRate(partitionArrival);
        }

        // double partitionArrival = reply.getArrival()/5.0;
        /*log.info("Arrival into first 2 partition is {}",
                (totalArrivalrate * 0.5) / 2.0);
        for (int i = 0; i < 2; i++) {
            topicpartitions.get(i).setArrivalRate((totalArrivalrate * 0.5) / 2.0);
        }

        log.info("Arrival into first remaining 7 partitions is {}",
                (totalArrivalrate * 0.5) / 7.0);
        for (int i = 2; i < 9; i++) {
            topicpartitions.get(i).setArrivalRate((totalArrivalrate * 0.5) / 7.0);
        }*/
        managedChannel.shutdown();
    }


    public static void callForConsumers() {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("rateservice", 5002)
                .usePlaintext()
                .build();
        ArrivalServiceGrpc.ArrivalServiceBlockingStub rateServiceBlockingStub
                = ArrivalServiceGrpc.newBlockingStub(managedChannel);
        RateRequest request = RateRequest.newBuilder()
                .setRaterequest("Give me the Assignment please").build();
        RateResponse reply = rateServiceBlockingStub
                .consumptionRatee(request);
        log.info("latency is {}", reply);
        managedChannel.shutdown();
    }


}
