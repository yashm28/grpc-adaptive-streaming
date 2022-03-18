package route;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.45.0)",
    comments = "Source: route.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class RequestServiceGrpc {

  private RequestServiceGrpc() {}

  public static final String SERVICE_NAME = "route.RequestService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<route.Request,
      route.Response> getRequestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "request",
      requestType = route.Request.class,
      responseType = route.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<route.Request,
      route.Response> getRequestMethod() {
    io.grpc.MethodDescriptor<route.Request, route.Response> getRequestMethod;
    if ((getRequestMethod = RequestServiceGrpc.getRequestMethod) == null) {
      synchronized (RequestServiceGrpc.class) {
        if ((getRequestMethod = RequestServiceGrpc.getRequestMethod) == null) {
          RequestServiceGrpc.getRequestMethod = getRequestMethod =
              io.grpc.MethodDescriptor.<route.Request, route.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "request"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route.Request.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route.Response.getDefaultInstance()))
              .setSchemaDescriptor(new RequestServiceMethodDescriptorSupplier("request"))
              .build();
        }
      }
    }
    return getRequestMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RequestServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RequestServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RequestServiceStub>() {
        @java.lang.Override
        public RequestServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RequestServiceStub(channel, callOptions);
        }
      };
    return RequestServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RequestServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RequestServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RequestServiceBlockingStub>() {
        @java.lang.Override
        public RequestServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RequestServiceBlockingStub(channel, callOptions);
        }
      };
    return RequestServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static RequestServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RequestServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RequestServiceFutureStub>() {
        @java.lang.Override
        public RequestServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RequestServiceFutureStub(channel, callOptions);
        }
      };
    return RequestServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class RequestServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void request(route.Request request,
        io.grpc.stub.StreamObserver<route.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRequestMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRequestMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                route.Request,
                route.Response>(
                  this, METHODID_REQUEST)))
          .build();
    }
  }

  /**
   */
  public static final class RequestServiceStub extends io.grpc.stub.AbstractAsyncStub<RequestServiceStub> {
    private RequestServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RequestServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RequestServiceStub(channel, callOptions);
    }

    /**
     */
    public void request(route.Request request,
        io.grpc.stub.StreamObserver<route.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRequestMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class RequestServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<RequestServiceBlockingStub> {
    private RequestServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RequestServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RequestServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public route.Response request(route.Request request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRequestMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class RequestServiceFutureStub extends io.grpc.stub.AbstractFutureStub<RequestServiceFutureStub> {
    private RequestServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RequestServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RequestServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<route.Response> request(
        route.Request request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRequestMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REQUEST = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final RequestServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(RequestServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REQUEST:
          serviceImpl.request((route.Request) request,
              (io.grpc.stub.StreamObserver<route.Response>) responseObserver);
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

  private static abstract class RequestServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    RequestServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return route.Route.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("RequestService");
    }
  }

  private static final class RequestServiceFileDescriptorSupplier
      extends RequestServiceBaseDescriptorSupplier {
    RequestServiceFileDescriptorSupplier() {}
  }

  private static final class RequestServiceMethodDescriptorSupplier
      extends RequestServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    RequestServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (RequestServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new RequestServiceFileDescriptorSupplier())
              .addMethod(getRequestMethod())
              .build();
        }
      }
    }
    return result;
  }
}
