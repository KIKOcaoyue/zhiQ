package com.nuistcy.zhiq1_0.entity;

import cn.bmob.v3.BmobObject;

public class Notification extends BmobObject {
    private Long requestid;
    private String notificationid;
    private Long userid;
    private String title;
    private String content;
    private Integer isread;
    private String time;
    public Notification(){ }

    public Notification(Long requestid, String notificationid, Long userid, String title, String content, Integer isread,String time) {
        this.requestid = requestid;
        this.notificationid = notificationid;
        this.userid = userid;
        this.title = title;
        this.content = content;
        this.isread = isread;
        this.time = time;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
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

    public Integer getIsread() {
        return isread;
    }

    public void setIsread(Integer isread) {
        this.isread = isread;
    }

    public String getNotificationid() {
        return notificationid;
    }

    public void setNotificationid(String notificationid) {
        this.notificationid = notificationid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getRequestid() {
        return requestid;
    }

    public void setRequestid(Long requestid) {
        this.requestid = requestid;
    }

    public String toString(){
        return this.title+","+this.requestid+","+this.notificationid+","+this.content+","+this.time+","+this.userid;
    }
}
