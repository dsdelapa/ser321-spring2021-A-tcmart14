package UDP;

import java.net.*;
import java.io.*;
import org.json.*;
import UDP.*;

public class UDPClient {

	private DatagramSocket ds;
	private DatagramPacket recvP;
	private DatagramPacket sendP;
	private InetAddress ip;
	private String host;
	private int port;
	private BufferedReader buffR;

	public UDPClient (int initPort, String initHost) {
		this.port = initPort;
		this.host = initHost;
	}

	public static void main (String[] args) {
		int initPort;
		String initHost = "localhost";

		if (args.length != 2) {
			initPort = 8080;
		} else {
			initPort = Integer.parseInt(args[1]);
			initHost = args[0];
		}

		UDPClient client = new UDPClient(initPort, initHost);
		client.run();
	}

	public void run() {

		buffR = new BufferedReader(new InputStreamReader(System.in));
		try {
			ds = new DatagramSocket();
			ip = InetAddress.getByName(this.host);
		} catch (Exception e) {
			System.out.println("Can not resolve host name");
			System.exit(1);
		}
		JSONObject data;
		String raw;

		while (true) {
			try {
				this.pollForHost();
				raw = this.recvData();
				data = JSONParser.getJSON(raw);
				if (data.getString("TYPE").equals("QUESTION")) {
					System.out.println(data.getString("QUESTION"));
					raw = buffR.readLine();
					data = JSONMesgBuilder.startNewMessage();
					data = JSONMesgBuilder.addData(data, "type", "answer");
					data = JSONMesgBuilder.addData(data, "answer", raw);
					//raw = JSONMesgBuilder.getString(data);
					this.sendData(data);
				}
			} catch (Exception e) {
				System.out.println("Error occued");
				continue;
			}
		}

	}

	public void sendData (JSONObject message) throws Exception {
		String mess = JSONMesgBuilder.getString(message);
		String capMessage = mess.toUpperCase();
		byte[] sendData = capMessage.getBytes();
		sendP = new DatagramPacket(sendData, sendData.length, this.ip, this.port);
		ds.send(sendP);
	}

	public void pollForHost () throws Exception {
		String message = "hello";
		byte[] sendData = message.getBytes();
		sendP = new DatagramPacket(sendData, sendData.length, this.ip, this.port);
		ds.send(sendP);
	}

	public String recvData () throws Exception {
		byte[] recv = new byte[1024];
		recvP = new DatagramPacket(recv, recv.length);
		String data = new String(recvP.getData());
		return data;
	}

}