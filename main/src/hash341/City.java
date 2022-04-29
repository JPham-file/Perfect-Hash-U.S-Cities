package hash341;


import java.io.Serializable;

public class City implements Serializable {
    public  String name;
    public  float latitude;
    public  float longitude;

    public City(String name, float latitude, float longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public City() {
        this.name = "NONE";
        this.latitude = -1f;
        this.longitude = -1f;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityAndState = '" + name + '\'' +
                ", latitude = " + latitude + ", longitude = " + longitude + '}' + "\n";
    }
}