package server;

import java.util.Random;

public class Task {

	private taskings tasks[];

	public Task () {
		tasks = new taskings[5];
		for (int i = 0; i < 5; i++) {
			tasks[i] = new taskings();
		}
		tasks[0].t = "Type 'hello'";
		tasks[0].ans = "hello";
		tasks[1].t = "Type '1'";
		tasks[1].ans = "1";
		tasks[2].t = "Answer the following: What is 2+2?";
		tasks[2].ans = "4";
		tasks[3].t = "Type 'a'";
		tasks[3].ans = "a";
		tasks[4].t = "Type 'world'";
		tasks[4].ans = "world";
	}

	public taskings getTask () {
		Random rand = new Random();
		int i = rand.nextInt(5);
		return tasks[i];
	}

}

class taskings {
	String t;
	String ans;
}