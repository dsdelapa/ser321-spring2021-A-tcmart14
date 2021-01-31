package UDP;

import java.io.*;
import java.net.*;
import org.json.*;
import UDP.*;

public class UDPServer {

	private DatagramSocket ss;
	private DatagramPacket recvP;
	private DatagramPacket sendP;
	private int state;
	private InetAddress ip;
	private int clientPort;

	/**
	* state represents where the program is in execution
	* state 0 is asking for client name
	* state 1 is waiting for client
	* state 2 is asking client for question number
	* state 3 is received client number;
	*/

	public static void main (String[] args) {
		int initPort;

		if (args.length != 1) {
			initPort = 8080;
		} else {
			initPort = Integer.parseInt(args[0]);
		}

		UDPServer server = new UDPServer();
		server.run(initPort);
	}	

	public void run (int port) {
		try {
			ss = new DatagramSocket(port);

			recvP = null;
			sendP = null;
			ip = null;
			clientPort = 0;
			JSONObject data;
			state = 0;
			String recvM;
			String clientName;
			
			while (state != 5) {
				this.recvData(); // throw away data to get client informtion
				data = JSONMesgBuilder.startNewMessage();
				data = JSONMesgBuilder.addData(data, "type", "message");
				data = JSONMesgBuilder.addData(data, "question", "What is your name");
				this.sendData(data);
				recvM = this.recvData();
				data = JSONParser.getJSON(recvM);
				if (data.getString("TYPE").equals("ANSWER")) {
					clientName = data.getString("ANSWER");
				} else {
					System.out.println("Something went wrong receiving message");
				}

			}


		} catch (Exception e) {
			System.out.println("Could not bind to port. Exiting");
			System.exit(1);
		}

	}

	public void sendData (JSONObject message) throws Exception {
		String mess = JSONMesgBuilder.getString(message);
		String capMessage = mess.toUpperCase();
		byte[] sendData = capMessage.getBytes();
		sendP = new DatagramPacket(sendData, sendData.length, ip, clientPort);
		ss.send(sendP);
		
	}

	public String recvData () throws Exception {
		byte[] recv = new byte[1024];
		recvP = new DatagramPacket(recv, recv.length);
		if (ip == null) {
			ip = recvP.getAddress();
		}
		if (clientPort == 0) {
			clientPort = recvP.getPort();
		}
		String data = new String(recvP.getData());
		return data;
	}

}