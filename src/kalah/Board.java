package kalah;

import com.qualitascorpus.testsupport.IO;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by bcoll on 5/3/2017.
 */
public class Board implements GameBoard{
    private static final int DEFAULT_NUM_HOUSES = 6;
    private static final int DEFAULT_SEED_COUNT = 4;
    private static final int DEFAULT_NUM_PLAYERS = 2;
    private static final String DEFAULT_QUIT_STRING = "q";
    private IO boardIO;

    private int seedCount = DEFAULT_SEED_COUNT;
    private int numHouses = DEFAULT_NUM_HOUSES;
    private int numPlayers = DEFAULT_NUM_PLAYERS;

    private List<Player> players = new ArrayList<Player>();
    private int currentTurn;
    private boolean firstTurn = true;

    public Board(IO io) {
        boardIO = io;
        for (int i=0; i<numPlayers; i++){
            players.add(new Player(i,seedCount,numHouses));
        }
        currentTurn = 0;
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
        printBoard();

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

            newTurn.setPickup(inputValue);

            //Returns true for bonus turn, repeats turn for same player
            if(newTurn.start()){
                return beginTurn(false);
            }

            return 0; // Continue normally
        }

    }

    //Called by Main, Continuously begins turns until game ends or is quit
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

    // Return -1 to quit, Otherwise return valid house to pickup
    public int expectInput() {
        int houseValue = boardIO.readInteger("Player P" + (currentTurn+1) + "'s turn - Specify house number or 'q' to quit: ", 1, numHouses, -1, DEFAULT_QUIT_STRING);

        if(houseValue == -1){
            return houseValue;
        }

        //If the House is empty, Print an error to user and repeat user's turn
        if(!checkInput(houseValue)){
            printBoard();
            houseValue = expectInput();
        }

        return houseValue;
    }


    public boolean checkInput(int houseValue){
        if (players.get(currentTurn).getHouses().get(houseValue-1).getSeeds() == 0){
            boardIO.println("House is empty. Move again.");
            return false;
        }
        return true;
    }


    //Print current state of board, prompt for input
    public void printBoard(){

        Player p1 = players.get(0);
        Player p2 = players.get(1);

        String separatorStart = "+----+";
        String separatorPerHouse ="";
        String separatorEnd = "----+";

        String middleEnds = "|    |";

        String p2Houses = "| P2 |";
        String p1Houses = "|" +p2.getStore().toString() +  "|";

        for (int i=0; i < numHouses ; i++ ){
            p2Houses += (p2.getHouses().get(numHouses-1-i).toString() + "|");
            p1Houses += (p1.getHouses().get(i).toString() + "|");
            separatorPerHouse += "-------+";
        }
        p2Houses += (p1.getStore().toString() + "|");
        p1Houses += " P1 |";
        String middleMiddle = separatorPerHouse.substring(0,separatorPerHouse.length()-1);

        String separatorString = separatorStart + separatorPerHouse + separatorEnd;
        String middleString = middleEnds + middleMiddle + middleEnds;

        //Print the board
        boardIO.println(separatorString);
        boardIO.println(p2Houses);
        boardIO.println(middleString);
        boardIO.println(p1Houses);
        boardIO.println(separatorString);


    }

    //The game has ended, declare a victor
    public int[] endGame(){
        int p1Score = players.get(0).checkScore();
        int p2Score = players.get(1).checkScore();
        int[] scores = {p1Score,p2Score};
        return scores;
    }
}
