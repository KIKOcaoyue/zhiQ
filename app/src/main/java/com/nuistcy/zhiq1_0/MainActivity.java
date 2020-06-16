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
        super.onStart();
    }

    private void getalltopic(){
        BmobQuery<Topic> alltopic = new BmobQuery<>();
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
                        Log.d("TOPICTEST", bmobresult);
                        ArrayList<Topic> tmplist = gettopiclist(bmobresult);
                        myapp.setTopiclist(tmplist);
                    }else{
                        Toast.makeText(MainActivity.this,"无法获取话题",Toast.LENGTH_LONG).show();
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
            Log.d("TOPICTEST","当前读取的字符为:"+str.charAt(i));
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
}
