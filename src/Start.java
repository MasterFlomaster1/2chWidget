import Network.ThreadsParser;
import GUI.GUI;

public class Start {


    public static void main(String[] args) {
        System.out.println("Program running");
        new GUI();
        ThreadsParser.getJsonData();
    }


}
