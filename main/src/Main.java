import hash341.*;
import java.util.*;

import static hash341.CityTable.hashIntegerArray;

public class Main {

    public static void main(String[] args) {

        Hash24 h1 = new Hash24();
        int test = h1.hash("Abington, MA");
        CityTable US_Cities = new CityTable("US_Cities_LL.txt", 16000);

        Collections.sort(hashIntegerArray);
        for (Integer x : hashIntegerArray) {
            System.out.println(x);
        }

//        ArrayList<City> a = US_Cities.getArray();
//        for (City city : a) {
//            System.out.println(city);
//        }
    }
}