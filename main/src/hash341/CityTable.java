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

    private static ArrayList<City> CityArray = new ArrayList<City>();

    // plan b
    private static ArrayList[] primaryCityHash = new ArrayList[16000];
    private static ArrayList<City> secondaryCityHash;


    // plan a
    private static ArrayList<ArrayList<City>> hashCityArray = new ArrayList<ArrayList<City>>(16000);
    private static ArrayList<City> secondTableArray;


    public static ArrayList<Integer> hashIntegerArray = new ArrayList<Integer>();  // testing

    // constructor
    public CityTable(String fname, int tsize) {
        this.fname = fname;
        this.tsize = tsize;
        readFromFile(this.fname);
    }

    public CityTable() {
        this.fname = "";
        this.tsize = 16000;
    }

    // getters
    public String getFname() {return fname;}
    public int getTsize() {return tsize;}
    public ArrayList<City> getArray() {return CityArray;}

    // setters
    public void setFname(String fname) {this.fname = fname;}
    public void setTsize(int tsize) {this.tsize = tsize;}



    /**
     * the dump thing
     */
    public void dump() {
        int prime1 = 0;
        int prime2 = 0;
        int num = 0;
        System.out.println("*** Hash24 dump ***");
        System.out.println("prime1" + prime1);
        System.out.println("prime2" + prime2);
        System.out.println("randome_a" + num);
        System.out.println("randome_b" + num);
        System.out.println("randome_c" + num);
    }

    /**
     * number of city read in
     * @return
     */
    public int numCities() {
        return 0;
    }


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


            // ------------ for cityArray (just testing) ------------------------------
            // just getting the city and state
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


            CityArray.add(new City(cityAndState, latitude, longitude));

            // ------------ for hashCityArray (actual project thing)------------------------------
            //  hash24 % tsize, i think this has to be new everytime
            Hash24 h1 = new Hash24();

            // PLAN::MAYBE WORK IDK
            /**
             * check if index at hashCityArray is empty
             *   if empty, then make a new ArrayList with object City
             *   else, use the already created ArrayList with object City and just add it
             */
            int primaryIndex = h1.hash(cityAndState) % tsize;
            if (primaryCityHash[primaryIndex] != null) {  // if the hash already exist (meaning collision I think)

                primaryCityHash[primaryIndex].add(new City(cityAndState, latitude, longitude));

                // hashCityArray.get(primaryIndex).add(new City(cityAndState, latitude, longitude));

            } else {  // if the hash is new
                secondTableArray = new ArrayList<City>();
                primaryCityHash[primaryIndex] = secondTableArray;
                primaryCityHash[primaryIndex].add(new City(cityAndState, latitude, longitude));

            }

            hashIntegerArray.add(h1.hash(cityAndState) % tsize);  // tsize is like table size or something i think
//            System.out.println(h1.hash(cityAndState) % 10000);
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
     * insert into perfect hash table
     * @param x
     */
    public void insert(int x) {

    }

    /**
     * search perfect hash table
     * @param x
     * @return
     */
    public boolean find(String x) {

        return false;
    }



    /*
    max number of collisions in a slot of primary hash table
     */

    /*
    for i between [0, 24] nums of primary hash table slots that have i collisions
     */

    /*
    all cities in the primary has table slot that has the largest nums of collisions
    if more than 1, pick whatever
     */

    /*
    for j between [1, 20] nums of secondary hash tables that tried j hash functions,
    to find a hash function that did not result in collisions.
    Include only the cases where at least 2 cities hashed to
    *** Include only the cases where at least 2 cities hashed to the same primary hash table slot ***
     */

    /*
    The average number of hash functions tried per slot of the primary hash table that
     had at least two items.
     */


    /**
     *
     * find next prime
     * @param num
     * @return
     */
    private static int find_prime(int num) {
        if (num <= 2) {return num;}
        if (num <= 3) {return num;}

        if (num % 2 == 0 || num % 3 == 0) {
            return find_prime(++num);
        }

        for (int i = 5; i*i <= num; i = i + 6) {
            if (num % i == 0 || num % (i + 2) == 0) {
                return find_prime(++num);
            }
        }
        return num;
    }
}