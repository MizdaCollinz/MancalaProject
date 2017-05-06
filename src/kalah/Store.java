package kalah;

/**
 * Created by bcoll on 5/3/2017.
 */
public class Store {
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

    public String toString(){
        String leftFiller = "";
        String rightFiller = "";
        if (seedCount < 10){
            if(playerNumber == 0){
                rightFiller = " ";
            } else if (playerNumber == 1){
                leftFiller = " ";
            }
        }

        return " " + leftFiller + seedCount + rightFiller + " ";
    }

    public void  receiveSeeds(int seeds){
        seedCount += seeds;
    }
}
