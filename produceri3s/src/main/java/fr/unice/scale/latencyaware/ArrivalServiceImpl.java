package fr.unice.scale.latencyaware;

import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.unice.scale.latencyaware.producer.ArrivalRequest;
import fr.unice.scale.latencyaware.producer.ArrivalResponse;
import fr.unice.scale.latencyaware.producer.ArrivalServiceGrpc;

public class ArrivalServiceImpl extends ArrivalServiceGrpc.ArrivalServiceImplBase {
    private static final Logger log = LogManager.getLogger(ArrivalServiceImpl.class);
    @Override
    public void arrivalRate(ArrivalRequest request, StreamObserver<ArrivalResponse> responseObserver) {
        log.info("received new rate request {}", request.getArrivalrequest());
        log.info("Arrival is {}", OldWorkload.ArrivalRate );
        ArrivalResponse arrival = ArrivalResponse.newBuilder()
                .setArrival(OldWorkload.ArrivalRate)
                        .build();

        responseObserver.onNext(arrival);
        responseObserver.onCompleted();

    }
}
