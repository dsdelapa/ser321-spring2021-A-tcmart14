package TCP;
import java.nio.charset.*;

public class EncoderDecoder {

	// class only supports the use of UTF_8
	// will not handle any parsing or creating of JSON

	public static byte[] encodeString (String message) {
		byte[] array = message.getBytes(StandardCharsets.UTF_8);
		return array;
	}
	public static String decodeString (byte[] array) {
		String message = new String(array, StandardCharsets.UTF_8);
		return message;
	}

}