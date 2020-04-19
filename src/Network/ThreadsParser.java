package Network;

import Base.ThreadBase;
import GUI.WidgetTableModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ThreadsParser {

    private static WidgetTableModel widgetTableModel = new WidgetTableModel();

    public static void getJsonData() {
        try {
            URL url = new URL("https://2ch.hk/b/index.json");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            System.out.println("REQUEST STATUS: " + status+"\n");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            getThreads(content.toString());
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
        System.out.println("Parsed " + msg.length() + " threads");
        System.out.println("\n--------------------------------");
        for (int i = 0; i < msg.length(); i++) {
            JSONObject a = (JSONObject)msg.get(i);
            JSONArray b = (JSONArray) a.get("posts");
            a = (JSONObject)b.get(0);
            threads.add(new ThreadBase(a.getString("comment")));
        }
        System.out.println("--------------------------------\n");
        widgetTableModel.updateData(threads);
        return threads;
    }

}
