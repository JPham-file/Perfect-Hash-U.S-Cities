package hash341;
import java.awt.Desktop;
import java.net.URI;

public class ReadHashTable {
    static void openURL (String url) {
    // cribbed from:
    // http://java-hamster.blogspot.com/2007/06/troubles-with-javaawtdesktop-browse.html
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(new URI(url));
                } else {
                    System.out.println("No browser. URL = " + url) ;
                }
            } else {
                System.out.println("No desktop. URL = " + url) ; }
        } catch (Exception e) { e.printStackTrace(); }
    }
}
