package Base;

import java.util.Date;

public class ThreadBase {

    private final String subject;
    private String comment;
    private boolean op;
    private int files_count;
    private int posts_count;
    private String thread_num;
    private Date date;
    private boolean banned;

    public ThreadBase(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

}
