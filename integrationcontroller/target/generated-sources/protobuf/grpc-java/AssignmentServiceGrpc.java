import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.43.1)",
    comments = "Source: assignment.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AssignmentServiceGrpc {

  private AssignmentServiceGrpc() {}

  public static final String SERVICE_NAME = "AssignmentService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<AssignmentRequest,
      AssignmentResponse> getGetAssignmentMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAssignment",
      requestType = AssignmentRequest.class,
      responseType = AssignmentResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<AssignmentRequest,
      AssignmentResponse> getGetAssignmentMethod() {
    io.grpc.MethodDescriptor<AssignmentRequest, AssignmentResponse> getGetAssignmentMethod;
    if ((getGetAssignmentMethod = AssignmentServiceGrpc.getGetAssignmentMethod) == null) {
      synchronized (AssignmentServiceGrpc.class) {
        if ((getGetAssignmentMethod = AssignmentServiceGrpc.getGetAssignmentMethod) == null) {
          AssignmentServiceGrpc.getGetAssignmentMethod = getGetAssignmentMethod =
              io.grpc.MethodDescriptor.<AssignmentRequest, AssignmentResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getAssignment"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AssignmentRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  AssignmentResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AssignmentServiceMethodDescriptorSupplier("getAssignment"))
              .build();
        }
      }
    }
    return getGetAssignmentMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AssignmentServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AssignmentServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AssignmentServiceStub>() {
        @java.lang.Override
        public AssignmentServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AssignmentServiceStub(channel, callOptions);
        }
      };
    return AssignmentServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AssignmentServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AssignmentServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AssignmentServiceBlockingStub>() {
        @java.lang.Override
        public AssignmentServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AssignmentServiceBlockingStub(channel, callOptions);
        }
      };
    return AssignmentServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AssignmentServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AssignmentServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AssignmentServiceFutureStub>() {
        @java.lang.Override
        public AssignmentServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AssignmentServiceFutureStub(channel, callOptions);
        }
      };
    return AssignmentServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class AssignmentServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getAssignment(AssignmentRequest request,
        io.grpc.stub.StreamObserver<AssignmentResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAssignmentMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetAssignmentMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                AssignmentRequest,
                AssignmentResponse>(
                  this, METHODID_GET_ASSIGNMENT)))
          .build();
    }
  }

  /**
   */
  public static final class AssignmentServiceStub extends io.grpc.stub.AbstractAsyncStub<AssignmentServiceStub> {
    private AssignmentServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AssignmentServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AssignmentServiceStub(channel, callOptions);
    }

    /**
     */
    public void getAssignment(AssignmentRequest request,
        io.grpc.stub.StreamObserver<AssignmentResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAssignmentMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class AssignmentServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<AssignmentServiceBlockingStub> {
    private AssignmentServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AssignmentServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AssignmentServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public AssignmentResponse getAssignment(AssignmentRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAssignmentMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class AssignmentServiceFutureStub extends io.grpc.stub.AbstractFutureStub<AssignmentServiceFutureStub> {
    private AssignmentServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AssignmentServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AssignmentServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<AssignmentResponse> getAssignment(
        AssignmentRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAssignmentMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ASSIGNMENT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AssignmentServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AssignmentServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_ASSIGNMENT:
          serviceImpl.getAssignment((AssignmentRequest) request,
              (io.grpc.stub.StreamObserver<AssignmentResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class AssignmentServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AssignmentServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return Assignment.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AssignmentService");
    }
  }

  private static final class AssignmentServiceFileDescriptorSupplier
      extends AssignmentServiceBaseDescriptorSupplier {
    AssignmentServiceFileDescriptorSupplier() {}
  }

  private static final class AssignmentServiceMethodDescriptorSupplier
      extends AssignmentServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AssignmentServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (AssignmentServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AssignmentServiceFileDescriptorSupplier())
              .addMethod(getGetAssignmentMethod())
              .build();
        }
      }
    }
    return result;
  }
}
