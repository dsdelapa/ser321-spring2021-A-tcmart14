package TCP;

import java.net.*;
import java.nio.charset.*;
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
		client.hostname = "192.168.0.55";
		client.start();
	}

	public void start () {
		this.s = null;
		this.handler = null;
		this.scanner = null;
		System.out.println("Game is initializing......");
		try {
			scanner = new Scanner(System.in);
			s = new Socket(hostname, port);
			handler = new TCPCommunicationHandler();
			handler.setInputStream(s.getInputStream());
			handler.setOutputStream(s.getOutputStream());

			System.out.println("Waiting for message");
			if (s == null || handler == null) {
				System.out.println("something failed");
				throw new Exception();
			}
			String recv;
			recv = handler.recvMessage();
			System.out.print("Respond: ");
			String response = scanner.nextLine();
			String jsonResponse = JSONMsgBuilder.getResponse("name", response);
			handler.sendMessage(jsonResponse);
		} catch (Exception e) {
			System.out.println("Could not connect to server and establish communications");
			System.exit(1);
		}
		System.out.println("Game resources initialized");
		/*try{
			//String recv = handler.recvMessage();
			//System.out.println("Received message!");
			System.out.println("Waiting for message");
			if (s == null || handler == null) {
				System.out.println("something failed");
				throw new Exception();
			}
			String recv;
			recv = handler.recvMessage();
			System.out.print("Respond: ");
			String response = scanner.nextLine();
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
		}*/
	}

}