package kalah;

import java.util.List;

/**
 * Created by bcoll on 5/6/2017.
 */
public class Player implements PlayerPrinter{

    private int playerNumber;
    private Store playerStore;
    private List<House> playerHouses;

    public Player(int player){
        this.playerNumber = player;
    }

    //TODO Change setters to interfaces

    public void setHouses(List<House> houses){
        this.playerHouses = houses;
    }

    public void setStore(Store store){
        this.playerStore = store;
    }

    public List<House> getHouses(){
        return playerHouses;
    }

    public Store getStore(){
        return playerStore;
    }

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
