package hash341;
import java.util.Objects;
import java.util.Scanner;

public class Program2 {
    public static void main(String[] args) {
        boolean quit = false;
        Scanner inputObject = new Scanner(System.in);
        CityTable US_Cities = new CityTable("US_Cities_LL.txt", 16000);

        while (!quit) {
            System.out.println("Enter \"Q\" to exit");
            System.out.print("input City and State name here (i.e. \"Dallas, TX\"): ");
            String cityAndState = inputObject.nextLine();

            if (Objects.equals(cityAndState, "Q")) {
                System.out.println("Exiting Program");
                quit = true;
            }else if (US_Cities.find(cityAndState) == null) {
                System.out.println("did not find " + cityAndState);
            } else {
                    float latitude = US_Cities.find(cityAndState).latitude;
                    float longitude = US_Cities.find(cityAndState).longitude;
                    String name = US_Cities.find(cityAndState).name;
                    System.out.println("Found " + name + " (" + latitude + "," + longitude + ")");
                    System.out.println("https://www.google.com/maps?z=10&q=" + latitude + "," + longitude);
            }

        }
    }
}
