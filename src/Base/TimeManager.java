package Base;

import Network.ThreadsParser;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeManager {

    public void startTimer() {
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                System.out.println("Task performed on " + new Date());
                ThreadsParser.getJsonData();
            }
        };
        Timer timer = new Timer();

        long delay  = 1000L;
        long period = 300000L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }

}
