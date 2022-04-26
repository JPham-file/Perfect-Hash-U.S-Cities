package hash341;

import java.util.ArrayList;

public class SecondTable {
    protected ArrayList<City> cities = new ArrayList<City>();
    private int collsions;


    public void insert(int index, City city) {
        cities.add(index, city);
    }

}
