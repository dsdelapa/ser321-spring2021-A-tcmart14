package client;

import java.net.*;
import java.io.*;

import org.json.*;

import buffers.RequestProtos.Request;
import buffers.ResponseProtos.Response;
import buffers.ResponseProtos.Entry;

import java.util.*;
import java.util.stream.Collectors;

class SockBaseClient {

    private Socket serverSock;
    private OutputStream out;
    private InputStream in;
    private int i1;
    private int i2;
    private int port; 
    private BufferedReader stdin;

    public SockBaseClient () {
        serverSock = null;
        out = null;
        in = null;
        i1 = 0;
        i2 = 0;
        port = 9099;
        stdin = null;
    }

    private Request askLeaderMessageBuilder () {
        Request op = Request.newBuilder()
            .setOperationType(Request.OperationType.LEADER)
            .build();
        return op;
    }

    private Request askEnterGameMessageBuilder () {
        Request op = Request.newBuilder()
            .setOperationType(Request.OperationType.NEW)
            .build();
        return op;
    }

    private void leaderBoard() {
        try {
            Request op = askLeaderMessageBuilder();
            op.writeDelimitedTo(out);
            Response response = Response.parseDelimitedFrom(in);
        } catch (Exception e) {
            // fill later
        }
    }

    private void playGame () {
        Request op;
        boolean cont = true;
        try {
            op = askEnterGameMessageBuilder();
            op.writeDelimitedTo(out);
        } catch (IOException e) {
            System.out.println("Issue writing to client");
            return;
        }
        while (cont) {
            try {
                Response response = Response.parseDelimitedFrom(in);
                if (response.getResponseType() == Response.ResponseType.TASK) {
                    System.out.println("###### IMAGE######\n" + response.getImage());
                    System.out.println("Do the following task: " + response.getTask());
                    System.out.print("Enter answer to task:");
                    Scanner scan = new Scanner(System.in);
                    String line = scan.nextLine();
                    op = answerResponse(line);
                    op.writeDelimitedTo(out);
                
                } else if (response.getResponseType() == Response.ResponseType.WON) {
                    System.out.println("Won! Going back to main menu");
                    cont = false;
                } else {
                    cont = false;
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("There was an issue");
                cont = false;
                break;
            }
         }
    }

    private Request answerResponse (String string) {
        Request op = Request.newBuilder()
            .setOperationType(Request.OperationType.ANSWER)
            .setAnswer(string)
            .build();
        return op;
    }

    private void run (String host, int port) {
         // Ask user for username
        System.out.println("Please provide your name for the server. ( ͡❛ ͜ʖ ͡❛)");
        String strToSend = "";
        try {
            stdin = new BufferedReader(new InputStreamReader(System.in));
            strToSend = stdin.readLine();
        } catch (Exception e) {
            System.out.println("Issue reading from the standard input....\nExiting.....");
            System.exit(1);
        }

        // Build the first request object just including the name
        Request op = Request.newBuilder()
                .setOperationType(Request.OperationType.NAME)
                .setName(strToSend).build();
        Response response;
        try {
            // connect to the server
            serverSock = new Socket(host, port);

            // write to the server
            out = serverSock.getOutputStream();
            in = serverSock.getInputStream();

            op.writeDelimitedTo(out);

            boolean cont = true;
            // read from the server
            while (cont) {
                response = Response.parseDelimitedFrom(in);

            // print the server response. 
                System.out.println(response.getGreeting());

            
                int choice;
                try {
                    strToSend = stdin.readLine();
                    choice = Integer.parseInt(strToSend);
                    if (!(choice >= 0 && choice <= 2)) // 0 is for quitting
                        throw new Exception();
                } catch (Exception e) {
                    System.out.println("Wrong choice! Try again.");
                    continue;
                }

                switch (choice) {
                    case 0: // quit case
                        cont = false;
                        break;
                    case 1:
                        leaderBoard();
                        break;
                    case 2:
                        playGame();
                        break;
                    default: // due to previous loops, should not hit, but if it does, will quit
                        System.out.println("exiting loop");
                        cont = false;
                        break;
                }
                System.out.println("Next iteration beginning");
            }
        } catch (Exception e) {
            System.out.println("An error has occured. Exiting.....");
            System.exit(1);
        } finally {

        }
    }

    public static void main (String args[]) throws Exception {

        SockBaseClient client = new SockBaseClient();
        int port = -1;
        // Make sure two arguments are given
        if (args.length != 2) {
            System.out.println("Expected arguments: <host(String)> <port(int)>");
            System.exit(1);
        }
        String host = args[0];
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException nfe) {
            System.out.println("[Port] must be integer");
            System.exit(2);
        }

        client.run(host, port);

        
    }
}


