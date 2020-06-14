package com.nuistcy.zhiq1_0.entity;

import android.view.View;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class User extends BmobObject {
    private String username;
    private String userid;
    private String userpwd;
    public User(){}

    public User(String username, String userid, String userpwd) {
        this.username = username;
        this.userid = userid;
        this.userpwd = userpwd;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
