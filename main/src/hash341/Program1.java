package hash341;

public class Program1 {

    public static void main(String[] args) {

        Hash24 h1 = new Hash24();
        int test = h1.hash("Abington, MA");
        CityTable US_Cities = new CityTable("US_Cities_LL.txt", 16000);

//        SecondTable[] testing = CityTable.getPrimaryTable();
        System.out.println("done");
        System.out.println(US_Cities.find("Austin, TX"));
    }
}