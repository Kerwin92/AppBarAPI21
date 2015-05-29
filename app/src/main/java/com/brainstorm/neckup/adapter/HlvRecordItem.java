package com.brainstorm.neckup.adapter;

/**
 * Created by kerwin on 15-5-19.
 */
public class HlvRecordItem {
    private String date;
    private int amDone = 0;
    private int pmDone = 0;
    private String newsTitle;
    private String comment;
    private int likeCount;
    private String amExec;
    private String pmExec;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmDone() {
        return amDone;
    }

    public void setAmDone(int amDone) {
        this.amDone = amDone;
    }

    public int getPmDone() {
        return pmDone;
    }

    public void setPmDone(int pmDone) {
        this.pmDone = pmDone;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getAmExec() {
        return amExec;
    }

    public void setAmExec(String amExec) {
        this.amExec = amExec;
    }

    public String getPmExec() {
        return pmExec;
    }

    public void setPmExec(String pmExec) {
        this.pmExec = pmExec;
    }
}
