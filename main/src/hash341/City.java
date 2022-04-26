package hash341;


public class City {
    protected  String cityAndState;
    protected  float latitude;
    protected  float longitude;

    public City(String cityAndState, float latitude, float longitude) {
        this.cityAndState = cityAndState;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setCityAndState(String city) {
        cityAndState = city;
    }
    public void setLatitude(float lat) {latitude = lat;}
    public void setLongitude(float longit) {
        longitude = longit;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityAndState = '" + cityAndState + '\'' +
                ", latitude = " + latitude +
                ", longitude = " + longitude +
                '}';
    }
}