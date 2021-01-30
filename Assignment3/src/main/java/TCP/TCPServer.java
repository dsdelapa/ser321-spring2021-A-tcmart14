package TCP;

import java.net.*;
import java.io.*;
import TCP.TCPCommunicationHandler;
import TCP.JSONMsgBuilder;
import TCP.EncoderDecoder;
import java.nio.charset.*;
import java.util.Map;
import TCP.JSONMessageParser;
import org.json.*;
import java.util.concurrent.TimeUnit;

public class TCPServer {

	private int port;
	private ServerSocket ss;
	private Socket client;
	private Charset charset;

	public static void main (String[] args) {
		TCPServer server = new TCPServer();
		server.port = 8080;
		server.run();
	}

	public void run () {
		charset = StandardCharsets.UTF_8;
		//ObjectOutputStream out = null;
		//ObjectInputStream in = null;
		TCPCommunicationHandler handler = new TCPCommunicationHandler();
		try {
			ss = new ServerSocket(port);
		} catch (Exception e) {
			System.out.println("Issue binding to port");
			System.exit(1);
		}
		try {
			client = ss.accept();
		} catch (Exception e) {
			System.out.println("Issue getting client connection");
			System.exit(1);
		}
		try {
			//in = new ObjectInputStream(client.getInputStream());
			//out = new ObjectOutputStream(client.getOutputStream());
			//out.flush();
			//in.flush();
			handler.setOutputStream(client.getOutputStream());
			handler.setInputStream(client.getInputStream());

			System.out.println("Start delay.....");
			TimeUnit.SECONDS.sleep(3);
			System.out.println("End Delay.....");

		} catch (Exception e) {
			System.out.println("Issue creating streams from client");
		}

		try {
			System.out.println("Sending first message");
			handler.sendMessage(this.startMessage());
			String recv = handler.recvMessage();
			JSONObject obj = JSONMessageParser.getJSONObject(recv);
			if (obj.getString("type").equals("name")) {
				System.out.println("Received: " + obj.getString("name"));	
				handler.setClientName(obj.getString("name"));	
			}
			boolean complete = false;

			/*
			recv = handler.recvMessage();
			obj = JSONMessageParser.getJSONObject(recv);
			if (obj.getString("type").equals("number")) {
				// handler number and set game logic
			}
			*/



		} catch (IOException e) {
			System.out.println("Not able to write to client");
			System.exit(1);
		} catch (Exception e) {
			System.out.println("Issue happened when handling communications with client");
			System.exit(1);
		}

	}

	public String startMessage() {
		JSONMsgBuilder msg = new JSONMsgBuilder();
		String message = msg.getStartHeader();
		return message;
	}
}