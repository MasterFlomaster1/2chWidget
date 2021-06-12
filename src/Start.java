import Base.TimeManager;
import GUI.GUI;

public class Start {

    public static void main(String[] args) {
        new GUI();
        new TimeManager().startTimer();
    }

}
