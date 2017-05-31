package kalah.Model;

import java.util.List;

/**
 * Created by Benjamin on 31/05/2017.
 */
public interface GamePlayer extends PlayerModel, PlayerPrinter {
    void setHouses(List<GameHouse> houses);
    void setStore(GameStore store);
    boolean movesAvailable(); //Determine whether the player has a valid move available
    int checkScore(); //Return the player's current score
}
