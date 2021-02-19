package SimplePeerToPeer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.ArrayDeque;

import java.io.PrintWriter;
import org.json.*;

/**
 * This is the main class for the peer2peer program.
 * It starts a client with a username and host:port for the peer and host:port of the initial leader
 * This Peer is basically the client of the application, while the server (the one listening and waiting for requests)
 * is in a separate thread ServerThread
 * In here you should handle the user input and then send it to the server of annother peer or anything that needs to be done on the client side
 * YOU CAN MAKE ANY CHANGES YOU LIKE: this is a very basic implementation you can use to get started
 * 
 */

public class Peer {
	private String username;
	private BufferedReader bufferedReader;
	private ServerThread serverThread;

	private Set<SocketInfo> peers = new HashSet<SocketInfo>();
	private boolean leader = false;
	private SocketInfo leaderSocket;

	private ElectionList list;
	private ElectionList jokeElection;
	private int number;

	private boolean isAskingForLeader;

	private ArrayList<Joke> jokeArray;
	private ArrayDeque<Joke> potJoke;

	
	public Peer(BufferedReader bufReader, String username,ServerThread serverThread){
		this.username = username;
		this.bufferedReader = bufReader;
		this.serverThread = serverThread;
		isAskingForLeader = false;
		jokeArray = new ArrayList<Joke>();
		potJoke = new ArrayDeque<>();
	}

	public void startElection () {
		list = new ElectionList();
		this.number = JSONMessageBuilder.getRandomNumber();
		pushCandidacy(this.number);
	}

	public void addPeerCandidate (String host, String port, int num) {
		list.add(host, port, num);
		if (list.getSize() == numberOfPeers()) {
			if (list.getLowestNumber() >= this.number) {
				pushMessage(JSONMessageBuilder.askForLeader(serverThread.getHost(), Integer.toString(serverThread.getPort())));
				list = new ElectionList();
				isAskingForLeader = true;
			}
		}
	}

	public void tallyVotes () {
		if (isAskingForLeader) {
			list.addVote(true);
			if (list.getSize() == numberOfPeers()) {
				pushMessage(JSONMessageBuilder.announceLeader(serverThread.getHost(), Integer.toString(serverThread.getPort())));
				leaderSocket = new SocketInfo(serverThread.getHost(), serverThread.getPort());
				leader = true;
				isAskingForLeader = false;
			}
		}
	}

	public void addJoke (String joke) {
		Joke j = new Joke();
		j.setJoke(joke);
		jokeArray.add(j);
	}

	public void addPotentialJoke (String joke) {
		Joke j = new Joke();
		j.setJoke(joke);
		potJoke.add(j);

	}

	public void startJokeElection () {
		jokeElection = new ElectionList();
		Joke j = potJoke.getFirst();
		pushMessage(JSONMessageBuilder.pollForJoke(j.getJoke()));
	}

	public void tallyJokeVote () {
		jokeElection.addVote(true);
		if (jokeElection.getSize() == numberOfPeers()) {
			pushMessage(JSONMessageBuilder.addJoke(potJoke.getFirst().getJoke()));
			jokeArray.add(potJoke.removeFirst());
		}
	}

	public void voteJoke () {
		tellLeader(JSONMessageBuilder.voteJoke());
	}

	public void removeLeader () {
		peers.remove(leaderSocket);
		leaderSocket = null;
	}

	public void setLeader(boolean leader, SocketInfo leaderSocket){
		this.leader = leader;
		this.leaderSocket = leaderSocket;
	}

	public void setLeader (String host, int port) {
		leaderSocket = new SocketInfo(host, port);
	}

	public boolean isLeader(){
		return leader;
	}

	public int numberOfPeers () {
		return peers.size();
	}

	public SocketInfo getMySocketInfo () {
		return new SocketInfo(serverThread.getHost(), serverThread.getPort());
	}

	public synchronized Set<SocketInfo> getPeerDS () {
		return peers;
	}

	public synchronized void addPeer(SocketInfo si){
		peers.add(si);
	}
	
	// get a string of all peers that this peer knows
	public synchronized String getPeers(){
		String s = "";
		for (SocketInfo p: peers){
			s = s +  p.getHost() + ":" + p.getPort() + " ";
		}
		return s; 
	}

	public synchronized void removeAPeer (String host, String port) {
		SocketInfo s = new SocketInfo(host, Integer.parseInt(port));
		SocketInfo toRemove = null;
		for (SocketInfo temp : peers) {
			System.out.println(temp.getHost() + " " + temp.getPort());
			if (s.getHost().equals(temp.getHost()) && s.getPort() == temp.getPort()) {
				toRemove = temp;
				System.out.println("peer removed");
			}
		}
		peers.remove(toRemove);
	}


