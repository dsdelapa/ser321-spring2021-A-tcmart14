package SimplePeerToPeer;

import org.json.*;
import java.util.Set;
import java.util.HashMap;
import java.util.Random;

//peer.commLeader("{'type': 'join', 'username': '"+ username +"','ip':'" + serverThread.getHost() + "','port':'" + serverThread.getPort() + "'}");

public class JSONMessageBuilder {

	public static String getJoinMessage (String username, ServerThread si) {
		String msg = "{'type': 'join', 'username': '"+ username +"','ip':'" + si.getHost() + "','port':'" + si.getPort() + "'}";
		return msg;
	}

	public static String message (String username, String mesg) {
		String msg;
		msg = "{'type': 'message', 'username': '"+ username +"','message':'" + mesg + "'}";
		return msg;
	}
	public static String removePeer (SocketInfo si) {
		String msg = "{'type': 'removePeer', 'ip': '" + si.getHost() + "', 'port': '" + si.getPort() +  "'}";
		System.out.println("Message to remove a peer: " + msg);
		return msg;
	}

	public static String peerListMessage (String l, SocketInfo s) {
		//JSONArray arr = new JSONArray();
		//String tuples[] = l.split(" ");
		//for (String s : tuples) {
		//	arr.put(s);
		//}
		String temp = s.getHost()+":"+s.getPort();
		l = l + temp;

		JSONObject obj = new JSONObject();
		obj.put("type", "peerList");
		obj.put("peerList", l);
		return obj.toString();
	}

	public static String askForLeader (String host, String port) {
		JSONObject obj = new JSONObject();
		obj.put("type", "ask");
		obj.put("ask", "can I has leader?");
		obj.put("ip", host);
		obj.put("port", port);
		return obj.toString();
	}

	public static String askAddJoke (String joke) {
		JSONObject obj = new JSONObject();
		obj.put("type", "ask");
		obj.put("ask", "joke");
		obj.put("joke", joke);
		return obj.toString();
	}

	public static String voteMessage (boolean vote) {
		JSONObject obj = new JSONObject();
		obj.put("type", "vote");
		if (vote) {
			obj.put("vote", "yes");
		} else {
			obj.put("vote", "no");
		}
		return obj.toString();
	}

	public static String notify (String message) {
		JSONObject obj = new JSONObject();
		obj.put("type", "alert");
		obj.put("alert", message);
		return obj.toString();
	}

	public static String candidacy (String host, int port, int number) {
		JSONObject obj = new JSONObject();
		obj.put("type", "candidacy");
		obj.put("ip", host);
		obj.put("port", Integer.toString(port));
		obj.put("number", Integer.toString(number));
		return obj.toString();
	}

	public static Integer getRandomNumber () {
		Random r = new Random();
		return r.nextInt();
	}

	public static String announceLeader (String host, String port) {
		JSONObject obj = new JSONObject();
		obj.put("type", "alert");
		obj.put("alert", "newLeader");
		obj.put("ip", host);
		obj.put("port", port);
		return obj.toString();
	}

	public static String pollForJoke (String joke) {
		JSONObject obj = new JSONObject();
		obj.put("type", "pollJoke");
		obj.put("joke", joke);
		return obj.toString();
	}

	public static String voteJoke () {
		JSONObject obj = new JSONObject();
		obj.put("type", "voteJoke");
		obj.put("voteJoke", "yes");
		return obj.toString();
	}

	public static String addJoke (String msg) {
		JSONObject obj = new JSONObject();
		obj.put("type", "addJoke");
		obj.put("joke", msg);
		return obj.toString();
	}

}