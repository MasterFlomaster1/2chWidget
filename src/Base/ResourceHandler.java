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
            return new ImageIcon(ImageIO.read(ResourceHandler.class.getResource("/refresh.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Icon getImageButtonIcon() {
        try {
            return new ImageIcon(ImageIO.read(ResourceHandler.class.getResource("/image.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Icon getVideoButtonIcon() {
        try {
            return new ImageIcon(ImageIO.read(ResourceHandler.class.getResource("/movie.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Icon getWebsiteButtonIcon() {
        try {
            return new ImageIcon(ImageIO.read(ResourceHandler.class.getResource("/website.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Icon getExitButtonIcon() {
        try {
            return new ImageIcon(ImageIO.read(ResourceHandler.class.getResource("/cancel.png")));
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

    public static Icon getFilesButtonIcon() {
        try {
            return new ImageIcon(ImageIO.read(ResourceHandler.class.getResource("/save.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
