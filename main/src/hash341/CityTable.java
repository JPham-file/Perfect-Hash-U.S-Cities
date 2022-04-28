package hash341;
import java.util.*;
import java.io.*;


/**
 *
 * construct hash table using perfect hash
 * tsize = primary hash table size
 */
public class CityTable {
    private String fname;
    private static int tsize;
    private static int numberOfCities;

    private static SecondTable[] primaryTable = new SecondTable[16000];


    private static int[] numbersOfCollisions = new int[16000];  // keep track of the number of collision at index


    // constructor
    public CityTable(String fname, int tsize) {
        this.fname = fname;
        this.tsize = tsize;
        readFromFile(this.fname);

        // now hash for second table
        for (int i = 0; i < 16000; ++i) {
            if (primaryTable[i] == null) {continue;}
            primaryTable[i].hashSecondTable();
        }

        System.out.println("DONE");


    }

    public CityTable() {
        this.fname = "";
        this.tsize = 16000;
    }

    // getters
    public String getFname() {return fname;}
    public int getTsize() {return tsize;}

    // setters
    public void setFname(String fname) {this.fname = fname;}
    public void setTsize(int tsize) {this.tsize = tsize;}



    /**
     * the dump thing
     */
    public void dump() {
        // checking collision
        int[] numsCollsions = new int[10];

        int max = 0;
        int maxIndex = 0;
        for (int i = 0; i < 16000; ++i) {

            // no cities
            if (primaryTable[i] == null) {
                numsCollsions[0]++;
                continue;
            }

            // max
            int numbersOfCollision = primaryTable[i].getNormalCityStorage().size();
            if (primaryTable[i].getNormalCityStorage().size() > max) {
                max = primaryTable[i].getNormalCityStorage().size();
                maxIndex = i;
            }

            // collision counter
            if (numbersOfCollision == 1){numsCollsions[1]++;}
            else if (numbersOfCollision == 2){numsCollsions[2]++;}
            else if (numbersOfCollision == 3){numsCollsions[3]++;}
            else if (numbersOfCollision == 4){numsCollsions[4]++;}
            else if (numbersOfCollision == 5){numsCollsions[5]++;}
            else if (numbersOfCollision == 6){numsCollsions[6]++;}
            else if (numbersOfCollision == 7){numsCollsions[7]++;}
            else if (numbersOfCollision == 8){numsCollsions[8]++;}
            else if (numbersOfCollision == 9){numsCollsions[9]++;}

        }

        System.out.println("--------------DUMP FILE--------------");
        System.out.println("table size: " + tsize);
        System.out.println("Number of cities: " + numberOfCities);
        System.out.println("Max numbers of collision: " + max);

        System.out.println("Zero Collisions: " + numsCollsions[0]);
        System.out.println("One Collisions: " + numsCollsions[1]);
        System.out.println("Two Collisions: " + numsCollsions[2]);
        System.out.println("Three Collisions: " + numsCollsions[3]);
        System.out.println("Four Collisions: " + numsCollsions[4]);
        System.out.println("Five Collisions: " + numsCollsions[5]);
        System.out.println("Six Collisions: " + numsCollsions[6]);
        System.out.println("Seven Collisions: " + numsCollsions[7]);
        System.out.println("Eight Collisions: " + numsCollsions[8]);
        System.out.println("Nine Collisions: " + numsCollsions[9]);

        System.out.println("--------------Cities in hash with most collisions--------------");
        System.out.println(primaryTable[maxIndex].getNormalCityStorage());
    }

    /**
     * number of city read in
     * @return
     */
    public int numCities() { return numberOfCities;}


    /**
     * read in file
     * parse the city and state first
     * than parse coords for longitude and latitude
     * @param fileName
     */
    public static CityTable readFromFile(String fileName) {

        // basic opening file
        Scanner infile = null;
        try {
            infile = new Scanner(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("FIle not found");
            e.printStackTrace();
            System.exit(0);
        }

        while(infile.hasNextLine()) {
            String line = infile.nextLine();
            String cityAndState;
            StringTokenizer tokenizer = new StringTokenizer(line);


            // getting name of city
            cityAndState = tokenizer.nextToken();
            while (tokenizer.hasMoreTokens()) {
                cityAndState += " " + tokenizer.nextToken();
            }

            // now get the coords
            line = infile.next();
            Float latitude = Float.parseFloat(line);

            line = infile.next();
            Float longitude = Float.parseFloat(line);

            infile.nextLine();  // has to get the "" before next line now


            // hashing object City
            Hash24 h1 = new Hash24();
            int primaryIndex = h1.hash(cityAndState) % tsize;

            // if collision, i.e. the same hash
            if (primaryTable[primaryIndex] != null) {
                primaryTable[primaryIndex].setNormalCityStorage(new City(cityAndState, latitude, longitude));

                if (primaryTable[primaryIndex].collision == 0) {
                    primaryTable[primaryIndex].collision++;
                }
                primaryTable[primaryIndex].collision++;  // keeping track of collision at specific hash

            } else {  // new hash
                SecondTable newSecondTable = new SecondTable();
                newSecondTable.setNormalCityStorage(new City(cityAndState, latitude, longitude));
                primaryTable[primaryIndex] = newSecondTable;
            }
            ++numberOfCities;

//            int primaryIndex = hashing.hash(cityAndState) % tsize;
//            if (primaryCityHash[primaryIndex] != null) {  // if the hash already exist (meaning collision I think)
//
//                primaryCityHash[primaryIndex].add(new City(cityAndState, latitude, longitude));
//                numbersOfCollisions[primaryIndex]++;  // number of collision at that hash
//                ++numberOfCities;
//
//            } else {  // if the hash is new
//                secondaryCityHash = new ArrayList<City>();
//                primaryCityHash[primaryIndex] = secondaryCityHash;
//                primaryCityHash[primaryIndex].add(new City(cityAndState, latitude, longitude));
//                ++numberOfCities;
//            }
        }

        infile.close();
        return new CityTable();
    }

    /**
     * write to file
     * @param fName
     */
    public void writeToFile(String fName) {

    }


    /**
     * search perfect hash table
     * @param x
     * @return
     */
    public boolean find(String x) {

        return false;
    }

    public static void resizeArrayList(ArrayList<City> arrayCity, int size) {
        arrayCity.ensureCapacity(size);
        for (int i = 0; i < size; ++i) {
            arrayCity.add(null);
        }
    }

}