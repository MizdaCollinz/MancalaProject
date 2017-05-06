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
	    boolean firstRun = true;
	    int continueGame;

		while(true){
			if(firstRun){
				firstRun = false;
				continueGame = board.beginTurn(false);//Dont swap on first call, use default value player 1
			} else {
				continueGame = board.beginTurn(true); //Continue turns until false which means q was pressed
			}
			if(continueGame == -1 || continueGame == 1){
				break;
			}
		}

		io.println("Game over"); // Game ended
		board.printBoard();

		//Game has ended normally, report the scores
		if(continueGame == 1){
			int[] scores = board.endGame();
			io.println("	player 1:" + scores[0]);
			io.println("	player 2:" + scores[1]);
			if (scores[0] > scores[1]) {
				io.println("Player 1 wins!");
			} else if (scores[1] > scores[0]){
				io.println("Player 2 wins!");
			} else {
				io.println("A tie!");
			}
		}
	}
}
