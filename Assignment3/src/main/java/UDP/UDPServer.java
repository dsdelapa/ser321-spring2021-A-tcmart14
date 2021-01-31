package UDP;

import java.io.*;
import java.net.*;
import org.json.*;
import UDP.*;

public class UDPServer {

	/**
	* state represents where the program is in execution
	* state 0 is asking for client name
	* state 1 is waiting for client
	* state 2 is asking client for question number
	* state 3 is received client number;
	*/

	public static void main (String[] args) throws IOException {
		int initPort;

		if (args.length != 1) {
			initPort = 8080;
		} else {
			initPort = Integer.parseInt(args[0]);
		}

		try {
			//ss = null;
			DatagramSocket ss = null;
			try {
				ss = new DatagramSocket();
				ss.bind(new InetSocketAddress(initPort));
			} catch (Exception e) {
				System.out.println("Issue bidning to port");
				System.exit(1);
			}

			 DatagramPacket recvP = null;
			 DatagramPacket sendP = null;
			 int state = 0;
		 	 InetAddress ip = null;
			 int clientPort = 0;

			JSONObject data;
			String recvM;
			String clientName;
			
			while (state != 5) {
				recvData(clientPort, ip, recvP, ss); // throw away data to get client informtion
				data = JSONMesgBuilder.startNewMessage();
				data = JSONMesgBuilder.addData(data, "type", "message");
				data = JSONMesgBuilder.addData(data, "question", "What is your name");
				sendData(clientPort, ip, sendP, data, ss);
				recvM = recvData(clientPort, ip, recvP, ss);
				data = JSONParser.getJSON(recvM);
				if (data.getString("TYPE").equals("ANSWER")) {
					clientName = data.getString("ANSWER");
				} else {
					System.out.println("Something went wrong receiving message");
				}

			}

			ss.close();

		} catch (Exception e) {
			System.out.println("Could not bind to port. Exiting");
			System.exit(1);
		}
		
	}	

	public static void sendData (int clientPort, InetAddress ip, DatagramPacket sendP, JSONObject message, DatagramSocket ss) throws Exception {
		String mess = JSONMesgBuilder.getString(message);
		String capMessage = mess.toUpperCase();
		byte[] sendData = capMessage.getBytes();
		sendP = new DatagramPacket(sendData, sendData.length, ip, clientPort);
		ss.send(sendP);
		
	}

	public static String recvData (int clientPort, InetAddress ip, DatagramPacket recvP, DatagramSocket ss) throws Exception {
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