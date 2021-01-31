package UDP;

import org.json.*;

public class JSONMesgBuilder {


	public static JSONObject startNewMessage() {
		return new JSONObject();
	}

	public static JSONObject addData (JSONObject obj, String key, String message) {
		obj.append(key, message);
		return obj;
	}

	public static String getString (JSONObject obj) {
		return obj.toString();
	}

}