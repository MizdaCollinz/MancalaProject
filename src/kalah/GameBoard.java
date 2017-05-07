package kalah;

/**
 * Created by Benjamin on 5/7/2017.
 */
public interface GameBoard {
    int beginTurn();
    void printBoard();
    int[] endGame();
}
