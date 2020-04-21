package Base;

public class ThreadBase {

    public String subject = "-";
    public String comment = "-";
    public int views;
    public int posts_count;
    public String thread_num;
    public String link;
    public String board;
    public int tablePosition;
    public boolean hidden;

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
        link = "https://2ch.hk/"+board+"/res/"+thread_num+".html";
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
