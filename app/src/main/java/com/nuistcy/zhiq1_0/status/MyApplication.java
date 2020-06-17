package com.nuistcy.zhiq1_0.status;

import android.app.Application;
import android.content.Context;

import com.nuistcy.zhiq1_0.entity.Notification;
import com.nuistcy.zhiq1_0.entity.Relationship;
import com.nuistcy.zhiq1_0.entity.Topic;
import com.nuistcy.zhiq1_0.entity.User;

import java.util.ArrayList;

public class MyApplication extends Application {
    private static User currentuser;
    private static ArrayList<Topic> topiclist;
    private static ArrayList<Notification> notificationlist;
    private static ArrayList<Relationship> friendlist;
    private static ArrayList<User> alluserlist;
    private static MyApplication myapp = new MyApplication();
    private static final String bmobkey = "mykey";
    private MyApplication(){}
    public static MyApplication getmyapp(){
        return myapp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static User getCurrentuser() {
        return currentuser;
    }

    public static void setCurrentuser(User currentuser) {
        MyApplication.currentuser = currentuser;
    }

    public static ArrayList<Topic> getTopiclist() {
        return topiclist;
    }

    public static void setTopiclist(ArrayList<Topic> topiclist) {
        MyApplication.topiclist = topiclist;
    }

    public static String getBmobkey() {
        return bmobkey;
    }

    public static ArrayList<Notification> getNotificationlist() {
        return notificationlist;
    }

    public static void setNotificationlist(ArrayList<Notification> notificationlist) {
        MyApplication.notificationlist = notificationlist;
    }

    public static ArrayList<Relationship> getFriendlist() {
        return friendlist;
    }

    public static void setFriendlist(ArrayList<Relationship> friendlist) {
        MyApplication.friendlist = friendlist;
    }

    public static ArrayList<User> getAlluserlist() {
        return alluserlist;
    }

    public static void setAlluserlist(ArrayList<User> alluserlist) {
        MyApplication.alluserlist = alluserlist;
    }
}
