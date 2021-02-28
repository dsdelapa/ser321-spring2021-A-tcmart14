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

class CalcImpl extends CalcGrpc.CalcImplBase {

    ArrayList<Integer> numbers = new ArrayList<>();

    public CalcImpl () {
        super();
    }

    public void add (CalcRequest req, StreamObserver<CalcResponse> responseObserver) {
        CalcResponse.Builder response = CalcResponse.newBuilder();
        double answer = 0;
        List<Double> numbers = req.getNumList();
        for (Double d : numbers) {
            answer = answer + d;
        }
        response.setSolution(answer);
        //        response.solution = answer;

        CalcResponse resp = response.build();

        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    public void multiply (CalcRequest req, StreamObserver<CalcResponse> responseObserver) {
        CalcResponse.Builder response = CalcResponse.newBuilder();
        double answer = 0;
        List<Double> numbers = req.getNumList();
        for (Double d : numbers) {
            answer = answer * d;
        }
        response.setSolution(answer);
        //        response.solution = answer;

        CalcResponse resp = response.build();

        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    public void subtract (CalcRequest req, StreamObserver<CalcResponse> responseObserver) {
        CalcResponse.Builder response = CalcResponse.newBuilder();
        double answer = 0;
        List<Double> numbers = req.getNumList();
        answer = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            answer = answer - numbers.get(i);
        }
        response.setSolution(answer);
        //        response.solution = answer;

        CalcResponse resp = response.build();

        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    public void divide (CalcRequest req, StreamObserver<CalcResponse> responseObserver) {
        CalcResponse.Builder response = CalcResponse.newBuilder();
        double answer = 0;
        List<Double> numbers = req.getNumList();
        answer = numbers.get(0);
        double denom = 0;
        for (int i = 1; i < numbers.size(); i++) {
            denom = denom + numbers.get(i);
        }
        answer = answer / denom;

        response.setSolution(answer);
        //        response.solution = answer;

        CalcResponse resp = response.build();

        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

}
