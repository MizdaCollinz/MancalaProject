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
        return expectInput();

    }

    public int beginTurn(){
        if (firstTurn){
            firstTurn = false;
           return beginTurn(false);
        } else {
           return beginTurn(true);
        }
    }

    public int expectInput() {
        int houseValue = boardIO.readInteger("Player P" + (currentTurn+1) + "'s turn - Specify house number or 'q' to quit: ", 1, numHouses, -1, DEFAULT_QUIT_STRING);
        if (houseValue == -1) {
            return -1; // Quit game
        } else {
            //If the House is empty, Print an error to user and repeat user's turn
            if (players.get(currentTurn).getHouses().get(houseValue-1).getSeeds() == 0){
                boardIO.println("House is empty. Move again.");
               return beginTurn(false);
            }
            //If you end in a store, complete a bonus turn
            if (pickUpHouse(houseValue)) {
                return beginTurn(false);
            }
            return 0; //True means continue game
        }
    }

    //Assume house is already checked for validity, assume house is not empty
    public boolean pickUpHouse(int house){
        //Get players
        Player currentPlayer = players.get(currentTurn);
        int other; //Opposing player number
        if(currentTurn == 0){ other = 1;} else { other = 0;}
        Player otherPlayer = players.get(other);

        List<House> playerHouses = currentPlayer.getHouses();
        List<House> opponentHouses = otherPlayer.getHouses();

        //Fetches specified house of current player, empties its seeds, returns number of seeds
       int seedsFound = playerHouses.get(house-1).pickUp();

       //House to drop next seed into
       int nextHouse = house;
        //Next seed drop is in player's own house
       boolean myHouses = true;
       boolean lastSeed = false;
       //Check if seeds placed in enemy housing
       boolean visitedEnemy = false;

        //Iterate through following houses dropping seeds one by one
       for(int i=0; i<seedsFound; i++){
           if(i == seedsFound - 1 ){
                lastSeed = true;
           }

           //Next house is a store
            if(nextHouse == numHouses){
                if(myHouses){
                    currentPlayer.getStore().receiveSeeds(1);
                    if(lastSeed){
                        return true; // Bonus turn
                    }
                } else {
                    //Skip the Opposing store, go straight to first owned house
                   i--;
                   nextHouse =0;
                }
                myHouses = !myHouses; // After a store is the opposite player's houses
                nextHouse = 0;
            } else { // Next house is a regular house
                if(myHouses){
                    House dropHouse = playerHouses.get(nextHouse);
                    if(lastSeed){
                       if(dropHouse.receiveLastSeed()){
                           int toStore = 1; //Seed being dropped
                           int oppositeHouse = numHouses - nextHouse-1;

                           //Number of seeds captured
                           int fromCapture = opponentHouses.get(oppositeHouse).capturedByEnemy();
                           if (fromCapture == 0){
                               dropHouse.receiveSeed();
                           } else {
                               toStore += fromCapture;
                               currentPlayer.getStore().receiveSeeds(toStore);
                           }
                       }
                    } else {
                        dropHouse.receiveSeed();
                    }
                } else {
                    House dropHouse = opponentHouses.get(nextHouse);
                    dropHouse.receiveSeed();
                }
                nextHouse++;
            }
       }
       return false; //No bonus turn
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
