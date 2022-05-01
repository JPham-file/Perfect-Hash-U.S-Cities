package hash341;
import java.util.Objects;
import java.util.Scanner;

public class UserFindCity {
    public static void main(String[] args) {
        boolean quit = false;
        Scanner inputObject = new Scanner(System.in);

        hash341.CityTable US_Cities;
        US_Cities = hash341.CityTable.readFromFile("US_Cities_LL.ser") ;

        // running program
        while (!quit) {
            System.out.println("Enter \"Q\" to exit");
            System.out.print("input City and State name here (i.e. \"Dallas, TX\"): ");
            String cityAndState = inputObject.nextLine();

            if (Objects.equals(cityAndState, "Q") || Objects.equals(cityAndState, "q")) {
                System.out.println("Exiting Program\n");
                quit = true;
            } else if (US_Cities.find(cityAndState) == null) {
                System.out.println("did not find: " + cityAndState + "\n");
            } else {
                    float latitude = US_Cities.find(cityAndState).latitude;
                    float longitude = US_Cities.find(cityAndState).longitude;
                    String name = US_Cities.find(cityAndState).name;
                    System.out.println("Found " + name + " (" + latitude + "," + longitude + ")");
                    System.out.println("https://www.google.com/maps?z=10&q=" + latitude + "," + longitude + "\n");
            }

        }
    }
}
