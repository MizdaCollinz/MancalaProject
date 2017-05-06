package kalah;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bcoll on 5/6/2017.
 */
public class Player {

    private int seedCount;
    private int numHouses;

    private int playerNumber;
    private Store playerStore;
    private List<House> playerHouses;

    public Player(int player, int seeds, int houses){
        this.seedCount = seeds;
        this.numHouses = houses;
        this.playerNumber = player;

        this.playerStore = new Store(playerNumber);
        this.createHouses();
    }

    public void createHouses(){

        playerHouses = new ArrayList<House>();

        for(int i=0; i<numHouses; i++){
            playerHouses.add(new House(i,seedCount));
        }
    }

    public List<House> getHouses(){
        return playerHouses;
    }

    public Store getStore(){
        return playerStore;
    }

    public boolean movesAvailable(){
        return true;
        //TODO
    }
}
