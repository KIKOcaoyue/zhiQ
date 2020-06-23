package com.nuistcy.zhiq1_0;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nuistcy.zhiq1_0.activity.LoginActivity;
import com.nuistcy.zhiq1_0.entity.Notification;
import com.nuistcy.zhiq1_0.entity.Relationship;
import com.nuistcy.zhiq1_0.entity.Topic;
import com.nuistcy.zhiq1_0.entity.User;
import com.nuistcy.zhiq1_0.status.MyApplication;
import com.nuistcy.zhiq1_0.ui.notifications.NotificationsFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends AppCompatActivity {

    private static final int RETURNALLTOPIC = 4;
    private static final int RETURNNOTIFICATION = 10;
    private MyApplication myapp;
    private String bmobresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myapp = MyApplication.getmyapp();
        Bmob.initialize(this,myapp.getBmobkey());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        myapp = MyApplication.getmyapp();
    }

    @Override
    protected void onStart(){
        getalltopic();
        getallnotification();
        super.onStart();
    }

    private void getalltopic(){
        BmobQuery<Topic> alltopic = new BmobQuery<>();
        alltopic.order("-createdAt");
        alltopic.findObjects(new FindListener<Topic>() {
            @Override
            public void done(List<Topic> list, BmobException e) {
                if(e==null){
                    String alltopicstr = "";
                    for(int i=0;i<list.size();i++){
                        alltopicstr += list.get(i).getTopicid();
                        alltopicstr += "!";
                        alltopicstr += list.get(i).getTitle();
                        alltopicstr += "@";
                        alltopicstr += list.get(i).getIntroduction();
                        alltopicstr += "$";
                        alltopicstr += list.get(i).getAskerid();
                        alltopicstr += "%";
                        alltopicstr += list.get(i).getHot();
                        alltopicstr += "^";
                        alltopicstr += list.get(i).getThumbsups();
                        alltopicstr += "*";
                    }
                    handlersendmessage(alltopicstr);
                    Log.d("TOPICTEST","发送了:"+alltopicstr);
                }else{
                    handlersendmessage("NULL");
                }
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case RETURNALLTOPIC:
                    bmobresult = (String) msg.obj;
                    if(findsuccess(bmobresult)==true){
                        ArrayList<Topic> tmplist = gettopiclist(bmobresult);
                        myapp.setTopiclist(tmplist);
                    }else{
                        Toast.makeText(MainActivity.this,"无法获取话题",Toast.LENGTH_LONG).show();
                    }
                    break;
                case RETURNNOTIFICATION:
                    bmobresult = (String) msg.obj;
                    if(findsuccess(bmobresult)){
                        ArrayList<Notification> tmplist = getnotificationlist(bmobresult);
                        myapp.setNotificationlist(tmplist);
//                        for(int i=0;i<tmplist.size();i++){
//                            Log.d("NOTITEST","每一个对象的内容为:"+tmplist.get(i).toString());
//                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void handlersendmessage(String str){
        Message msg = Message.obtain();
        msg.what = RETURNALLTOPIC;
        msg.obj = str;
        handler.sendMessage(msg);
    }

    private void handlersendmessagereq(String str){
        Message msg = Message.obtain();
        msg.what = RETURNNOTIFICATION;
        msg.obj = str;
        handler.sendMessage(msg);
    }



    private boolean findsuccess(String str){
        if(!str.equals("NULL")){
            return true;
        }
        return false;
    }

    private ArrayList<Topic> gettopiclist(String str){
        ArrayList<Topic> tmplist = new ArrayList<>();
        Topic tmptopic = new Topic();
        String tmp = "";
        int state = 0;
        for(int i=0;i<str.length();i++){
            if(state==0){
                tmp += str.charAt(i);
                state = 1;
                continue;
            }
            if(state==1){
                if(str.charAt(i)=='!'){
                    state = 2;
                    tmptopic.setTopicid(Long.parseLong(tmp));
                    tmp = "";
                    continue;
                }
                tmp += str.charAt(i);
            }
            if(state==2){
                if(str.charAt(i)=='@'){
                    state = 3;
                    tmptopic.setTitle(tmp);
                    tmp = "";
                    continue;
                }
                tmp += str.charAt(i);
            }
            if(state==3){
                if(str.charAt(i)=='$'){
                    state = 4;
                    tmptopic.setIntroduction(tmp);
                    tmp = "";
                    continue;
                }
                tmp += str.charAt(i);
            }
            if(state==4){
                if(str.charAt(i)=='%'){
                    state = 5;
                    tmptopic.setAskerid(Long.parseLong(tmp));
                    tmp = "";
                    continue;
                }
                tmp += str.charAt(i);
            }
            if(state==5){
                if(str.charAt(i)=='^'){
                    state = 6;
                    tmptopic.setHot(tmp);
                    tmp = "";
                    continue;
                }
                tmp += str.charAt(i);
            }
            if(state==6){
                if(str.charAt(i)=='*'){
                    state = -1;
                    tmptopic.setThumbsups(tmp);
                    tmp = "";
                }else{
                    tmp += str.charAt(i);
                }

            }
            if(state==-1){
                tmplist.add(tmptopic);
                state = 0;
                tmptopic = new Topic();
                continue;
            }
        }
        return tmplist;
    }

    private void getallnotification(){
        BmobQuery<Notification> allnotification = new BmobQuery<>();
        allnotification.addWhereEqualTo("userid",myapp.getCurrentuser().getUserid());
        BmobQuery<Notification> allnotification2 = new BmobQuery<>();
        allnotification2.addWhereEqualTo("isread",0);
        List<BmobQuery<Notification>> andQuerys = new ArrayList<BmobQuery<Notification>>();
        andQuerys.add(allnotification);
        andQuerys.add(allnotification2);
        BmobQuery<Notification> query = new BmobQuery<>();
        query.and(andQuerys);
        query.findObjects(new FindListener<Notification>() {
            @Override
            public void done(List<Notification> list, BmobException e) {
                if(e==null){
                    String allnotificationstr = "";
                    for(int i=0;i<list.size();i++){
                        allnotificationstr += list.get(i).getTitle();
                        allnotificationstr += "#";
                        allnotificationstr += list.get(i).getContent();
                        allnotificationstr += "^";
                        allnotificationstr += list.get(i).getObjectId();
                        allnotificationstr += "&";
                        allnotificationstr += list.get(i).getCreatedAt();
                        allnotificationstr += "`";
                        allnotificationstr += list.get(i).getRequestid();
                        allnotificationstr += "~";
                    }
                    handlersendmessagereq(allnotificationstr);
                    Log.d("TNOTIFICATIONTEST","发送了:"+allnotificationstr);
                }else{
                    handlersendmessagereq("NULL");
                }
            }
        });
    }

    private ArrayList<Notification> getnotificationlist(String str){
        ArrayList<Notification> tmplist = new ArrayList<>();
        Notification tmpnotifi = new Notification();
        String tmp = "";
        int state = 0;
        for(int i=0;i<str.length();i++){
            if(state==0){
                tmp += str.charAt(i);
                state = 1;
                continue;
            }
            if(state==1){
                if(str.charAt(i)=='#'){
                    state = 2;
                    tmpnotifi.setTitle(tmp);
                    tmp = "";
                    continue;
                }
                tmp += str.charAt(i);
            }
            if(state==2){
                if(str.charAt(i)=='^'){
                    state = 3;
                    tmpnotifi.setContent(tmp);
                    tmp = "";
                    continue;
                }
                tmp += str.charAt(i);
            }
            if(state==3){
                if(str.charAt(i)=='&'){
                    state = 4;
                    tmpnotifi.setNotificationid(tmp);
                    tmp = "";
                    continue;
                }
                tmp += str.charAt(i);
            }
            if(state==4){
                if(str.charAt(i)=='`'){
                    state = 5;
                    tmpnotifi.setTime(tmp);
                    tmp = "";
                    continue;
                }
                tmp += str.charAt(i);
            }
            if(state==5){
                if(str.charAt(i)=='~'){
                    state = -1;
                    tmpnotifi.setRequestid(Long.parseLong(tmp));
                    tmp = "";
                }else{
                    tmp += str.charAt(i);
                }
            }
            if(state==-1){
                tmplist.add(tmpnotifi);
                state = 0;
                tmpnotifi = new Notification();
                continue;
            }
        }
        return tmplist;
    }


}
