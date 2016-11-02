package cn.com.cjland.careplus.bean;

/**
 * Created by Administrator on 2016/1/20.
 */
public class JournalismBean {
    private String type;
    private String title;
    private String journalismId;

    public String getJournalismId() {
        return journalismId;
    }
    public void setJournalismId(String journalismId) {
        this.journalismId = journalismId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
