package TCP;

import java.net.*;
import java.nio.charset.*;
import TCP.EncoderDecoder;
import TCP.TCPCommunicationHandler;
import org.json.*;
import TCP.JSONMsgBuilder;
import java.util.Scanner;
import java.io.IOException;

public class TCPClient {

	private int port;
	private String hostname;
	private Socket s;
	private TCPCommunicationHandler handler;
	private Scanner scanner;

	public static void main (String[] args) {

		TCPClient client = new TCPClient();
		client.port = 8080;
		client.hostname = "localhost";
		client.start();
	}

	public void start () {
		s = null;
		handler = null;
		scanner = null;
		System.out.println("Game is initializing......");
		try {
			s = new Socket(hostname, port);
			handler = new TCPCommunicationHandler();
			handler.setOutputStream(s.getOutputStream());
			handler.setInputStream(this.s.getInputStream());
			scanner = new Scanner(System.in);
		} catch (Exception e) {
			System.out.println("Could not connect to server and establish communications");
			System.exit(1);
		}
		System.out.println("Game resources initialized");
		try{
			//String recv = handler.recvMessage();
			//System.out.println("Received message!");
			System.out.println("Waiting for message");
			String recv = handler.recvMessage();
			System.out.println(recv);
			System.out.print("Respond: ");
			String response = "todd";
			String jsonResponse = JSONMsgBuilder.getResponse("name", response);
			handler.sendMessage(jsonResponse);
		} catch (IOException e) {
			System.out.println("Problem with input/output. Closing sockets and exiting...");
			//s.close();
			System.exit(1);
		} catch (Exception e) {
			System.out.println("Some other error has occured. Closing program");
			//s.close();
			System.exit(1);
		}
	}

}