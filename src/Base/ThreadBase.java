package Base;

import java.net.MalformedURLException;
import java.net.URL;

public class ThreadBase {

    public String subject = "-";
    public String comment = "-";
    public int views;
    public int posts_count;
    public String threadNumber;
    public String link;
    public String board;
    public int tablePosition;
    public boolean hidden;

    public URL threadContentURL;
    public String fileLink;

    public ThreadBase(String subject) {
        if (!subject.equals("")) {
            this.subject = subject.trim();
        }

    }

    public void finish() {
        buildLink();
        resolveHtmlCharacters();
        resolveEmoji();
    }

    private void buildLink() {
        link = "https://2ch.hk/"+board+"/res/"+ threadNumber +".html";
        fileLink = "https://2ch.hk/"+board+"/src/"+threadNumber+"/";
        try {
            threadContentURL = new URL("https://2ch.hk/"+board+"/res/"+threadNumber+".json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void resolveHtmlCharacters() {
        HtmlStringHandler htmlStringHandler = new HtmlStringHandler();
        subject = htmlStringHandler.handleString(subject);
        comment = htmlStringHandler.handleString(comment);
    }

    private void resolveEmoji() {
//        subject = EmojiHandler.resolveEmoji(subject);
//        comment = EmojiHandler.resolveEmoji(subject);
    }

}
