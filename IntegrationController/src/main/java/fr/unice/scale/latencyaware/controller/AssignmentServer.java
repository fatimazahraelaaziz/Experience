package fr.unice.scale.latencyaware.controller;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.unice.scale.latencyaware.controller.ConsumerGrpc;
import fr.unice.scale.latencyaware.controller.PartitionGrpc;
import fr.unice.scale.latencyaware.controller.AssignmentServiceGrpc;
import fr.unice.scale.latencyaware.controller.AssignmentRequest;
import fr.unice.scale.latencyaware.controller.AssignmentResponse;


public class AssignmentServer implements Runnable {
    private final int port;
    private final Server server;
    private static final Logger log = LogManager.getLogger(AssignmentServer.class);
    public AssignmentServer(int port) throws IOException {
        this(ServerBuilder.forPort(port), port);
    }
    public AssignmentServer(ServerBuilder<?> serverBuilder, int port) {
        this.port = port;
        this.server = serverBuilder.addService(new AssignmentService()).build();
    }
    public void start() throws IOException {
        log.info("Server Started");
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may has been reset by its JVM shutdown hook.
                log.info("*** shutting down gRPC server since JVM is shutting down");
                AssignmentServer.this.stop();
                log.info("*** server shut down");
            }
        });
    }
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
    @Override
    public void run() {
        try {
            start();
            blockUntilShutdown();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static class AssignmentService extends AssignmentServiceGrpc.AssignmentServiceImplBase {
        @Override
        public void getAssignment(AssignmentRequest request, StreamObserver<AssignmentResponse> responseObserver) {
            if (BinPackLag2.assignment.size() == 0) {
                List<ConsumerGrpc> assignmentReply = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    List<PartitionGrpc> pgrpclist = new ArrayList<>();
                    PartitionGrpc pgrpc = PartitionGrpc.newBuilder().setId(i).build();
                    pgrpclist.add(pgrpc);
                    ConsumerGrpc consg = ConsumerGrpc.newBuilder().setId(i)
                            .addAllAssignedPartitions(pgrpclist).build();
                    assignmentReply.add(consg);
                }
                ////////////////////////////////////
                log.info("The assignment is {}", assignmentReply);
                responseObserver.onNext(AssignmentResponse.newBuilder().addAllConsumers(assignmentReply).build());
                responseObserver.onCompleted();
                log.info("Sent Assignment to client");
                return;
            }
            log.info(request.getRequest());
            //TODO Synchronize access to assignment
            List<Consumer> assignment = BinPackLag2.assignment;
            log.info("The assignment is {}", assignment);
            List<ConsumerGrpc> assignmentReply = new ArrayList<>(assignment.size());
            for (Consumer cons : assignment) {
                List<PartitionGrpc> pgrpclist = new ArrayList<>();
                for (Partition p : cons.getAssignedPartitions()) {
                    log.info("partition {} is assigned to consumer {}", p.getId(), cons.getId());
                    PartitionGrpc pgrpc = PartitionGrpc.newBuilder().setId(p.getId()).build();
                    pgrpclist.add(pgrpc);
                }
                ConsumerGrpc consg = ConsumerGrpc.newBuilder().setId(Integer.parseInt(cons.getId()))
                        .addAllAssignedPartitions(pgrpclist).build();
                assignmentReply.add(consg);
            }

         /*   for(ConsumerGrpc cons : assignmentReply){
                log.info("Consumer {} has the following partitions", cons.getId());
                for(PartitionGrpc part : cons.getAssignedPartitionsList()){
                    log.info("partition {}", part.getId());
                }
            }*/
            responseObserver.onNext(AssignmentResponse.newBuilder().addAllConsumers(assignmentReply).build());
            responseObserver.onCompleted();
            log.info("Sent Assignment to client");
        }
    }

}