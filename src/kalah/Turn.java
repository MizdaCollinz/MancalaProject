package kalah;

import kalah.Model.GameHouse;
import kalah.Model.GameStore;
import kalah.Model.Player;
import kalah.Model.PlayerModel;

import java.util.List;

/**
 * Created by Benjamin on 5/8/2017.
 */
public class Turn implements GameTurn {

    private int numHouses;

    private PlayerModel current;
    private PlayerModel opponent;

    private GameStore currentStore;
    private GameStore opponentStore;

    private List<? extends GameHouse> playerHouses;
    private List<? extends GameHouse> opponentHouses;

    private int pickup;

    public Turn(PlayerModel turn, PlayerModel opp) {
        this.current = turn;
        this.opponent = opp;

        currentStore = current.getGameStore();
        opponentStore = opponent.getGameStore();

        playerHouses = current.getGameHouses();
        opponentHouses = opponent.getGameHouses();

        numHouses = playerHouses.size();
    }

    public boolean start(){
        return pickUpHouse(pickup);
    }

    public void setPickup(int house){
        this.pickup = house;
    }

    public void setPlayers(Player current, Player other){
        this.current = current;
        this.opponent = other;
    }

    private boolean pickUpHouse(int house){

        //Fetches specified house of current player, empties its seeds, returns number of seeds
        int seedsFound = playerHouses.get(house-1).pickUp();

        //House to drop next seed into
        int nextHouse = house;
        //Next seed drop is in player's own house
        boolean myHouses = true;
        //This is the last seed to be dropped (Captures, Bonus Turns)
        boolean lastSeed = false;

        //Iterate through following houses dropping seeds one by one
        for(int i=0; i<seedsFound; i++){
            if(i == seedsFound - 1 ){
                lastSeed = true;
            }

            //Next-house is a store, A players store is after their houses
            if(nextHouse == numHouses){
                if(myHouses){
                    currentStore.receiveSeeds(1);
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

            // Next house is a regular house
            } else {
                dropInHouse(myHouses,nextHouse,lastSeed);
                nextHouse++;
            }
        }
        return false; //No bonus turn
    }

    //Drop the current seed in a house object
    private void dropInHouse(boolean currentHouse, int nextHouse, boolean lastSeed){
        //Drop in players own house
        if(currentHouse){
            GameHouse dropHouse = playerHouses.get(nextHouse);
            if(lastSeed){
                int oppositeHouse = numHouses - nextHouse-1;
                //Pass opposite house in case it is captured
                //Returns quantity to send to the store
                int toStore = dropHouse.receiveLastSeed(opponentHouses.get(oppositeHouse));
                if (toStore > 0){
                    currentStore.receiveSeeds(toStore);
                }

            } else {
                dropHouse.receiveSeed();
            }

        //Drop in opponents house
        } else {
            GameHouse dropHouse = opponentHouses.get(nextHouse);
            dropHouse.receiveSeed();
        }
    }

}