	public synchronized void newPeerList(String list) throws Exception {
		peers = new HashSet<SocketInfo>();
		String[] peerList = list.split(" ");
		for (String p: peerList){
			String[] hostPort = p.split(":");

			// basic check to not add ourself, since then we would send every message to ourself as well (but maybe you want that, then you can remove this)
			if ((hostPort[0].equals(serverThread.getHost())) && Integer.valueOf(hostPort[1]) == serverThread.getPort()){
				continue;
			}
			SocketInfo s = new SocketInfo(hostPort[0], Integer.valueOf(hostPort[1]));
			peers.add(s);
		}
	}

   
	public synchronized void updateListenToPeers(String list) throws Exception {
		String[] peerList = list.split(" ");
		for (String p: peerList){
			String[] hostPort = p.split(":");

			// basic check to not add ourself, since then we would send every message to ourself as well (but maybe you want that, then you can remove this)
			if ((hostPort[0].equals(serverThread.getHost())) && Integer.valueOf(hostPort[1]) == serverThread.getPort()){
				continue;
			}
			SocketInfo s = new SocketInfo(hostPort[0], Integer.valueOf(hostPort[1]));
			peers.add(s);
		}
	}
	
	/**
	 * Client waits for user to input can either exit or send a message
	 */
	// add entry for user entering joke here
	public void askForInput() throws Exception {
		try {
			
			System.out.println("> You can now start chatting (exit to exit)");
			while(true) {
				String message = bufferedReader.readLine();
				if (message.equals("exit")) {
					System.out.println("bye, see you next time");
					break;
				} else if (message.equals("peerList")) {
					for (SocketInfo s : peers) {
						System.out.print(s.getHost() + ":" + s.getPort());
						if (s.getHost().equals(leaderSocket.getHost()) && s.getPort() == leaderSocket.getPort())
							System.out.print(" Leader");
						System.out.println("\n");
					}
				} else if (message.equals("joke")) {
					System.out.println("Enter a joke:");
					 String joke = bufferedReader.readLine();
					 tellLeader(JSONMessageBuilder.askAddJoke(joke));
				} else if (message.equals("jokeList")) {
					for (Joke j : jokeArray) {
						System.out.println(j.getJoke());
					}
				} else {
					//pushMessage("{'type': 'message', 'username': '"+ username +"','message':'" + message + "'}");
					pushMessage(JSONMessageBuilder.message(username, message));
					//JSONMessageBuilder msg = new JSONMessageBuilder();
					//pushMessage(msg.makeMessage(username, message));
				}	
			}
			System.exit(0);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

// ####### You can consider moving the two methods below into a separate class to handle communication
	// if you like (they would need to be adapted some of course)


	/**
	 * Send a message only to the leader 
	 *
	 * @param message String that peer wants to send to the leader node
	 * this might be an interesting point to check if one cannot connect that a leader election is needed
	 */
	public void commLeader(String message) {
		try {
			BufferedReader reader = null; 
				Socket socket = null;
				try {
					socket = new Socket(leaderSocket.getHost(), leaderSocket.getPort());
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					
				} catch (Exception c) {
					if (socket != null) {
						socket.close(); 
					} else {
						System.out.println("Could not connect to " + leaderSocket.getHost() + ":" + leaderSocket.getPort());
					}
					return; // returning since we cannot connect or something goes wrong the rest will not work. 
				}

				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println(message);

				JSONObject json = new JSONObject(reader.readLine());
				System.out.println("     Received from server " + json);
				String list = json.getString("list");
				updateListenToPeers(list); // when we get a list of all other peers that the leader knows we update them

		} catch(Exception e) {
			try {
				String t = JSONMessageBuilder.notify("dead leader");
		   		pushMessage(t);
		   		Socket tempSocket = new Socket(serverThread.getHost(), serverThread.getPort());
		   		PrintWriter tempWriter = new PrintWriter(tempSocket.getOutputStream(), true);
		   		tempWriter.println(t);
		    } catch (Exception a) {
	    		System.out.println("Not able to connected with network... exiting");
	  	   		System.exit(1);
	    	}
		}
	}

	public void tellLeader(String message) {
		try {
			BufferedReader reader = null; 
				Socket socket = null;
				try {
					socket = new Socket(leaderSocket.getHost(), leaderSocket.getPort());
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					
				} catch (Exception c) {
					if (socket != null) {
						socket.close(); 
					} else {
						System.out.println("Could not connect to " + leaderSocket.getHost() + ":" + leaderSocket.getPort());
					}
					return; // returning since we cannot connect or something goes wrong the rest will not work. 
				}

				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println(message);

			} catch (Exception e) {
				try {
					String t = JSONMessageBuilder.notify("dead leader");
		    		pushMessage(t);
		    		Socket tempSocket = new Socket(serverThread.getHost(), serverThread.getPort());
		    		PrintWriter tempWriter = new PrintWriter(tempSocket.getOutputStream(), true);
		    		tempWriter.println(t);
		    	} catch (Exception a) {
		    		System.out.println("Not able to connected with network... exiting");
		    		System.exit(1);
		    	}
			}
		}

/**
	 * Send a message to every peer in the peers list, if a peer cannot be reached remove it from list
	 *
	 * @param message String that peer wants to send to other peers
	 */
	 public synchronized void pushMessage(String message) {
		try {
			System.out.println("     Trying to send to peers: " + peers.size());
			boolean deadLeader = false;
			Set<SocketInfo> toRemove = new HashSet<SocketInfo>();
			BufferedReader reader = null; 
			int counter = 0;
			for (SocketInfo s : peers) {
				Socket socket = null;
				try {
					socket = new Socket(s.getHost(), s.getPort());
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				} catch (Exception c) {
					if (socket != null) {
						socket.close();
					} else {
						System.out.println("  Could not connect to " + s.getHost() + ":" + s.getPort());
						System.out.println("  Removing that socketInfo from list");
						toRemove.add(s);
						//commLeader(JSONMessageBuilder.removePeer(s));
						continue;
					}
					System.out.println("     Issue: " + c);
				}

				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println(message);
				counter++;
				socket.close();
		     }
		    for (SocketInfo s: toRemove){
		    	if (!leader) {
		    		tellLeader(JSONMessageBuilder.removePeer(s));
		    	} 
		    	if (s.getHost().equals(leaderSocket.getHost()) && s.getPort() == leaderSocket.getPort())
		    		deadLeader = true;
		    	peers.remove(s);
		    }

		    if (deadLeader && leaderSocket != null) {
		    	String t = JSONMessageBuilder.notify("dead leader");
		    	pushMessage(t);
		    	Socket tempSocket = new Socket(serverThread.getHost(), serverThread.getPort());
		    	PrintWriter tempWriter = new PrintWriter(tempSocket.getOutputStream(), true);
		    	tempWriter.println(t);
		    }

		    System.out.println("     Message was sent to " + counter + " peers");

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void pushCandidacy (int number) {
		String message = JSONMessageBuilder.candidacy(serverThread.getHost(), serverThread.getPort(), number);

		pushMessage(message);
	}

	/**
	 * Main method saying hi and also starting the Server thread where other peers can subscribe to listen
	 *
	 * @param args[0] username
	 * @param args[1] port for server
	 */
	public static void main (String[] args) throws Exception {

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String username = args[0];
		System.out.println("Hello " + username + " and welcome! Your port will be " + args[1]);

		int size = args.length;
		System.out.println(size);
		if (size == 4) {
			System.out.println("Started peer");
        } else {
            System.out.println("Expected: <name(String)> <peer(String)> <leader(String)> <isLeader(bool-String)>");
            System.exit(0);
        }

        System.out.println(args[0] + " " + args[1]);
        ServerThread serverThread = new ServerThread(args[1]);
        Peer peer = new Peer(bufferedReader, username, serverThread);

        String[] hostPort = args[2].split(":");
        SocketInfo s = new SocketInfo(hostPort[0], Integer.valueOf(hostPort[1]));
        System.out.println(args[3]);
        if (args[3].equals("true")){
			System.out.println("Is leader");
			peer.setLeader(true, s);
		} else {
			System.out.println("Pawn");

			// add leader to list 
			peer.addPeer(s);
			peer.setLeader(false, s);

			// send message to leader that we want to join
			
			//String joinmsg = joinMsg.getJoinMessage(username, serverThread.getHost(), serverThread.getPort());
			//peer.commLeader(joinmsg);
			peer.commLeader(JSONMessageBuilder.getJoinMessage(username, serverThread));
			//System.oout.println("{'type': 'join', 'username': '"+ username +"','ip':'" + serverThread.getHost() + "','port':'" + serverThread.getPort() + "'}");
		}
		serverThread.setPeer(peer);
		serverThread.start();
		peer.askForInput();

	}
	
}
