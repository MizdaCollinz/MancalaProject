package kalah;

/**
 * Created by bcoll on 5/3/2017.
 */
public class House implements GameHouse {


    private int seedCount;
    private int id;

    public House(int inputId, int seedCount){
        this.seedCount = seedCount;
        this.id = inputId;
    }

    public int pickUp(){
        int pickedSeeds = seedCount;
        seedCount = 0;
        return pickedSeeds;
    }

    public void receiveSeed(){
        seedCount++;
    }

    //Return true if results in a capture
    public int receiveLastSeed(GameHouse opposite){
        if (seedCount == 0){
            //Attempt to capture opposite's seeds
            if(opposite.getSeeds() > 0){ //Can't capture empty house
                return captureHouse(opposite);
            }
        }
        //Default behaviour when no capture occurs
        receiveSeed();
        return 0; //Send nothing to store

    }

    public int captureHouse(GameHouse enemyHouse){
        int seedsCaptured = enemyHouse.capturedByEnemy();
        int droppedSeed = 1;
        int toStore = seedsCaptured + droppedSeed;
        return toStore;
    }

    public int getSeeds(){
        return seedCount;
    }

    public int capturedByEnemy(){
        int handover = seedCount;
        seedCount = 0;
        return handover;
    }

    @Override
    public String toString(){
        String filler = "";
        if (seedCount < 10){
            filler = " ";
        }
        return " " + (id+1) + "[" + filler + seedCount + "] ";
    }

    public int getId(){
        return this.id;
    }

}
