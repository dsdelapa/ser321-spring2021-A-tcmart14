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
import TCP.Questions;
import java.time.Clock;

public class TCPServer {

	private int port;
	private ServerSocket ss;
	private Socket client;
	private Charset charset;
	private TCPCommunicationHandler handler;

	public static void main (String[] args) {
		int initPort = 0;
		if (args.length != 1) {
			initPort = 8080;
		} else {
			initPort = Integer.parseInt(args[0]);
		}
		TCPServer server = new TCPServer();
		server.port = initPort;
		server.run();
	}

	public void run () {
		charset = StandardCharsets.UTF_8;
		//ObjectOutputStream out = null;
		//ObjectInputStream in = null;
		//handler = new TCPCommunicationHandler();

		try {
			ss = new ServerSocket(port);
			handler = new TCPCommunicationHandler();
		} catch (Exception e) {
			System.out.println("Issue binding to port");
			System.exit(1);
		}
		while (true) {
			try {
				client = ss.accept();
			} catch (Exception e) {
				System.out.println("Issue getting client connection");
			}
			try {
				//in = new ObjectInputStream(client.getInputStream());
				//out = new ObjectOutputStream(client.getOutputStream());
				//out.flush();
				//in.flush();
				handler.setOutputStream(client.getOutputStream());
				handler.setInputStream(client.getInputStream());	

				//System.out.println("Start delay.....");
				//TimeUnit.SECONDS.sleep(3);
				//System.out.println("End Delay.....");

			} catch (Exception e) {
				System.out.println("Issue creating streams from client");
			}

			try {
				handler.sendMessage(this.startMessage());
				String recv = handler.recvMessage();
				JSONObject obj = JSONMessageParser.getJSONObject(recv);
				if (obj.getString("type").equals("name")) {
					System.out.println("Received: " + obj.getString("name"));
					handler.setClientName(obj.getString("name"));	
				}
			} catch (Exception e) {
				continue;
			}
		
			boolean playAgain = true;
			while (playAgain) {
				String recv;
				JSONObject obj;
				String message = JSONMsgBuilder.getResponse("question", "How many questions do you want?");
				//message = JSONMsgBuilder.appendResponse(message, "name", handler.getClientName());
				//System.out.println(message);
					
				try {
					handler.sendMessage(message);
				} catch (Exception e) {
					System.out.println("Can not establisht connection with client. dropping client....");
					playAgain = false;
					continue;
				}
				//System.out.println("sent question asking for number");
				boolean complete = false;
				// getting question number from client
				while (!complete) {
					
					try {
						recv = handler.recvMessage();
					} catch (Exception e) {
						System.out.println("Can not establisht connection with client. dropping client....");
						playAgain = false;
						continue;
					}
					//System.out.println(recv);
					obj = JSONMessageParser.getJSONObject(recv);
					if (!obj.getString("type").equals("number")) {
						complete = false;
						//System.out.println("fail 1");
					} else {
						try {
							handler.setNumQuestions(Integer.parseInt(obj.getString("number")));
							complete = true;
						} catch (Exception e) {
							// retry
							//System.out.println("fail 2");
							complete = false;						
						}
					}
					if (complete == false) {
						//System.out.println("fail 3");
						try {
							this.sendError(JSONMsgBuilder.getResponse("question", "How many questions do you want?"));
						} catch (IOException e) {
							//System.out.println("fail 4");
							System.out.println("Error connecting with client");
							System.exit(1);
						}
					} else {
						message = JSONMsgBuilder.getResponse("message", "ready");
						try {
							//System.out.println("fail 6");
							//System.out.println(message);
							handler.sendMessage(message);
							//System.out.println("sent");
							complete = true;
							//break;
						} catch (Exception e) {
							//System.out.println("fail 5");
							System.out.println("Issue sending messafe to client.");
							System.exit(1);
						}
					}
					//System.out.println(complete);
				}
				//System.out.println("Through this section");
				//System.out.println(handler.getNumQuestions());
				try {//System.out.println("Number of questions:" + handler.getNumQuestions());
					recv = handler.recvMessage();
					obj = JSONMessageParser.getJSONObject(recv);
					if (obj.getString("type").equals("message") && obj.getString("message").equals("start")) {
						System.out.println("start game");
						this.gameLoop();
					} else {
						try {
							this.sendError(JSONMsgBuilder.getResponse("message", "waiting for ready"));
						} catch (IOException e) {
							System.out.println("Issue connecting with client. dropping connection");
							playAgain = false;
							break;
						}
					}
					message = JSONMsgBuilder.getResponse("question", "Do you want to ply again? [yes or no]");
					handler.sendMessage(message);
				
					recv = handler.recvMessage();
					obj = JSONMessageParser.getJSONObject(recv);
					if (obj.getString("type").equals("answer")) {
						if (obj.getString("answer").equals("yes")) {
							playAgain = true;
						} else {
							playAgain = false;
						}
					}
				} catch (Exception e) {
					System.out.println("Issue connecting with client. dropping connection");
					playAgain = false;
				}


				}
			}
		}
			/*
			recv = handler.recvMessage();
			obj = JSONMessageParser.getJSONObject(recv);
			if (obj.getString("type").equals("number")) {
				// handler number and set game logic
			}
			*/



	private String startMessage() {
		JSONMsgBuilder msg = new JSONMsgBuilder();
		String message = msg.getStartHeader();
		return message;
	}

	private void sendError (String initialMessage) throws IOException {
		String errorMessage = JSONMsgBuilder.appendResponse(initialMessage, "error", "incorrect input");
		try {
			//System.out.println("fail 7");
			handler.sendMessage(errorMessage);
		} catch (Exception e) {
			throw new IOException();
		}
	}

	private void gameLoop() throws IOException, Exception {
		int timeLimit = handler.getNumQuestions() * 5;
		System.out.println("Time limit: " + timeLimit);
		Questions q = new Questions();
		int questionNum = 0;
		String recv;
		String msg;
		boolean win;
		//Clock clock = new Clock();
		long startTime = System.currentTimeMillis();
		long dt = 0;
		for (int i = 0; i < handler.getNumQuestions(); i++) {
			String temp = q.getQuestion();
			String qa[] = temp.split("/");
			String question = qa[0];
			String answer = qa[1];
			System.out.println("Answer to question: " + answer);
			msg = JSONMsgBuilder.getResponse("question", question);
			try {
				handler.sendMessage(msg);
				recv = handler.recvMessage();
			} catch (IOException e) {
				throw new IOException();
			} catch (Exception e) {
				throw new Exception();
			}
			JSONObject obj = JSONMessageParser.getJSONObject(recv);
			if (obj.getString("type").equals("answer") && !obj.getString("answer").equals("next")) {
				if (!obj.isNull("answer")) {
					handler.isCorrect();	
				} else if (obj.isNull("answer")) {
					continue;
				}
			} else if (obj.getString("type").equals("answer") && obj.getString("answer").equals("next")) {
				System.out.println("client skipping question");
				continue;
			}

			if (i == handler.getNumQuestions()-1) {
				break;
			}

			dt = System.currentTimeMillis() - startTime;
			if (dt >= (timeLimit * 1000)) {
				win = false;
				break;
			}
		}


		if (handler.getCorrectAnswers() == (handler.getNumQuestions() - 1) && dt >= (timeLimit * 1000)) {
			win = true;
		} else {
			win = false;
		}

		if (win = true) {
			msg = JSONMsgBuilder.getResponse("message", "win");
			handler.sendMessage(msg);
		} else {
			msg = JSONMsgBuilder.getResponse("message", "lose");
			handler.sendMessage(msg);
		}

	}
}
