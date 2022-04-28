package hash341;
import java.util.ArrayList;

public class SecondTable {

    protected City[] secondTable;  // my second hash
    private ArrayList<City> normalCityStorage;  // just to store before actually hash
    private ArrayList<Integer> collisionStorage = new ArrayList<Integer>();  // check collision so rehash if needed

    protected int collision = 0;  // for t^2


    public SecondTable() {
        normalCityStorage = new ArrayList<City>();
    }

    public void setNormalCityStorage(City c) {
        normalCityStorage.add(c);
    }

    public ArrayList<City> getNormalCityStorage() {
        return normalCityStorage;
    }

    private void rehash() {
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
     *
     * @return true if all values are unique, false otherwise
     */
    private boolean checkCollision() {

        for (int hashValue = 0; hashValue < collisionStorage.size()-1; ++hashValue) {
            for (int j = hashValue+1; j < collisionStorage.size(); ++j) {
                if (collisionStorage.get(hashValue) == collisionStorage.get(j)) {
                    collisionStorage.clear();  // reset
                    return false;
                }
            }
        }
        return true;
    }

    public void hashSecondTable() {
        if (normalCityStorage.size() <= 1) {return;}  // 1 or less is good i think

        // keep on rehashing until collision free
        boolean collisionFree = false;
        while (!collisionFree) {
            rehash();
            collisionFree = checkCollision();
        }

        // just printing out for debugging
        for (int i = 0; i < collisionStorage.size(); ++i) {
            System.out.println("second hash: " + collisionStorage.get(i));
        }
        collisionStorage.clear();  // reset
        System.out.println("--------------");

        // PROGRESS: all secondary hash table is now collision free
        // TODO: just store it now and go to next part of project

    }
}
