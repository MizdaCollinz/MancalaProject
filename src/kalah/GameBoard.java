package kalah;

import kalah.Model.PlayerPrinter;

import java.util.List;

/**
 * Created by Benjamin on 5/7/2017.
 */
public interface GameBoard {
    void printBoard(); // Print the state of the game
    void setPlayers(List<PlayerPrinter> players);
}
