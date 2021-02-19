package SimplePeerToPeer;
import java.util.ArrayList;

public class ElectionList {

	class People {
		String host;
		String port;
		int number;
		boolean vote;
	}

	private ArrayList<People> list;
	private boolean inElection;

	public ElectionList () {
		list = new ArrayList<>();
	}

	public synchronized void makeNull () {
		list = null;
	}

	// getting the number for proposal for a leader election
	public synchronized void add (String host, String port, int number) {
		People p = new People();
		p.host = host;
		p.port = port;
		p.number = number;
		//p.vote = null;
		list.add(p);
	}

	// storing the votes;
	public synchronized void add (String host, String port, boolean vote) {
		People p = new People();
		p.host = host;
		p.port = port;
		//p.number = null;
		p.vote = vote;
		list.add(p);
	}

	public synchronized int getLowestNumber () {
		int low = list.get(0).number;
		int index = 0;
		int i = 0;
		for (People  p : list) {
			if (p.number < low) {
				low = p.number;
				index = i;
			}
			i++;
		}
		return low;
	}

	public int getSize() {
		return list.size();
	}

	public void addVote(boolean vote) {
		People p = new People();
		p.vote = vote;
		list.add(p);
	}

	public void setInElection () {
		inElection = true;
	}

	public boolean getInElection () {
		return inElection;
	}

}