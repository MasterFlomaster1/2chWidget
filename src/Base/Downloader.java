package Base;

import Network.ThreadsParser;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Downloader {



    public static void downloadImages(ThreadBase threadBase, String attachedFileType) {
        Path path = Paths.get(System.getProperty("user.home"), "Desktop", threadBase.threadNumber);

        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
            //debug
            return;
        }

        ArrayList<URL> arrayList = ThreadsParser.getAttachedFilesLinks(threadBase, attachedFileType);

//        if (true) return;

        for (URL link : arrayList) {
            //test
            String fileName = link.toString().substring(link.toString().lastIndexOf('/')+1);
            try (BufferedInputStream in = new BufferedInputStream(link.openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(Paths.get(path.toString(), fileName).toString())) {
                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
                System.out.println("\033[0;92m"+"File '"+fileName+"' was downloaded successfully"+"\u001b[0m");
            } catch (IOException e) {
                System.out.println("\033[0;91m"+"Error occurred while downloading '"+fileName+"'"+"\u001b[0m");
            }
        }
    }

}
