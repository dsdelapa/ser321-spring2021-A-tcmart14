package example.grpcclient;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;
import service.*;
import test.TestProtobuf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import java.util.ArrayList;
import java.util.List;

/**
 * Client that requests `parrot` method from the `EchoServer`.
 */
public class Client {
  private final EchoGrpc.EchoBlockingStub blockingStub;
  private final JokeGrpc.JokeBlockingStub blockingStub2;
  private final RegistryGrpc.RegistryBlockingStub blockingStub3;
    private final CalcGrpc.CalcBlockingStub blockingStub4;
    private final TipsGrpc.TipsBlockingStub blockingStub5;
    private final StockGrpc.StockBlockingStub blockingStub6;

  /** Construct client for accessing server using the existing channel. */
  public Client(Channel channel, Channel regChannel) {
    // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's
    // responsibility to
    // shut it down.

    // Passing Channels to code makes code easier to test and makes it easier to
    // reuse Channels.
    blockingStub = EchoGrpc.newBlockingStub(channel);
    blockingStub2 = JokeGrpc.newBlockingStub(channel);
    blockingStub3 = RegistryGrpc.newBlockingStub(regChannel);
    blockingStub4 = CalcGrpc.newBlockingStub(channel); // may be an issue
    blockingStub5 = TipsGrpc.newBlockingStub(channel);
    blockingStub6 = StockGrpc.newBlockingStub(channel);
  }

  public void askServerToParrot(String message) {
    ClientRequest request = ClientRequest.newBuilder().setMessage(message).build();
    ServerResponse response;
    try {
      response = blockingStub.parrot(request);
    } catch (Exception e) {
      System.err.println("RPC failed: " + e.getMessage());
      return;
    }
    System.out.println("Received from server: " + response.getMessage());
  }

