package kalah;

/**
 * Created by bcoll on 5/3/2017.
 */
public class Store implements GameStore{
    private int seedCount;
    private int playerNumber;

    public Store(int playerNumber) {
        seedCount = 0;
        this.playerNumber = playerNumber;
    }

    public Store(int playerNumber, int customSeed){
        seedCount = customSeed;
        this.playerNumber = playerNumber;
    }

    @Override
    public String toString(){
        String filler = "";
        if (seedCount < 10){
            filler = " ";
        }

        return " " + filler + seedCount + " ";
    }

    public void  receiveSeeds(int seeds){
        seedCount += seeds;
    }

    public int getSeeds(){
        return seedCount;
    }
}
