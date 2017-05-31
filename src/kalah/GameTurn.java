package kalah;

import kalah.Model.Player;

/**
 * Created by Benjamin on 5/8/2017.
 */
public interface GameTurn {
    boolean start(); //Returns true to allow same user to have a repeat turn
    void setPickup(int house); //Sets the input house retrieved from the user
    void setPlayers(Player current, Player other); //Assigns the players relevant to the turn
}
