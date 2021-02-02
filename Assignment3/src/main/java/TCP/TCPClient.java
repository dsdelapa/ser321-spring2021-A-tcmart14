package TCP;

import java.net.*;
import java.nio.charset.*;
import TCP.TCPCommunicationHandler;
import org.json.*;
import TCP.JSONMsgBuilder;
import java.util.Scanner;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import TCP.JSONMessageParser;
import java.util.InputMismatchException;

public class TCPClient {

	private int port;
	private String hostname;
	private Socket s;
	private TCPCommunicationHandler handler;
	private Scanner scanner;

	public static void main (String[] args) {

		String initHost;
		int initPort;

		if (args.length != 2) {
			initHost = "localhost";
			initPort = 8080;
		} else {
			initPort = Integer.parseInt(args[1]);
			initHost = args[0];
		}

		TCPClient client = new TCPClient();
		client.port = initPort;
		client.hostname = initHost;
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
			if (s == null || handler == null) {
				System.out.println("something failed");
				throw new Exception();
			}
			String recv;
			recv = handler.recvMessage();
			JSONObject msgObj = JSONMessageParser.getJSONObject(recv);
			System.out.println(msgObj.getString("question"));
			String response = scanner.nextLine();
			//System.out.println(response);
			String jsonResponse = JSONMsgBuilder.getResponse("name", response);
			//System.out.println(jsonResponse);
			handler.sendMessage(jsonResponse);
			//System.out.println("response sent with name");
			boolean playAgain = true;
			while (playAgain) {
				recv = handler.recvMessage();
				msgObj = JSONMessageParser.getJSONObject(recv);
				boolean correct = false;
				while (!correct) {
					try {
						System.out.print("Enter the number of questions you want: ");
						scanner = new Scanner(System.in);
						int numQ = scanner.nextInt();
						String temp = Integer.toString(numQ);
						String tempResponse = JSONMsgBuilder.getResponse("number", temp);
						handler.sendMessage(tempResponse);
						correct = true;
					} catch (InputMismatchException e) {
						continue;
					} catch (IOException e) {
						System.out.println("issues communicating with server, disconnecting");
						System.exit(1);
					}
				}
				recv = handler.recvMessage();
				msgObj = JSONMessageParser.getJSONObject(recv);
				if (msgObj.getString("type").equals("message")) {
					if (msgObj.getString("message").equals("ready")) {
						System.out.println("Type start when ready to start game: ");
						scanner = new Scanner(System.in);
						//response = new String();
						response = scanner.nextLine();
						//System.out.println("response here: " + response);
						jsonResponse = JSONMsgBuilder.getResponse("message", response);
						handler.sendMessage(jsonResponse);
					}
				} else {
					throw new Exception();
				}

				boolean win = false;
				boolean done = false;
				while (!done) {
					recv = handler.recvMessage();
					msgObj = JSONMessageParser.getJSONObject(recv);
					if (msgObj.getString("type").equals("question")) {
						System.out.println(msgObj.getString("question"));
						scanner = new Scanner(System.in);
						response = scanner.nextLine();
						jsonResponse = JSONMsgBuilder.getResponse("answer", response);
						handler.sendMessage(jsonResponse);
					} else if (msgObj.getString("type").equals("message")) {
						if (msgObj.getString("message").equals("win")) {
							win = true;
							done = true;
							System.out.println("IMAGE");
						} else if (msgObj.getString("message").equals("lose")) {
							win = false;
							done = true;
							System.out.println("IMAGE");
						}
					}
				}

				recv = handler.recvMessage();
				msgObj = JSONMessageParser.getJSONObject(recv);
				if (msgObj.getString("type").equals("question")) {
					System.out.println(msgObj.getString("question"));
					scanner = new Scanner(System.in);
					response = scanner.nextLine();
					if (response.equals("yes")) {
						playAgain = true;
					} else {
						playAgain = false;
					}
					jsonResponse = JSONMsgBuilder.getResponse("answer", response);
					handler.sendMessage(jsonResponse);
				}		
				
			}
		} catch (IOException e) {
			System.out.println("Could not connect to server and establish communications");
			System.exit(1);
		} catch (Exception e) {
			System.out.println("Issue handling data");
			System.exit(1);
		}
	}

}
