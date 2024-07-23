package fr.unice.scale.latencyaware.producer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.grpc.stub.StreamObserver;

public class ArrivalServiceImpl extends ArrivalServiceGrpc.ArrivalServiceImplBase {
    private static final Logger log = LogManager.getLogger(ArrivalServiceImpl.class);
    @Override
    public void arrivalRate(ArrivalRequest request, StreamObserver<ArrivalResponse> responseObserver) {
        log.info("received new rate request {}", request.getArrivalrequest());
        log.info("Arrival is {}", NonUniformWorkload.ArrivalRate );
        ArrivalResponse arrival = ArrivalResponse.newBuilder()
                .setArrival(NonUniformWorkload.ArrivalRate)
                        .build();

        responseObserver.onNext(arrival);
        responseObserver.onCompleted();

    }
}