package TCP;

import java.net.*;
import java.io.*;
import TCP.EncoderDecoder;

public class TCPCommunicationHandler {

	private ObjectOutputStream out;
	private ObjectInputStream in;

	public TCPCommunicationHandler () {
		out = null;
		in = null;
	}

	public void setOutputStream (OutputStream initOut) throws Exception {
		out = new ObjectOutputStream(initOut);
		out.flush();
	}

	public void setInputStream (InputStream initIn) throws Exception {
		in = new ObjectInputStream(initIn);
	}

	public void sendMessage (String source) throws Exception {
		byte array[] = EncoderDecoder.encodeString(source);
		out.write(array);
		out.flush();
	}

	public String recvMessage () throws Exception {
		byte array[] = new byte[1024];
		in.readFully(array);
		return EncoderDecoder.decodeString(array);
	}




}