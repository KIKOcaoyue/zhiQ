package com.nuistcy.zhiq1_0.entity;

import cn.bmob.v3.BmobObject;

public class Answer extends BmobObject {
    private Long answerid;
    private Long authorid;
    private String title;
    private String content;
    public Answer(){ }

    public Answer(Long answerid, Long authorid, String title, String content) {
        this.answerid = answerid;
        this.authorid = authorid;
        this.title = title;
        this.content = content;
    }

    public Long getAnswerid() {
        return answerid;
    }

    public void setAnswerid(Long answerid) {
        this.answerid = answerid;
    }

    public Long getAuthorid() {
        return authorid;
    }

    public void setAuthorid(Long authorid) {
        this.authorid = authorid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString(){
        return this.answerid+","+this.authorid+","+this.title+","+this.content;
    }
}
