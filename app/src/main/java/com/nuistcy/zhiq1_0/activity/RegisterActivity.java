package com.nuistcy.zhiq1_0.activity;

import androidx.appcompat.app.AppCompatActivity;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.util.V;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nuistcy.zhiq1_0.MainActivity;
import com.nuistcy.zhiq1_0.R;
import com.nuistcy.zhiq1_0.entity.User;
import com.nuistcy.zhiq1_0.utils.registerUtils;

public class RegisterActivity extends AppCompatActivity {
    private static final int REGISTERREQUEST = 2;
    private EditText editregusername;
    private EditText editregpwd;
    private EditText editregpwdc;
    private Button btnregregister;
    private String bmobresult;
    private User registeruser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initBmob();
        btnregregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editregpwd.getText().toString().trim().equals(editregpwdc.getText().toString().trim())){
                    registeruser = doregister(editregusername.getText().toString().trim(),editregpwd.getText().toString().trim());

                }else{
                    Toast.makeText(RegisterActivity.this,"无法注册",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initView(){
        editregusername = (EditText) findViewById(R.id.editregusername);
        editregpwd = (EditText) findViewById(R.id.editregpwd);
        editregpwdc = (EditText) findViewById(R.id.editregpwdc);
        btnregregister = (Button) findViewById(R.id.btnregregister);
    }

    private void initBmob(){
        Bmob.initialize(this,"mykey");
    }

    private User doregister(String username, String pwd){
        User registeruser = new User();
        registeruser.setUsername(username);
        registeruser.setUserpwd(pwd);
        registeruser.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    handlersendmessage("REGISTERSUCCESS");
                }else{
                    handlersendmessage("REGISTERFAILED");
                }
            }
        });
        return registeruser;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case REGISTERREQUEST:
                    bmobresult = (String) msg.obj;
                    if(registersuccess(bmobresult)==true){
                        Log.d("REGISTERBMOB",bmobresult);
                        if(registeruser!=null){
                            Intent intent = new Intent();
                            intent.putExtra("username",registeruser.getUsername());
                            intent.putExtra("userid",registeruser.getUserid());
                            intent.setClass(RegisterActivity.this, RegistersuccessActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(RegisterActivity.this,"无法注册",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this,"无法注册",Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void handlersendmessage(String str){
        Message msg = Message.obtain();
        msg.what = REGISTERREQUEST;
        msg.obj = str;
        handler.sendMessage(msg);
    }

    private boolean registersuccess(String str){
        if(str=="REGISTERSUCCESS"){
            return true;
        }
        return false;
    }
}