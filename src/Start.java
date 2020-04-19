import Base.TimeManager;
import GUI.GUI;

public class Start {


    public static void main(String[] args) {
        System.out.println("Program running");
        new GUI();
        new TimeManager().startTimer();
    }


}
