package com.nuistcy.zhiq1_0.entity;

import cn.bmob.v3.BmobObject;

public class Relationship extends BmobObject {
    private Long myid;
    private Long yourid;
    private Integer isagreed;
    public Relationship(){}

    public Relationship(Long myid, Long yourid,Integer isagreed) {
        this.myid = myid;
        this.yourid = yourid;
        this.isagreed = isagreed;
    }

    public Long getMyid() {
        return myid;
    }

    public void setMyid(Long myid) {
        this.myid = myid;
    }

    public Long getYourid() {
        return yourid;
    }

    public void setYourid(Long yourid) {
        this.yourid = yourid;
    }

    public Integer getIsagreed() {
        return isagreed;
    }

    public void setIsagreed(Integer isagreed) {
        this.isagreed = isagreed;
    }
}
