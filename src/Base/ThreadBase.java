package Base;

public class ThreadBase {

    public String subject;
    public String comment;
    public int views;
    public int posts_count;
    public String thread_num;
    public String link;
    public String board;
    public int tablePosition;

    public ThreadBase(String subject) {
        this.subject = subject;
    }

    public void buildLink() {
        link = "https://2ch.hk/"+board+"/res/"+thread_num+".html";
    }

}
