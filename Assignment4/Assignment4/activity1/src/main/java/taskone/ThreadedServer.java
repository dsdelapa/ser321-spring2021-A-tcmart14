package taskone;

import java.net.*;
import java.io.*;
import taskone.*;
import org.json.*;

public class ThreadedServer extends Thread {

	private Socket conn;
	private int id;
	private StringList list;

	public ThreadedServer (Socket sock, StringList list, int connectionID) {
		this.conn = sock;
		this.list = list;
		this.id = connectionID;
	}

	public void run () {
		Performer performer = new Performer(this.conn, this.list);
		performer.doPerform();
		try {
            System.out.println("close socket of client ");
            this.conn.close();
        } catch (Exception e) {
            e.printStackTrace(); 
        }            

	}

	public static void main (String[] args) throws IOException {

		int port;
        StringList strings = new StringList();

        if (args.length != 1) {
            // gradle runServer -Pport=9099 -q --console=plain
            System.out.println("Usage: gradle runServer -Pport=9099 -q --console=plain");
            System.exit(1);
        }
        port = -1;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            System.out.println("[Port] must be an integer");
            System.exit(2);
        }
        ServerSocket server = new ServerSocket(port);
        System.out.println("Server Started...");
        int connectionID = 1;
        while (true) {
            System.out.println("Accepting a Request...");
            Socket sock = server.accept();
            connectionID++;
            System.out.println("Incoming connection with ID: " + connectionID);

            //Performer performer = new Performer(sock, strings);
            //performer.doPerform();
            ThreadedServer serverThread =  new ThreadedServer(sock, strings, connectionID);
            serverThread.start();
        }

	}

}