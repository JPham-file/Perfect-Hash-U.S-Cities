package hash341;

import java.io.Serializable;
import java.util.ArrayList;

public class SecondTable implements Serializable {

    protected City[] secondTable;  // my second hash
    protected int collision = 0;  // for t^2
    protected final ArrayList<City> normalCityStorage;  // just to store before actually hash
    private final ArrayList<Integer> collisionStorage = new ArrayList<Integer>();  // check collision so rehash if needed
    public int secondTableSize;

    public City[] getSecondTable() {
        return secondTable;
    }

    private int collisionCounter = 0;

    public int getCollisionCounter() {
        return collisionCounter;
    }

    public SecondTable() {
        normalCityStorage = new ArrayList<City>();
    }

    public ArrayList<City> getNormalCityStorage() {
        return normalCityStorage;
    }

    public void setNormalCityStorage(City c) {
        normalCityStorage.add(c);
    }

    /**
     * hash secondary table
     */
    private void hashing() {
        int secondTableSize = collision * collision;  // the t^2  collision
        Hash24 h2 = new Hash24();
        boolean collisionFree = false;

        for (int i = 0; i < normalCityStorage.size(); ++i) {
            int secondaryIndex = h2.hash(String.valueOf(normalCityStorage.get(i))) % secondTableSize;
            collisionStorage.add(secondaryIndex);
//            System.out.println("second hash: " + secondaryIndex);
        }
    }

    /**
     * @return true if all values are unique, false otherwise
     */
    private boolean checkCollision() {

        for (int hashValue = 0; hashValue < collisionStorage.size() - 1; ++hashValue) {
            for (int j = hashValue + 1; j < collisionStorage.size(); ++j) {
                if (collisionStorage.get(hashValue) == collisionStorage.get(j)) {
                    collisionStorage.clear();  // reset
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * CityTable constructor call this to hash (perfect hash) secondary table
     */
    public void hashSecondTable() {
        if (normalCityStorage.size() < 1) {return;}

        // it cannot be any different
        if (normalCityStorage.size() == 1) {
            secondTableSize = 1;
            secondTable = new City[secondTableSize];
            secondTable[0] = normalCityStorage.get(0);
            return;
        }


        int secondTableSize = collision * collision;  // the t^2  collision
        this.secondTableSize = secondTableSize;


        // keep on hashing until collision free
        boolean collisionFree = false;
        while (!collisionFree) {
            hashing();
            collisionCounter++;
            collisionFree = checkCollision();
        }

        secondTable = new City[secondTableSize];
        for (int i = 0; i < collisionStorage.size(); ++i) {

            // now properly storing it
            secondTable[collisionStorage.get(i)] = normalCityStorage.get(i);
        }
        collisionStorage.clear();  // reset
    }
}
