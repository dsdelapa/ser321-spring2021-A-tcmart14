package TCP;

import java.util.Random;

public class Questions {

	private QA q[];
	private int selected[];
	private Random rand;

	class QA {
		String question;
		String answer;
	}

	public Questions () {
	    q = new QA[20];
	    for (int i = 0; i < 20; i++)
	    	q[i] = new QA();
		q[0].question = "In 2020, apple made a transition to what computer architecture for it's Mac product line?";
		q[0].answer = "arm";
		q[1].question = "Who is known as the first programmer?";
		q[1].answer = "ada lovelace";
		q[2].question = "What operating system is a current day derivative of BSD4.4?";
		q[2].answer = "freebsd";
		q[3].question = "What distro is mostly known for having a packagemanager that compiles from source?";
		q[3].answer = "gentoo";
		q[4].question = "What is the parent company of ubuntu?";
		q[4].answer = "canonical";
		q[5].question = "What computer company created the SPARC architecture?";
		q[5].answer = "sun";
		q[6].question = "What processor architecture did Apple use prior to their 2006 transition to intel?";
		q[6].answer = "powerpc";
		q[7].question = "What computer company bought red hat for 34 billion?";
		q[7].answer = "ibm";
		q[8].question = "Name of the enterprise linux distribution from Germany?";
		q[8].answer = "SUSE";
		q[9].question = "Operating system with developers who have created openSSH";
		q[9].answer = "openbsd";
		q[10].question = "What is the logo of openbsd?";
		q[10].answer = "puffer fish";
		q[11].question = "What is the logo of freebsd?";
		q[11].answer = "devil";
		q[12].question = "Complete the rest of the slogan from the gentoo community: if it moves";
		q[12].answer = "compile it";
		q[13].question = "Gentoo's emerge package manager is based on?";
		q[13].answer = "freebsd ports tree";
		q[14].question = "Do the BSDs actually have original unix code?";
		q[14].answer = "yes";
		q[15].question = "This distribution is known for using MUSL C instead of GNU C and their founder disappearing for a period of time?";
		q[15].answer = "void linux";
		q[16].question = "Wht does KISS stand for?";
		q[16].answer = "keep it simple stupid";
		q[17].question = "Alterntive name for Richard Stallman?";
		q[17].answer = "saint ignucius";
		q[18].question = "Emacs uses this language for building on features and customization?";
		q[18].answer = "lisp";
		q[19].question = "Who created the first compiler?";
		q[19].answer = "admiral grace hopper";
		selected = new int[20];
		for (int i = 0; i < 20; i++) {
			selected[i] = 0;
		}
		rand = new Random();
	}

	 public String getQuestion() {
	 	String quan = new String();
 		boolean loop = true;
 		while (loop) {
 			int i = rand.nextInt(20);
 			if (selected[i] == 0) {
 				quan = q[i].question + "/" + q[i].answer;
 				selected[i] = 1;
 				loop = false;
 			} else {
 				loop = true;
 			}
 		}
 		return quan;
 	}


}