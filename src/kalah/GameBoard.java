package kalah;

/**
 * Created by Benjamin on 5/7/2017.
 */
public interface GameBoard {
    boolean playGame(); //Start the game
    void printBoard(); // Print the state of the game
    int[] endGame(); // Returns the player scores as an array of integers
}
