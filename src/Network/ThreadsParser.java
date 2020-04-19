package Network;

import Base.ThreadBase;
import GUI.GUI;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ThreadsParser {

    private final static String URL = "https://2ch.hk/b/threads.json";

    //Upgrade request logic (switch to /catalog or /threads URL)
    public static void getJsonData() {
        try {
            URL url = new URL(URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            System.out.println("REQUEST STATUS: " + status);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            GUI.widgetTableModel.updateData(getThreads(content.toString()));
            in.close();
            con.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ThreadBase> getThreads(String raw) {
        ArrayList<ThreadBase> threads = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(raw);
        JSONArray msg = (JSONArray) jsonObject.get("threads");

        for (int i = 0; i < msg.length(); i++) {
            JSONObject a = (JSONObject)msg.get(i);
            ThreadBase t = new ThreadBase(a.getString("subject"));
            t.comment = a.getString("comment");
            t.posts_count = a.getInt("posts_count");
            t.views = a.getInt("views");
            t.thread_num = a.getString("num");
            threads.add(t);
        }

        System.out.println("Parsed " + threads.size() + " threads!");

        return threads;
    }

}
