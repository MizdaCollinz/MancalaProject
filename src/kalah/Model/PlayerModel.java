package kalah.Model;

import java.util.List;

/**
 * Created by Benjamin on 31/05/2017.
 */
public interface PlayerModel {
    List<? extends GameHouse> getGameHouses();
    GameStore getGameStore();
}
