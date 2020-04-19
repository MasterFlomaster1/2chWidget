package Base;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class BrowserHandler {

    public void openURL(String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                System.out.println("Error occurred while opening URL");
                e.printStackTrace();
            }
        } else {
            System.out.println("Desktop is not supported!");
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (IOException e) {
                System.out.println("Error occurred while opening URL");
                e.printStackTrace();
            }
        }
    }

}
