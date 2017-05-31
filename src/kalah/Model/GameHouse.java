package kalah.Model;

/**
 * Created by bcoll on 31/05/2017.
 */
public interface GameHouse {
    int pickUp(); //Pick up seeds in this house (return amount contained)
    int receiveLastSeed(GameHouse opposingHouse); // Gain one seed, pass reference to house on opposite site of board
    void receiveSeed(); //Gain one seed in this house
    int getSeeds(); //Retrieve the seed count
    int captureHouse(GameHouse enemyHouse); //Return seeds to send to the store
    int capturedByEnemy(); //Return seeds handed over
}
