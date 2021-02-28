package service;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.33.1)",
    comments = "Source: services/stock.proto")
public final class StockGrpc {

  private StockGrpc() {}

  public static final String SERVICE_NAME = "services.Stock";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<service.StockReq,
      service.StockRes> getGetStockMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getStock",
      requestType = service.StockReq.class,
      responseType = service.StockRes.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.StockReq,
      service.StockRes> getGetStockMethod() {
    io.grpc.MethodDescriptor<service.StockReq, service.StockRes> getGetStockMethod;
    if ((getGetStockMethod = StockGrpc.getGetStockMethod) == null) {
      synchronized (StockGrpc.class) {
        if ((getGetStockMethod = StockGrpc.getGetStockMethod) == null) {
          StockGrpc.getGetStockMethod = getGetStockMethod =
              io.grpc.MethodDescriptor.<service.StockReq, service.StockRes>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getStock"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.StockReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.StockRes.getDefaultInstance()))
              .setSchemaDescriptor(new StockMethodDescriptorSupplier("getStock"))
              .build();
        }
      }
    }
    return getGetStockMethod;
  }

  private static volatile io.grpc.MethodDescriptor<service.StockReq,
      service.StockAnnualYieldResp> getGetAnnualYieldMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAnnualYield",
      requestType = service.StockReq.class,
      responseType = service.StockAnnualYieldResp.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.StockReq,
      service.StockAnnualYieldResp> getGetAnnualYieldMethod() {
    io.grpc.MethodDescriptor<service.StockReq, service.StockAnnualYieldResp> getGetAnnualYieldMethod;
    if ((getGetAnnualYieldMethod = StockGrpc.getGetAnnualYieldMethod) == null) {
      synchronized (StockGrpc.class) {
        if ((getGetAnnualYieldMethod = StockGrpc.getGetAnnualYieldMethod) == null) {
          StockGrpc.getGetAnnualYieldMethod = getGetAnnualYieldMethod =
              io.grpc.MethodDescriptor.<service.StockReq, service.StockAnnualYieldResp>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getAnnualYield"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.StockReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.StockAnnualYieldResp.getDefaultInstance()))
              .setSchemaDescriptor(new StockMethodDescriptorSupplier("getAnnualYield"))
              .build();
        }
      }
    }
    return getGetAnnualYieldMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static StockStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StockStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StockStub>() {
        @java.lang.Override
        public StockStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StockStub(channel, callOptions);
        }
      };
    return StockStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static StockBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StockBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StockBlockingStub>() {
        @java.lang.Override
        public StockBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StockBlockingStub(channel, callOptions);
        }
      };
    return StockBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static StockFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StockFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StockFutureStub>() {
        @java.lang.Override
        public StockFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StockFutureStub(channel, callOptions);
        }
      };
    return StockFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class StockImplBase implements io.grpc.BindableService {

    /**
     */
    public void getStock(service.StockReq request,
        io.grpc.stub.StreamObserver<service.StockRes> responseObserver) {
      asyncUnimplementedUnaryCall(getGetStockMethod(), responseObserver);
    }

    /**
     */
    public void getAnnualYield(service.StockReq request,
        io.grpc.stub.StreamObserver<service.StockAnnualYieldResp> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAnnualYieldMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetStockMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                service.StockReq,
                service.StockRes>(
                  this, METHODID_GET_STOCK)))
          .addMethod(
            getGetAnnualYieldMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                service.StockReq,
                service.StockAnnualYieldResp>(
                  this, METHODID_GET_ANNUAL_YIELD)))
          .build();
    }
  }

  /**
   */
  public static final class StockStub extends io.grpc.stub.AbstractAsyncStub<StockStub> {
    private StockStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StockStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StockStub(channel, callOptions);
    }

    /**
     */
    public void getStock(service.StockReq request,
        io.grpc.stub.StreamObserver<service.StockRes> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetStockMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAnnualYield(service.StockReq request,
        io.grpc.stub.StreamObserver<service.StockAnnualYieldResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAnnualYieldMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class StockBlockingStub extends io.grpc.stub.AbstractBlockingStub<StockBlockingStub> {
    private StockBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StockBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StockBlockingStub(channel, callOptions);
    }

    /**
     */
    public service.StockRes getStock(service.StockReq request) {
      return blockingUnaryCall(
          getChannel(), getGetStockMethod(), getCallOptions(), request);
    }

    /**
     */
    public service.StockAnnualYieldResp getAnnualYield(service.StockReq request) {
      return blockingUnaryCall(
          getChannel(), getGetAnnualYieldMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class StockFutureStub extends io.grpc.stub.AbstractFutureStub<StockFutureStub> {
    private StockFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StockFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StockFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.StockRes> getStock(
        service.StockReq request) {
      return futureUnaryCall(
          getChannel().newCall(getGetStockMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.StockAnnualYieldResp> getAnnualYield(
        service.StockReq request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAnnualYieldMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_STOCK = 0;
  private static final int METHODID_GET_ANNUAL_YIELD = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final StockImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(StockImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_STOCK:
          serviceImpl.getStock((service.StockReq) request,
              (io.grpc.stub.StreamObserver<service.StockRes>) responseObserver);
          break;
        case METHODID_GET_ANNUAL_YIELD:
          serviceImpl.getAnnualYield((service.StockReq) request,
              (io.grpc.stub.StreamObserver<service.StockAnnualYieldResp>) responseObserver);
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

  private static abstract class StockBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    StockBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return service.StockProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Stock");
    }
  }

  private static final class StockFileDescriptorSupplier
      extends StockBaseDescriptorSupplier {
    StockFileDescriptorSupplier() {}
  }

  private static final class StockMethodDescriptorSupplier
      extends StockBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    StockMethodDescriptorSupplier(String methodName) {
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
      synchronized (StockGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new StockFileDescriptorSupplier())
              .addMethod(getGetStockMethod())
              .addMethod(getGetAnnualYieldMethod())
              .build();
        }
      }
    }
    return result;
  }
}
