package kalah;

/**
 * Created by bcoll on 5/3/2017.
 */
public class House {


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
    public boolean receiveLastSeed(){
        if (seedCount == 0){
            return true;
        } else {
            seedCount++;
            return false;
        }
    }

    public int getSeeds(){
        return seedCount;
    }

//    public void returnSeeds(int seeds){
//        seedCount += seeds;
//    }

    public int capturedByEnemy(){
        int handover = seedCount;
        seedCount = 0;
        return handover;
    }

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
