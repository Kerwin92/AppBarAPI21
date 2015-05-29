package com.brainstorm.neckup.adapter;

/**
 * Created by kerwin on 15-5-18.
 */
public class CommentItem {
    private String ivHead;
    private String tvNickname;
    private String tvComment;
    private String tvTime;
    private int tvCount;
    private int ivLike;
    private int isShow = 0;

    public String getIvHead() {
        return ivHead;
    }

    public void setIvHead(String ivHead) {
        this.ivHead = ivHead;
    }

    public String getTvNickname() {
        return tvNickname;
    }

    public void setTvNickname(String tvNickname) {
        this.tvNickname = tvNickname;
    }

    public String getTvComment() {
        return tvComment;
    }

    public void setTvComment(String tvComment) {
        this.tvComment = tvComment;
    }

    public String getTvTime() {
        return tvTime;
    }

    public void setTvTime(String tvTime) {
        this.tvTime = tvTime;
    }

    public int getTvCount() {
        return tvCount;
    }

    public void setTvCount(int tvCount) {
        this.tvCount = tvCount;
    }

    public int getIvLike() {
        return ivLike;
    }

    public void setIvLike(int ivLike) {
        this.ivLike = ivLike;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }
}
