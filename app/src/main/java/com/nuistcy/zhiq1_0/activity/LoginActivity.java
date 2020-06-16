package com.nuistcy.zhiq1_0.activity;

import androidx.appcompat.app.AppCompatActivity;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nuistcy.zhiq1_0.MainActivity;
import com.nuistcy.zhiq1_0.R;
import com.nuistcy.zhiq1_0.entity.User;
import com.nuistcy.zhiq1_0.status.MyApplication;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final int QUERYDATABASE = 1;
    private String bmobresult;
    private static TextView editusername;
    private static TextView editpwd;
    private static Button btnlogin;
    private static Button btnregister;
    private MyApplication myapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        btnregister = (Button) findViewById(R.id.btnregregister);
        Bmob.initialize(this,"mykey");
        editusername = (TextView) findViewById(R.id.editusername);
        editpwd = (TextView) findViewById(R.id.editpwd);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checklogin(Long.parseLong(editusername.getText().toString().trim()),editpwd.getText().toString().trim());
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }

    private void checklogin(Long userid,String pwd){
        BmobQuery<User> userquery = new BmobQuery<User>();
        userquery.addWhereEqualTo("userid",userid);
        userquery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e==null){
                    handlersendmessage( list.get(0).getUserpwd()+"#"+pwd+"%"+list.get(0).getUserid()+'$'+list.get(0).getUsername()+'&'+list.get(0).getIntroduction());
                }else{
                    handlersendmessage( "NULL");
                }
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case QUERYDATABASE:
                    bmobresult = (String) msg.obj;
                    if(loginsuccess(bmobresult)==true){
                        Long userid = getuserid(bmobresult);
                        String username = getusername(bmobresult);
                        String userpwd = getuserpwd(bmobresult);
                        String userintro = getuserintro(bmobresult);
                        myapp = MyApplication.getmyapp();
                        myapp.setCurrentuser(new User(username,userid,userpwd,userintro));
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this,"无法登录",Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private boolean loginsuccess(String request){
        String correctpwd = "";
        String inputpwd = "";
        boolean found = false;
        for(int i=0;i<request.length();i++){
            if(request.charAt(i)=='%'){
                break;
            }
            if(request.charAt(i)=='#'){
                found = true;
                continue;
            }
            if(!found) {
                correctpwd += request.charAt(i);
            }
            if(found){
                inputpwd += request.charAt(i);
            }
        }
        if(correctpwd.equals(inputpwd)){
            return true;
        }else{
            return false;
        }
    }

    private void handlersendmessage(String str){
        Message msg = Message.obtain();
        msg.what = QUERYDATABASE;
        msg.obj = str; //a#b
        handler.sendMessage(msg);
    }

    private Long getuserid(String str){
        String ans = "";
        boolean found = false;
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)=='$'){
                break;
            }
            if(str.charAt(i)=='%'){
                found = true;
                continue;
            }
            if(found){
                ans += str.charAt(i);
            }
        }
        return Long.parseLong(ans);
    }

    private String getusername(String str){
        String ans = "";
        boolean found = false;
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)=='&'){
                break;
            }
            if(str.charAt(i)=='$'){
                found = true;
                continue;
            }
            if(found){
                ans += str.charAt(i);
            }
        }
        return ans;
    }

    private String getuserpwd(String str){
        String ans = "";
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)=='#'){
                break;
            }
           ans += str.charAt(i);
        }
        return ans;
    }

    private String getuserintro(String str){
        String ans = "";
        boolean found = false;
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)=='&'){
                found = true;
                continue;
            }
            if(found){
                ans += str.charAt(i);
            }
        }
        return ans;
    }
}