package kalah;

import java.util.List;

/**
 * Created by Benjamin on 5/8/2017.
 */
public class Turn implements GameTurn {

    private int numHouses;

    private Player current;
    private Player opponent;

    private Store currentStore;
    private Store opponentStore;

    private List<House> playerHouses;
    private List<House> opponentHouses;

    private int pickup;

    public Turn(Player turn, Player opp) {
        this.current = turn;
        this.opponent = opp;

        currentStore = current.getStore();
        opponentStore = opponent.getStore();

        playerHouses = current.getHouses();
        opponentHouses = opponent.getHouses();

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

    private void dropInHouse(boolean currentHouse, int nextHouse, boolean lastSeed){
        //Drop in players own house
        if(currentHouse){
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
                        currentStore.receiveSeeds(toStore);
                    }
                }
            } else {
                dropHouse.receiveSeed();
            }

        //Drop in opponents house
        } else {
            House dropHouse = opponentHouses.get(nextHouse);
            dropHouse.receiveSeed();
        }
    }

}
