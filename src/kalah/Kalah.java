package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the starting point for the Modifiability Assignment.
 */
public class Kalah {

	private static final int DEFAULT_NUM_HOUSES = 6;
	private static final int DEFAULT_SEED_COUNT = 4;
	private static final int DEFAULT_NUM_PLAYERS = 2;
	private static final String DEFAULT_QUIT_STRING = "q";

	private int seedCount = DEFAULT_SEED_COUNT;
	private int numHouses = DEFAULT_NUM_HOUSES;
	private int numPlayers = DEFAULT_NUM_PLAYERS;



	private List<Player> players;
	private GameBoard board;
	private IO io;

	private boolean firstTurn = true;
	private int currentTurn = 0; //Player 1 is 0, Player 2 is 1

	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}

	public void play(IO io) {

		this.io = io;
		board = new Board(io);


		//Setup Players, pass to board
		players = new ArrayList<>();
		List<PlayerPrinter> playerPrinters = new ArrayList<>();
		for (int i=0; i<numPlayers; i++){
			Player newPlayer = new Player(i);
			newPlayer.setHouses(createHouses());
			newPlayer.setStore(new Store(i));
			players.add(newPlayer);
			playerPrinters.add(newPlayer);
		}

		board.setPlayers(playerPrinters);

		//Loop until game finishes
		boolean fullGame = playGame();

		//The game has ended, either due to Quit or a player running out of moves
		io.println("Game over"); // Game ended
		board.printBoard();

		//Game has ended normally, report the scores
		if(fullGame){
			getScores();
		}
	}

	//Instantiates a list of houses for each player
	public List<House> createHouses(){

		List<House> playerHouses = new ArrayList<>();

		for(int i=0; i<numHouses; i++){
			playerHouses.add(new House(i,seedCount));
		}

		return playerHouses;
	}

	//Control turn flow of game
	public boolean playGame(){
		while(true){
			int proceed;
			if (firstTurn){
				firstTurn = false;
				proceed = beginTurn(false);
			} else {
				proceed = beginTurn(true);
			}
			if(proceed == -1){ //Player has quit
				return false;
			} else if (proceed == 1){ //Game has finished
				return true;
			} else { //Game continues
				continue;
			}
		}
	}

	// Return -1 to quit, Otherwise return valid house to pickup, retrieves input from the user
	public int expectInput() {
		int houseValue = io.readInteger("Player P" + (currentTurn+1) + "'s turn - Specify house number or 'q' to quit: ", 1, numHouses, -1, DEFAULT_QUIT_STRING);

		if(houseValue == -1){
			return houseValue;
		}

		//If the House is empty, Print an error to user and repeat user's turn
		if(!checkInput(houseValue)){
			board.printBoard();
			houseValue = expectInput();
		}

		return houseValue;
	}


	//Determines whether the user has provided a valid non-empty house as input
	public boolean checkInput(int houseValue){
		if (players.get(currentTurn).getGameHouses().get(houseValue-1).getSeeds() == 0){
			io.println("House is empty. Move again.");
			return false;
		}
		return true;
	}

	//Return status
    /*
        -1 : User has quit the game
         0 : Proceed as per usual
         1 : The game has ended
     */
	public int beginTurn(boolean swapTurn){

		//Alternate turns unless input false [Bonus Turn, Invalid Input]
		if(swapTurn) {
			if (currentTurn == 0) {
				currentTurn = 1;
			} else {
				currentTurn = 0;
			}
		}
		board.printBoard();

		//Check if current turn is possible
		if(!players.get(currentTurn).movesAvailable()){
			return 1;
		}

		//Retrieve input from the user
		int inputValue = expectInput();

		//Exit
		if (inputValue == -1) {
			return inputValue;
		} else { //Proceed

			int other; //Opposing player number
			if(currentTurn == 0){ other = 1;} else { other = 0;}

			//Create a turn, inputs: Current Player, Opposing Player
			GameTurn newTurn = new Turn(players.get(currentTurn),players.get(other));

			newTurn.setPickup(inputValue); //Which house to be picked up

			//Start the turn, Returns true when a bonus turn occurs and repeats the turn for same player
			if(newTurn.start()){
				return beginTurn(false);
			}

			return 0; // Continue normally
		}

	}

	//The game has ended, declare a victor
	public void getScores(){
		int p1Score = players.get(0).checkScore();
		int p2Score = players.get(1).checkScore();
		int[] scores = {p1Score,p2Score};

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
