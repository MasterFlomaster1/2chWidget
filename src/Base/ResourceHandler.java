package Base;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ResourceHandler {

    public static Image getApplicationIcon() {
        try {
            return ImageIO.read(ResourceHandler.class.getResource("/rss.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Icon getUpdateButtonIcon() {
        try {
            return new ImageIcon(ImageIO.read(ResourceHandler.class.getResource("/update.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Icon getViewsLabelIcon() {
        try {
            return new ImageIcon(ImageIO.read(ResourceHandler.class.getResource("/eye.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
