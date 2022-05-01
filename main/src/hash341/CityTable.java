package hash341;

import java.util.*;
import java.io.*;


/**
 * construct hash table using perfect hash
 */
public class CityTable implements Serializable {
    static int primaryIndex;
    private int tsize;
    private SecondTable[] primaryTable;
    private int[] secondTableCollisions; // keep track of collision in second table
    private int numberOfCities;
    private Hash24 h1;

    // constructor
    public CityTable(String fname, int tsize) {
        this.tsize = tsize;
        primaryTable = new SecondTable[tsize];
        secondTableCollisions = new int[tsize];
        h1 = new Hash24();

        // create all City object and store City object into Object SecondTable which is stored in array of primaryTable
        readNormalFile(fname);

        // now hash for second table
        for (int i = 0; i < tsize; ++i) {
            if (primaryTable[i] == null) {
                continue;
            }
            primaryTable[i].hashSecondTable();
        }

        // storing second hash number of hashes required
        for (int i = 0; i < tsize; ++i) {
            if (primaryTable[i] == null) {
                continue;
            }

            for (int j = 1; j < 15; ++j) {
                if (primaryTable[i].getCollisionCounter() == j) {
                    secondTableCollisions[j]++;
                }
            }
        }
        h1.dump();
        dump();
        writeToFile("US_Cities_LL.ser");
    }

    public CityTable() {
    }

    /**
     * read from serialize file
     *
     * @param fileName serialize file input
     * @return perfect hash table
     */
    public static CityTable readFromFile(String fileName) {
        FileInputStream inputFile = null;
        CityTable table = new CityTable();
        try {
            inputFile = new FileInputStream(fileName);
            ObjectInputStream inputStream = new ObjectInputStream(inputFile);

            table.setTsize((Integer) inputStream.readObject());
            table.setH1((Hash24) inputStream.readObject());
            table.setPrimaryTable((SecondTable[]) inputStream.readObject());


            inputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return table;
    }

    /**
     * set Hash24 object
     *
     * @param h1 Hash24 object to copy
     */
    public void setH1(Hash24 h1) {
        this.h1 = h1;
    }

    /**
     * copy over Perfect Hash table
     *
     * @param primaryTable Perfect Hash table to copy
     */
    public void setPrimaryTable(SecondTable[] primaryTable) {
        this.primaryTable = primaryTable;
    }

    /**
     * @param tsize size to set table
     */
    public void setTsize(int tsize) {
        this.tsize = tsize;
    }

    /**
     * read in file
     * parse the city and state first
     * than parse coords for longitude and latitude
     *
     * @param fileName US cities file and coordinates
     */
    public void readNormalFile(String fileName) {

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
            StringBuilder cityAndState;
            StringTokenizer tokenizer = new StringTokenizer(line);


            // getting name of city
            cityAndState = new StringBuilder(tokenizer.nextToken());
            while (tokenizer.hasMoreTokens()) {
                cityAndState.append(" ").append(tokenizer.nextToken());
            }

            // now get the coords
            line = infile.next();
            float latitude = Float.parseFloat(line);

            line = infile.next();
            float longitude = Float.parseFloat(line);

            infile.nextLine();  // has to get the "" before next line now


            // hashing object City
            primaryIndex = h1.hash(cityAndState.toString()) % tsize;

            // if collision, i.e. the same hash
            if (primaryTable[primaryIndex] != null) {
                primaryTable[primaryIndex].setNormalCityStorage(new City(cityAndState.toString(), latitude, longitude));

                if (primaryTable[primaryIndex].collision == 0) {
                    primaryTable[primaryIndex].collision++;
                }
                primaryTable[primaryIndex].collision++;  // keeping track of collision at specific hash

            } else {  // new hash
                SecondTable newSecondTable = new SecondTable();
                newSecondTable.setNormalCityStorage(new City(cityAndState.toString(), latitude, longitude));
                primaryTable[primaryIndex] = newSecondTable;
            }
            ++numberOfCities;
        }

        infile.close();

    }

    /**
     * dump method
     * output statistics of constructing perfect hash table
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

        System.out.println("\n--------------Primary table collision--------------");
        System.out.println("table size: " + tsize);
        System.out.println("Number of cities: " + numberOfCities);
        System.out.println("Max numbers of collision: " + max);

        for (int i = 0; i < 15; ++i) {
            System.out.println(i + " Collisions: " + numsCollsions[i]);
        }

        System.out.println("\n--------------Cities in hash with most collisions--------------");
        System.out.println(primaryTable[maxIndex].getNormalCityStorage());

        System.out.println("\n--------------Second table numbers of rehash required--------------");
        for (int i = 1; i < 19; ++i) {
            System.out.println("number of secondary hash tables trying " + i + " hash functions = " + secondTableCollisions[i]);
        }
    }

    /**
     * write to file as objects
     *
     * @param fName the serialize file name for object
     */
    public void writeToFile(String fName) {
        FileOutputStream outfile = null;
        try {
            outfile = new FileOutputStream(fName);
            ObjectOutputStream out = new ObjectOutputStream(outfile);

            out.writeObject(tsize);
            out.writeObject(h1);
            out.writeObject(primaryTable);


            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

    /**
     * find specific city within PrimaryTable -> secondTable
     *
     * @param cName the city name i.e. "Dallas, TX"
     * @return City object if found, return null if not found
     */
    public City find(String cName) {
        primaryIndex = h1.hash(cName) % tsize;
        if (primaryTable[primaryIndex] == null) {
            return null;
        }

        for (int i = 0; i < primaryTable[primaryIndex].secondTableSize; ++i) {
            if (primaryTable[primaryIndex].getSecondTable()[i] == null) {
                continue;
            }

            // primaryTable[] -> secondaryTable[] -> City -> name  == cName
            if (Objects.equals(primaryTable[primaryIndex].getSecondTable()[i].name, cName)) {
                return primaryTable[primaryIndex].getSecondTable()[i];
            }
        }
        return null;
    }
}