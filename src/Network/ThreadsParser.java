package Network;

import Base.ThreadBase;
import GUI.GUI;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ThreadsParser {

    private final static String URL = "https://2ch.hk/b/threads.json";

    public static void getJsonData() {
        try {
            URL url = new URL(URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            System.out.println("REQUEST STATUS: " + status);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            GUI.widgetTableModel.updateData(getThreads(content.toString()));
            in.close();
            con.disconnect();
        } catch (SocketTimeoutException e) {
            System.out.println("Timeout exception");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ThreadBase> getThreads(String raw) {
        ArrayList<ThreadBase> threads = new ArrayList<>();
        //Exception in thread "Timer" org.json.JSONException: Unterminated string at 131748 [character 131749 line 1]
        JSONObject jsonObject = new JSONObject(raw);
        JSONArray msg = (JSONArray) jsonObject.get("threads");

        for (int i = 0; i < msg.length(); i++) {
            JSONObject a = (JSONObject)msg.get(i);
            ThreadBase t = new ThreadBase(a.getString("subject"));
            t.comment = a.getString("comment");
            t.posts_count = a.getInt("posts_count");
            t.views = a.getInt("views");
            t.thread_num = a.getString("num");
            t.board = "b";
            t.tablePosition = i;
            t.finish();
            threads.add(t);
        }

        System.out.println("Parsed " + threads.size() + " threads!");

        return threads;
    }

}
