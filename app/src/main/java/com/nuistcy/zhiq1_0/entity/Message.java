package com.nuistcy.zhiq1_0.entity;

import cn.bmob.v3.BmobObject;

public class Message extends BmobObject {
    private Long senderid;
    private Long receiverid;
    private String time;
    private String content;

    public Message(){}
    public Message(Long senderid, Long receiverid, String time, String content) {
        this.senderid = senderid;
        this.receiverid = receiverid;
        this.time = time;
        this.content = content;
    }

    public Long getSenderid() {
        return senderid;
    }

    public void setSenderid(Long senderid) {
        this.senderid = senderid;
    }

    public Long getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(Long receiverid) {
        this.receiverid = receiverid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
