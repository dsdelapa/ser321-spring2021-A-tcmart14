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

import yahoofinance.YahooFinance;
import yahoofinance.Stock;
import yahoofinance.quotes.stock.StockQuote;
import yahoofinance.quotes.stock.StockDividend;

class StockImpl extends StockGrpc.StockImplBase {


    public void getStock (StockReq sr, StreamObserver<StockRes> responseObserver) {
        StockRes.Builder response = StockRes.newBuilder();
        String name = null;
        Stock stock = null;
        try {
            stock = YahooFinance.get(sr.getTicker());
            name = stock.getName();
        } catch (Exception e) {
            
        }
        //        double quote = stock.getQuote().getPrice().doubleValue();
        //response.setName(stock.getName());
        //        String name = stock.getName();
        if (name == null) {
            response.setSuccess(false);
            System.out.println("Client request failed...");

        } else {
            response.setName(name);
            response.setQuote(stock.getQuote().getPrice().doubleValue());
            response.setSuccess(true);
            System.out.print("Client Request Stock: ");
            System.out.println(name + "[" + stock.getQuote().getPrice().doubleValue()  + "]");

        }
        //response.setQuote(quote);
    
        StockRes resp = response.build();

        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    public void getAnnualYield (StockReq sr, StreamObserver<StockAnnualYieldResp> responseObserver) {
        StockAnnualYieldResp.Builder response = StockAnnualYieldResp.newBuilder();
        String ticker = sr.getTicker();
        Stock stock = null;
        try {
            stock = YahooFinance.get(ticker);
        } catch (Exception e) {
            stock = null;
        }
        if (stock.getName() == null) {
            response.setSuccess(false);
        } else {
            StockDividend sd = stock.getDividend();
            response.setSuccess(true);
            response.setAnnualYield(sd.getAnnualYield().doubleValue());
            response.setAnnualPercentYield(sd.getAnnualYieldPercent().doubleValue());
        }
        StockAnnualYieldResp resp = response.build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

}
