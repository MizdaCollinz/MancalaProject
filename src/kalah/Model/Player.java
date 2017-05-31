package kalah.Model;

import java.util.List;

/**
 * Created by bcoll on 5/6/2017.
 */
public class Player implements GamePlayer {

    private int playerNumber;
    private GameStore playerStore;
    private List<GameHouse> playerHouses;

    public Player(int player){
        this.playerNumber = player;
    }

    //TODO Change setters to interfaces

    public void setHouses(List<GameHouse> houses){
        this.playerHouses = houses;
    }

    public void setStore(GameStore store){
        this.playerStore = store;
    }

    public List<? extends Object> getHousePrinters(){return playerHouses;}
    public List<GameHouse> getGameHouses() { return playerHouses; }
    public Object getStorePrinter(){ return playerStore; }
    public GameStore getGameStore() { return playerStore; }


    //Check if player can proceed
    public boolean movesAvailable(){
        for (int i=0; i<playerHouses.size(); i++){
            if(playerHouses.get(i).getSeeds() > 0){
                return true;
            }
        }
        return false;
    }

    public int checkScore(){
        int score = 0;
        for (int i=0; i<playerHouses.size(); i++ ){
            score += playerHouses.get(i).getSeeds();
        }
        score += playerStore.getSeeds();

        return score;
    }


}
