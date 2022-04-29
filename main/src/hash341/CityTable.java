package hash341;

import java.util.*;
import java.io.*;


/**
 * construct hash table using perfect hash
 * tsize = primary hash table size
 */
public class CityTable implements Serializable {
    private static  SecondTable[] primaryTable = new SecondTable[16000];
    private static  int[] secondTableCollisions = new int[16000]; // keep track of collision in second table
    private static int tsize;
    private static int numberOfCities;
    private String fname;
    static Hash24 h1 = new Hash24();
    static int primaryIndex;


    public static SecondTable[] getPrimaryTable() {
        return primaryTable;
    }

    // constructor
    public CityTable(String fname, int tsize) {
        this.fname = fname;
        this.tsize = tsize;

        // create all City object and store City object into Object SecondTable which is stored in array of primaryTable
        readFromFile(this.fname);

        // now hash for second table
        for (int i = 0; i < 16000; ++i) {
            if (primaryTable[i] == null) {
                continue;
            }
            primaryTable[i].hashSecondTable();
        }
        writeToFile(this.fname);

        // storing second hash number of hashes required
        for (int i = 0; i < 16000; ++i) {
            if (primaryTable[i] == null) {
                continue;
            }

            for (int j = 1; j < 15; ++j) {
                if (primaryTable[i].getCollisionCounter() == j) {
                    secondTableCollisions[j]++;
                }
            }
        }
        dump();
    }

    public CityTable() {
        this.fname = "";
        this.tsize = 16000;
    }

    /**
     * read in file
     * parse the city and state first
     * than parse coords for longitude and latitude
     *
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

        while (infile.hasNextLine()) {
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
            primaryIndex = h1.hash(cityAndState) % 16000;

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
        }

        infile.close();
        return new CityTable();
    }

    // getters
    public String getFname() {
        return fname;
    }

    // setters
    public void setFname(String fname) {
        this.fname = fname;
    }

    public int getTsize() {
        return tsize;
    }

    public void setTsize(int tsize) {
        CityTable.tsize = tsize;
    }

    /**
     * the dump thing
     */
    public void dump() {
        // checking collision
        int[] numsCollsions = new int[15];

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

            for (int j = 1; j < 15; ++j) {
                if (numbersOfCollision == j) {
                    numsCollsions[j]++;
                }
            }
        }

        System.out.println("--------------DUMP FILE--------------");
        System.out.println("table size: " + tsize);
        System.out.println("Number of cities: " + numberOfCities);
        System.out.println("Max numbers of collision: " + max);

        for (int i = 0; i < 15; ++i) {
            System.out.println(i + " Collisions: " + numsCollsions[i]);
        }

        System.out.println("");
        System.out.println("--------------Cities in hash with most collisions--------------");
        System.out.println(primaryTable[maxIndex].getNormalCityStorage());

        System.out.println("");
        System.out.println("--------------Second Table numbers of rehash required--------------");
        for (int i = 1; i < 19; ++i) {
            System.out.println("number of secondary hash tables trying " + i + " hash functions = " + secondTableCollisions[i]);
        }
    }

    /**
     * number of city read in
     *
     * @return
     */
    public int numCities() {
        return numberOfCities;
    }

    /**
     * write to file
     *
     * @param fName
     */
    public void writeToFile(String fName) {
        // basic opening file
        FileOutputStream outfile = null;
        try {
            outfile = new FileOutputStream("US_Cities_LL.ser");
            ObjectOutputStream out = new ObjectOutputStream(outfile);
            out.writeObject(primaryTable);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

    public City find(String cName) {
        primaryIndex = h1.hash(cName) % 16000;
        for (int i = 0; i < primaryTable[primaryIndex].secondTableSize; ++i) {
            if (primaryTable[primaryIndex] == null) {return null; }
            if (primaryTable[primaryIndex].getSecondTable()[i] == null) {continue; }

            // primaryTable[] -> secondaryTable[] -> City -> name  == cName
            if (Objects.equals(primaryTable[primaryIndex].getSecondTable()[i].name, cName)) {
                System.out.println("Found: " + primaryTable[primaryIndex].getSecondTable()[i].name);
                return primaryTable[primaryIndex].getSecondTable()[i];
            }
        }

        System.out.println("Did not Find: " + cName);
        return null;
    }
}