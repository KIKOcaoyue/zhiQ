package com.nuistcy.zhiq1_0.activity;

import androidx.appcompat.app.AppCompatActivity;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nuistcy.zhiq1_0.MainActivity;
import com.nuistcy.zhiq1_0.R;
import com.nuistcy.zhiq1_0.entity.Notification;
import com.nuistcy.zhiq1_0.entity.Relationship;
import com.nuistcy.zhiq1_0.entity.User;
import com.nuistcy.zhiq1_0.status.MyApplication;

import org.w3c.dom.Text;

public class ViewNotificationActivity extends AppCompatActivity {

    private static final int UPDATEREAD = 11;
    private static final int AGREESUCCESS = 12;
    private TextView notifititletxt;
    private TextView noticontenttxt;
    private TextView notitimetxt;
    private Button btncheck;
    private String notifititle;
    private String noticontent;
    private String notitime;
    private String notificationid;
    private Long requestid;
    private String bmobresult;
    private MyApplication myapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notification);
        initView();
        myapp = MyApplication.getmyapp();
        Bmob.initialize(this,myapp.getBmobkey());
        Intent intent = getIntent();
        notifititle = intent.getStringExtra("notifitxt");
        noticontent = intent.getStringExtra("noticontent");
        notitime = intent.getStringExtra("notifitimetxt");
        notificationid = intent.getStringExtra("notificationidtxt");
        requestid = Long.parseLong(intent.getStringExtra("requestidtxt"));
        notifititletxt.setText(notifititle);
        noticontenttxt.setText(noticontent);
        notitimetxt.setText(notitime);
        setisread(notificationid);
        if(notifititle.equals("您有新朋友")){
            btncheck.setText("同意加为好友");
            btncheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    agreefriend(myapp.getCurrentuser().getUserid(),requestid);
                }
            });
        }else if(notifititle.equals("您有新消息")){
            btncheck.setText("进入聊天");
            btncheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    private void initView(){
        notifititletxt = (TextView) findViewById(R.id.notifititletxt);
        noticontenttxt = (TextView) findViewById(R.id.noticontenttxt);
        notitimetxt = (TextView) findViewById(R.id.notitimetxt);
        btncheck = (Button) findViewById(R.id.btncheck);
    }

    private void setisread(String id){
        Log.d("NOTITEST",id);
        Notification notification = new Notification();
        notification.setIsread(1);
        notification.update(id,new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    handlersendmessage("UPDATESUCCESS");
                }else{
                    handlersendmessage("UPDATEFAILED");
                }
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case UPDATEREAD:
                    bmobresult = (String) msg.obj;
                    Log.d("NOTITEST",bmobresult);
                    if(updatesuccess(bmobresult)==true){

                    }else{
                        Toast.makeText(ViewNotificationActivity.this,"无法更新数据库",Toast.LENGTH_LONG).show();
                    }
                    break;
                case AGREESUCCESS:
                    bmobresult = (String) msg.obj;
                    if(agreesuccess(bmobresult)){
                        Toast.makeText(ViewNotificationActivity.this,"加好友成功啦!",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(ViewNotificationActivity.this,"无法更新数据库",Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void handlersendmessage(String str){
        Message msg = Message.obtain();
        msg.what = UPDATEREAD;
        msg.obj = str;
        handler.sendMessage(msg);
    }

    private void handlersendmessageagr(String str){
        Message msg = Message.obtain();
        msg.what = AGREESUCCESS;
        msg.obj = str;
        handler.sendMessage(msg);
    }

    private boolean updatesuccess(String str){
        if(str.equals("UPDATESUCCESS")){
            return true;
        }
        return false;
    }

    private void agreefriend(Long myid,Long yourid){
        Relationship rlp = new Relationship();
        rlp.setMyid(myid);
        rlp.setYourid(yourid);
        rlp.setIsagreed(1);
        rlp.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    handlersendmessageagr("AGREESUCCESS");
                }else{
                    handlersendmessageagr("AGREEFAILED");
                }
            }
        });
    }

    private boolean agreesuccess(String str){
        if(str.equals("AGREESUCCESS")){
            return true;
        }
        return false;
    }
}