  public void askForJokes(int num) {
    JokeReq request = JokeReq.newBuilder().setNumber(num).build();
    JokeRes response;

    try {
      response = blockingStub2.getJoke(request);
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
    System.out.println("Your jokes: ");
    for (String joke : response.getJokeList()) {
      System.out.println("--- " + joke);
    }
  }

  public void setJoke(String joke) {
    JokeSetReq request = JokeSetReq.newBuilder().setJoke(joke).build();
    JokeSetRes response;

    try {
      response = blockingStub2.setJoke(request);
      System.out.println(response.getOk());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public void getServices() {
    GetServicesReq request = GetServicesReq.newBuilder().build();
    ServicesListRes response;
    try {
      response = blockingStub3.getServices(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public void findServer(String name) {
    FindServerReq request = FindServerReq.newBuilder().setServiceName(name).build();
    SingleServerRes response;
    try {
      response = blockingStub3.findServer(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public void findServers(String name) {
    FindServersReq request = FindServersReq.newBuilder().setServiceName(name).build();
    ServerListRes response;
    try {
      response = blockingStub3.findServers(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 5) {
      System.out
          .println("Expected arguments: <host(String)> <port(int)> <regHost(string)> <regPort(int)> <message(String)>");
      System.exit(1);
    }
    int port = 9099;
    int regPort = 9003;
    String host = args[0];
    String regHost = args[2];
    String message = args[4];
    try {
      port = Integer.parseInt(args[1]);
      regPort = Integer.parseInt(args[3]);
    } catch (NumberFormatException nfe) {
      System.out.println("[Port] must be an integer");
      System.exit(2);
    }

    // Create a communication channel to the server, known as a Channel. Channels
    // are thread-safe
    // and reusable. It is common to create channels at the beginning of your
    // application and reuse
    // them until the application shuts down.
    String target = host + ":" + port;
    ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
        // Channels are secure by default (via SSL/TLS). For the example we disable TLS
        // to avoid
        // needing certificates.
        .usePlaintext().build();

    String regTarget = regHost + ":" + regPort;
    ManagedChannel regChannel = ManagedChannelBuilder.forTarget(regTarget).usePlaintext().build();
    try {

      // ##############################################################################
      // ## Assume we know the port here from the service node it is basically set through Gradle
      // here.
      // In your version you should first contact the registry to check which services
      // are available and what the port
      // etc is.

      /**
       * Your client should start off with 
       * 1. contacting the Registry to check for the available services
       * 2. List the services in the terminal and the client can
       *    choose one (preferably through numbering) 
       * 3. Based on what the client chooses
       *    the terminal should ask for input, eg. a new sentence, a sorting array or
       *    whatever the request needs 
       * 4. The request should be sent to one of the
       *    available services (client should call the registry again and ask for a
       *    Server providing the chosen service) should send the request to this service and
       *    return the response in a good way to the client
       * 
       * You should make sure your client does not crash in case the service node
       * crashes or went offline.
       */

      // Just doing some hard coded calls to the service node without using the
      // registry
      // create client
      Client client = new Client(channel, regChannel);

      // call the parrot service on the server
      client.askServerToParrot(message);

      // ask the user for input how many jokes the user wants
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


      // ############### Contacting the registry just so you see how it can be done

      // Comment these last Service calls while in Activity 1 Task 1, they are not needed and wil throw issues without the Registry running
      // get thread's services
      client.getServices();

      // get parrot
      client.findServer("services.Echo/parrot");
      
      // get all setJoke
      client.findServers("services.Joke/setJoke");

      // get getJoke
      client.findServer("services.Joke/getJoke");

      // does not exist
      client.findServer("random");

      while (true) {
          String option = "";
          System.out.println("Please select an option");
          System.out.println("\t[1] Joke");
          System.out.println("\t[2] Calc");
          System.out.println("\t[3] Tips");
          System.out.println("\t[4] Markets");
          System.out.print(">> ");
          option = reader.readLine();

          switch (option) {
                  case "1" :
      // Reading data using readLine
                      System.out.println("How many jokes would you like?"); // NO ERROR handling of wrong input here.
                      String num = reader.readLine();

      // calling the joked service from the server with num from user input
                      client.askForJokes(Integer.valueOf(num));

      // adding a joke to the server
                      client.setJoke("I made a pencil with two erasers. It was pointless.");

      // showing 6 joked
                      client.askForJokes(Integer.valueOf(6));
                      break;
                  case "2" :
                      client.doCalc();
                      break;
                  case "3" :
                      client.doTips();
                      // do stuff
                      break;
                  case "4" :
                      client.doMarket();
                      break;
                  default :
                      System.out.println("Not valid, try again...");
                      break;
          }
      }

    } finally {
      // ManagedChannels use resources like threads and TCP connections. To prevent
      // leaking these
      // resources the channel should be shut down when it will no longer be used. If
      // it may be used
      // again leave it running.
      channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
      regChannel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }
  }

    public void doMarket () {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String opt = "";
        System.out.println("Please select an option");
        System.out.println("\t[1] Get Stock price");
        System.out.println("\t[2] Get Stock annual yield");
        System.out.print(">> ");
        try {
            opt = reader.readLine();
        } catch (IOException e) {
            System.out.println("Not the correct input!");
            return;
        }
        switch (opt) {
        case "1" :
            doStock();
            break;
        case "2" :
            doAnnual();
            break;
        default :
            System.out.println("Not an expected input... Back to main menu");
            break;
        }
    } 

    public void doStock () {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String tickerSymbol = "";
        try {
            System.out.println("Enter in a stock ticker symbol");
            System.out.print(">> ");
            tickerSymbol = reader.readLine();
            StockReq request = StockReq.newBuilder().setTicker(tickerSymbol).build();
            StockRes rep = blockingStub6.getStock(request);
            if (rep.getSuccess()) {
                //                double quote = rep.getQuote();
                //                String name = rep.getName();
                System.out.println("Name: " + rep.getName());
                System.out.println("Price: " + rep.getQuote());
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("Request failed somewhere");
        }
    }

    public void doAnnual () {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String ticker = "";
        System.out.println("Enter stock symbol");
        System.out.print(">> ");
        try {
            ticker = reader.readLine();
        } catch (IOException e) {
            System.out.println("Not a valid input.");
        }
        try {
            StockReq r = StockReq.newBuilder().setTicker(ticker).build();
            StockAnnualYieldResp resp = blockingStub6.getAnnualYield(r);
            if (!resp.getSuccess()) {
                throw new Exception();
            }
            double annualYield = resp.getAnnualYield();
            double percent = resp.getAnnualPercentYield();
            System.out.println("For ticker: " + ticker);
            System.out.println("\tAnnual Yield: " + annualYield);
            System.out.println("\tPercent Annual: " + percent);
        } catch (Exception e) {
            System.out.println("An issue occured with the request");
        }
    }

    public void doTips () {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Pick an option for tips:");
            System.out.println("\t[1] Read tips");
            System.out.println("\t[2] Add a new tip");
            System.out.print(">> ");
            String opt = reader.readLine();
            switch (opt) {
                case "1" :
                    readTips();
                    break;
                case "2" :
                    addNewTip();
                    break;
                default :
                    System.out.println("Not a valid option, going back to the main menu");
            }
        } catch (IOException e) {
            System.err.println("Issue taking input...");
        } catch (Exception a) {
            System.err.println("Issue completing the task....");
        }
    }

    public void readTips () throws Exception {
        Empty e = service.Empty.newBuilder().build();
        TipsReadResponse response = blockingStub5.read(e);
        if (!response.getIsSuccess()) {
            throw new Exception();
        }
        List<Tip> tips = response.getTipsList();
        for (Tip t : tips) {
            System.out.println(t.getTip());
        }
    }

    public void addNewTip () throws Exception {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Type in new tip to add");
            System.out.print(">> ");
            String tip = reader.readLine();
            Tip t = Tip.newBuilder().setName("").setTip(tip).build();
            //Tip t = new Tip();
            //t.setTip(tip);
            //t.setName("name");
            TipsWriteRequest req = TipsWriteRequest.newBuilder().setTip(t).build();
            TipsWriteResponse  resp = blockingStub5.write(req);
            if (resp.getIsSuccess()) {
                System.out.println("Added a new tip!");
            } else {
                new Exception();
            }
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void doCalc () {
        try {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Double> list = getNumbers();
        CalcResponse rep = null;
        System.out.println("Pick an operation [add, sub, mult, div] : ");
        System.out.print(">> ");
        String option = reader.readLine();
        CalcRequest req = null;
        switch (option) {
        case "add":
            req = CalcRequest.newBuilder().addAllNum(list).build();
            rep = blockingStub4.add(req);
            break;
        case "sub" :
            req = CalcRequest.newBuilder().addAllNum(list).build();
            rep = blockingStub4.subtract(req);
            break;
        case "mult" :
            req = CalcRequest.newBuilder().addAllNum(list).build();
            rep = blockingStub4.multiply(req);
            break;
        case "div" :
            req = CalcRequest.newBuilder().addAllNum(list).build();
            rep = blockingStub4.divide(req);
            break;
        default :
            System.out.println("Wrong option choice, try again...");
            doCalc();
        }
        double ans = rep.getSolution();
        System.out.println("Answer is: "  + ans);

        } catch (IOException e) {
            System.out.println("Wrong input type");
        }
    }

    public static ArrayList<Double> getNumbers () {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Double> temp = new ArrayList<>();
        String numberReader = "";
        while (true) {
            System.out.println("Enter a number [to stop put in any non integer] : ");

            try {
                String num = reader.readLine();
                temp.add(Double.parseDouble(num));
            } catch (IOException a) {
                System.out.println("Something went wrong with the input, try again.....");
                continue;
            } catch (Exception e) {
                if (temp.size() < 2) {
                    System.out.println("Not enough numbers, try again....");
                    temp = new ArrayList<>();
                    continue;
                } else {
                    return temp;
                }
            }
        }
        //return temp;
    }


}
