package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

/**
 * This class is the starting point for the Modifiability Assignment.
 */
public class Kalah {
	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}

	public void play(IO io) {

	    Board board = new Board(io);
		while(board.beginTurn()){
			//Continue turns until false which means q was pressed
		}
	}
}
