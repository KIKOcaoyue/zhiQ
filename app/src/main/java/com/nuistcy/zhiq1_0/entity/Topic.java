package com.nuistcy.zhiq1_0.entity;

import java.util.Date;

import cn.bmob.v3.BmobObject;
public class Topic extends BmobObject {
    private String askername;
    private Long topicid;
    private String thumbsups;
    private Long askerid;
    private String introduction;
    private String title;
    private String hot;

    public Topic(){}
    public Topic(String askername, Long topicid, Long askerid, String title, String introduction, String thumbsups, String hot, Date time) {
        this.askername = askername;
        this.topicid = topicid;
        this.thumbsups = thumbsups;
        this.askerid = askerid;
        this.introduction = introduction;
        this.title = title;
        this.hot = hot;
    }

    public Long getTopicid() {
        return topicid;
    }

    public void setTopicid(Long topicid) {
        this.topicid = topicid;
    }

    public Long getAskerid() {
        return askerid;
    }

    public void setAskerid(Long askerid) {
        this.askerid = askerid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getThumbsups() {
        return thumbsups;
    }

    public void setThumbsups(String thunbsups) {
        this.thumbsups = thunbsups;
    }

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public String getAskername() {
        return askername;
    }

    public void setAskername(String askername) {
        this.askername = askername;
    }
}
