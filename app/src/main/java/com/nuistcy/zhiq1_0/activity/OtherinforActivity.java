package com.nuistcy.zhiq1_0.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nuistcy.zhiq1_0.MainActivity;
import com.nuistcy.zhiq1_0.R;
import com.nuistcy.zhiq1_0.entity.Notification;
import com.nuistcy.zhiq1_0.entity.Relationship;
import com.nuistcy.zhiq1_0.entity.Topic;
import com.nuistcy.zhiq1_0.entity.User;
import com.nuistcy.zhiq1_0.status.MyApplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OtherinforActivity extends AppCompatActivity {

    private static final int QUERYUSER = 7;
    private static final int ADDFRIEND = 8;
    private static final int INSERTNOTIFICATION = 9;
    private TextView otherusernametxt;
    private TextView otherintrotxt;
    private Button btnsendmessage;
    private Button btnaddfriend;
    private static MyApplication myapp;
    private Long otherid;
    private String bmobresult;
    private ArrayList<User> userlist = new ArrayList<>();
    private boolean flag = false;
    private String myintrostr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otherinfor);
        Bmob.initialize(this,myapp.getBmobkey());
        initView();
        myapp = MyApplication.getmyapp();
        Intent intent = getIntent();
        otherid = intent.getLongExtra("authorid",0);
        queryother();
        btnaddfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendfriendrequest(myapp.getCurrentuser().getUserid(),otherid);
            }
        });
    }
    private void initView(){
        otherusernametxt = (TextView) findViewById(R.id.otherusernametxt);
        otherintrotxt = (TextView) findViewById(R.id.otherintrotxt);
        btnsendmessage = (Button) findViewById(R.id.btnsendmessage);
        btnaddfriend = (Button) findViewById(R.id.btnaddfriend);
    }
    private void queryother(){
        BmobQuery<User> queryuser = new BmobQuery<User>();
        queryuser.addWhereEqualTo("userid",otherid);
        queryuser.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e==null){
                    String tmp = "";
                    for(int i=0;i<list.size();i++){
                        tmp += list.get(i).getUsername();
                        tmp += "@";
                        tmp += list.get(i).getIntroduction();
                        tmp += "$";
                    }
                    handlersendmessage(tmp);
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
                case QUERYUSER:
                    bmobresult = (String) msg.obj;
                    if(querysuccess(bmobresult)==true){
                        ArrayList<User> tmplist = getuserlist(bmobresult);
                        userlist.clear();
                        for(int i=0;i<tmplist.size();i++){
                            userlist.add(tmplist.get(i));
                        }
                        if(userlist.size()>0){
                            otherusernametxt.setText(userlist.get(0).getUsername());
                            otherintrotxt.setText(userlist.get(0).getIntroduction());
                        }else{
                            Toast.makeText(OtherinforActivity.this,"无法获取该用户信息",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(OtherinforActivity.this,"无法获取该用户信息",Toast.LENGTH_LONG).show();
                    }
                    break;
                case ADDFRIEND:
                    bmobresult = (String) msg.obj;
                    if(sendrequestsuccess(bmobresult)==true){
                        flag = true;
                        sendnotification();
                    }else{
                        Toast.makeText(OtherinforActivity.this,"无法加对方为好友",Toast.LENGTH_LONG).show();
                    }
                    break;
                case INSERTNOTIFICATION:
                    bmobresult = (String) msg.obj;
                    if(sendnotifisuccess(bmobresult)){
                        if(flag==true){
                            Toast.makeText(OtherinforActivity.this,"好友请求发送成功，请等待对方回应",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(OtherinforActivity.this,"无法加对方为好友",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(OtherinforActivity.this,"无法加对方为好友",Toast.LENGTH_LONG).show();
                    }
                default:
                    break;
            }
        }
    };

    private void handlersendmessage(String str){
        Message msg = Message.obtain();
        msg.what = QUERYUSER;
        msg.obj = str;
        handler.sendMessage(msg);
    }

    private boolean querysuccess(String str){
        if(!str.equals("NULL")){
            return true;
        }
        return false;
    }

    private ArrayList<User> getuserlist(String str){
        ArrayList<User> tmplist = new ArrayList<>();
        User tmpuser = new User();
        String tmp = "";
        int state = 0;
        for(int i=0;i<str.length();i++){
            if(state==0){
                tmp += str.charAt(i);
                state = 1;
                continue;
            }
            if(state==1){
                if(str.charAt(i)=='@'){
                    state = 2;
                    tmpuser.setUsername(tmp);
                    tmp = "";
                    continue;
                }
                tmp += str.charAt(i);
            }
            if(state==2){
                if(str.charAt(i)=='$'){
                    state = -1;
                    tmpuser.setIntroduction(tmp);
                    tmp = "";
                }else{
                    tmp += str.charAt(i);
                }
            }
            if(state==-1){
                tmplist.add(tmpuser);
                state = 0;
                tmpuser = new User();
                continue;
            }
        }
        return tmplist;
    }

    private void sendfriendrequest(Long me, Long you){
        EditText myintro = new EditText(OtherinforActivity.this);
        AlertDialog.Builder dialog = new AlertDialog.Builder(OtherinforActivity.this);
        dialog.setTitle("加好友请求");
        dialog.setView(myintro);
        dialog.setCancelable(false);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myintrostr = myintro.getText().toString();
                Relationship rlp = new Relationship();
                rlp.setMyid(me);
                rlp.setYourid(you);
                rlp.setIsagreed(0);
                rlp.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null){
                            handlersendmessagefri("SENDREQUESTSUCCESS");
                        }else{
                            handlersendmessagefri("SENDREQUESTFAILED");
                        }
                    }
                });
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    private void handlersendmessagefri(String str){
        Message msg = Message.obtain();
        msg.what = ADDFRIEND;
        msg.obj = str;
        handler.sendMessage(msg);
    }

    private boolean sendrequestsuccess(String str){
        if(str.equals("SENDREQUESTSUCCESS")){
            return true;
        }
        return false;
    }

    private void handlersendmessagenoc(String str){
        Message msg = Message.obtain();
        msg.what = INSERTNOTIFICATION;
        msg.obj = str;
        handler.sendMessage(msg);
    }

    private void sendnotification(){
        Notification notification = new Notification();
        notification.setUserid(otherid);
        notification.setTitle("您有新朋友");
        notification.setContent("("+myapp.getCurrentuser().getUsername()+")想加您为好友，是否同意？\n"+myintrostr);
        notification.setIsread(0);
        notification.setRequestid(myapp.getCurrentuser().getUserid());
        notification.setTime((new Date()).toString());
        notification.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    handlersendmessagenoc("SENDNOCSUCCESS");
                }else{
                    handlersendmessagenoc("SENDNOCFAILED");
                }
            }
        });
    }

    private boolean sendnotifisuccess(String str){
        if(str.equals("SENDNOCSUCCESS")){
            return true;
        }
        return false;
    }
}