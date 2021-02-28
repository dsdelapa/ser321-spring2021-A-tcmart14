package example.grpcclient;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerMethodDefinition;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import service.*;
import java.util.Stack;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import buffers.RequestProtos.Request;
import buffers.RequestProtos.Request.RequestType;
import buffers.ResponseProtos.Response;

class TipsImpl extends TipsGrpc.TipsImplBase {

    List<Tip> tips = new ArrayList<>();

    public TipsImpl () {
        super();
    }

    public synchronized void read (Empty e, StreamObserver<TipsReadResponse> responseObserver) {
        TipsReadResponse.Builder response = TipsReadResponse.newBuilder();
        for (Tip t : tips) {
            response.addAllTips(tips);
        }
        response.setIsSuccess(true);
        TipsReadResponse resp = response.build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    public synchronized void write (TipsWriteRequest req, StreamObserver<TipsWriteResponse> responseObserver) {
        TipsWriteResponse.Builder response = TipsWriteResponse.newBuilder();

        if (!req.hasTip()) {
            response.setIsSuccess(false);
            response.setError("There was an issue adding tip!");
            TipsWriteResponse resp = response.build();
            responseObserver.onNext(resp);
            responseObserver.onCompleted();
            
        } else {
            Tip newTip = req.getTip();
            System.out.println("New tip from " + newTip.getName() + ": " + newTip.getTip());
            tips.add(newTip);
            response.setIsSuccess(true);
            TipsWriteResponse resp = response.build();
            responseObserver.onNext(resp);
            responseObserver.onCompleted();
        }
    }

}
