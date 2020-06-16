package com.nuistcy.zhiq1_0.entity;

import android.view.View;

import cn.bmob.v3.BmobObject;

public class User extends BmobObject {
    private String username;
    private Long userid;
    private String userpwd;
    private String introduction;
    public User(){}

    public User(String username, Long userid, String userpwd, String introduction) {
        this.username = username;
        this.userid = userid;
        this.userpwd = userpwd;
        this.introduction = introduction;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
