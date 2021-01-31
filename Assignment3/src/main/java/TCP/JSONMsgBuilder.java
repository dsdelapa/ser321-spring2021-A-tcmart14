package TCP;

import org.json.*;
import TCP.JSONMessageParser;

public class JSONMsgBuilder {

	private String msg;
	//private String header;
	//private String payload;
	private JSONObject header;
	private JSONObject payload;

	public String getStartHeader () {
		String message;
		JSONObject json = new JSONObject()
			.put("question", "What is your name?").put("type", "question");
		message = json.toString();
		return message;
	}

	public static String getResponse (String type, String response) {
		JSONObject json;
		if (type.equals("answer")) {
			json = new JSONObject()
				.put(type, response).put("type", type);
		} else if (type.equals("name")) {
			json = new JSONObject()
				.put(type, response).put("type", type);
		} else if (type.equals("number")) {
			json = new JSONObject()
				.put(type, response).put("type", type);
		} else if (type.equals("message")) {
			json = new JSONObject()
				.put(type, response).put("type", type);
		} else if (type.equals("question")) {
			json = new JSONObject()
				.put(type, response).put("type", type);
		} else {
			return null;
		}
		return json.toString();
	}

	public static String appendResponse (String source, String newKey, String newValue) {
		JSONObject obj = JSONMessageParser.getJSONObject(source);
		obj.put(newKey, newValue);
		return obj.toString();
	}

}