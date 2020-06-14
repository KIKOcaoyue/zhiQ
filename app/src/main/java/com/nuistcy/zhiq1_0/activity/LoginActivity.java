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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nuistcy.zhiq1_0.MainActivity;
import com.nuistcy.zhiq1_0.R;
import com.nuistcy.zhiq1_0.entity.User;
import com.nuistcy.zhiq1_0.nets.socketHandle;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final int QUERYDATABASE = 1;
    private String bmobresult;
    private static TextView editusername;
    private static TextView editpwd;
    private static Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        Bmob.initialize(this,"mykey");
        editusername = (TextView) findViewById(R.id.editusername);
        editpwd = (TextView) findViewById(R.id.editpwd);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checklogin(editusername.getText().toString().trim(),editpwd.getText().toString().trim());
            }
        });
    }

    private void checklogin(String userid,String pwd){
        BmobQuery<User> userquery = new BmobQuery<User>();
        userquery.addWhereEqualTo("userid",userid);
        userquery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e==null){
                    Message msg = Message.obtain();
                    msg.what = QUERYDATABASE;
                    msg.obj = list.get(0).getUserpwd()+"#"+pwd; //a#b
                    handler.sendMessage(msg);
                }else{
                    Message msg = Message.obtain();
                    msg.what = QUERYDATABASE;
                    msg.obj = "NULL"; //a#b
                    handler.sendMessage(msg);
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
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
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
}