package Network;

import Base.AttachedFileTypes;
import Base.ThreadBase;
import GUI.GUI;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ThreadsParser {

    private final static String URL = "https://2ch.hk/aa/threads.json";

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

    /**
     * This method performs HTTP request using <code>{@link #request(java.net.URL)}</code>
     * @param threadContentURL your url
     * @return json raw data
     */
    public static String request(URL threadContentURL) {

        String raw;

        try {
            HttpURLConnection con = (HttpURLConnection) threadContentURL.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            System.out.println("REQUEST STATUS: " + status);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            raw = content.toString();
            in.close();
            con.disconnect();
        } catch (SocketTimeoutException e) {
            System.out.println("Timeout exception");
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return raw;
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
            t.threadNumber = a.getString("num");
            t.board = "aa";
            t.tablePosition = i;
            t.finish();
            threads.add(t);
        }

        return threads;
    }

    public static ArrayList<URL> getAttachedFilesLinks(ThreadBase threadBase, String attachedFileType) {
        ArrayList<URL> imagesURLs = new ArrayList<>();

        String raw = request(threadBase.threadContentURL);

        JSONObject content = new JSONObject(raw);
        JSONArray posts = content.getJSONArray("threads").getJSONObject(0).getJSONArray("posts");

        for (int i = 0; i < posts.length(); i++) {
            JSONObject post = (JSONObject)posts.get(i);
            JSONArray attachedFiles = (JSONArray)post.get("files");

            for (int j = 0; j < attachedFiles.length(); j++) {
                JSONObject attachedFile = (JSONObject)attachedFiles.get(j);
                String fileName = attachedFile.getString("name");

                switch (attachedFileType) {
                    case AttachedFileTypes.ALL:
                        try {
                            imagesURLs.add(new URL(threadBase.fileLink+fileName));
                            System.out.println("\033[0;94m" + threadBase.fileLink+fileName + "\u001b[0m");
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case AttachedFileTypes.IMAGES:
                        if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".gif")) {
                            try {
                                imagesURLs.add(new URL(threadBase.fileLink+fileName));
                                System.out.println("\033[0;94m" + threadBase.fileLink+fileName + "\u001b[0m");
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case AttachedFileTypes.VIDEOS:
                        if (fileName.endsWith(".mp4") || fileName.endsWith(".webm")) {
                            try {
                                imagesURLs.add(new URL(threadBase.fileLink+fileName));
                                System.out.println("\033[0;94m" + threadBase.fileLink+fileName + "\u001b[0m");
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }
            }
        }

        System.out.println("Parsed " + imagesURLs.size() + " files!");

        return imagesURLs;
    }

}
