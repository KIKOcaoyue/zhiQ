package com.nuistcy.zhiq1_0.activity;

import androidx.appcompat.app.AppCompatActivity;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nuistcy.zhiq1_0.MainActivity;
import com.nuistcy.zhiq1_0.R;
import com.nuistcy.zhiq1_0.entity.Answer;
import com.nuistcy.zhiq1_0.entity.User;
import com.nuistcy.zhiq1_0.status.MyApplication;

public class AnswerActivity extends AppCompatActivity {
    private static final int INSERTANSWER = 6;
    private EditText edittxt;
    private Button btnsendanswer;
    private MyApplication myapp;
    private String title;
    private String bmobresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        myapp = MyApplication.getmyapp();
        Bmob.initialize(this,myapp.getBmobkey());
        initView();
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        btnsendanswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendanswer(edittxt.getText().toString());
            }
        });
    }

    private void initView(){
        edittxt = (EditText) findViewById(R.id.edittxt);
        btnsendanswer = (Button) findViewById(R.id.btnsendanswer);
    }

    private void sendanswer(String str){
        Answer answer = new Answer();
        answer.setAuthorid(myapp.getCurrentuser().getUserid());
        answer.setContent(edittxt.getText().toString());
        answer.setTitle(title);
        answer.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    handlersendmessage("SENDSUCCESS");
                }else{
                    handlersendmessage("SENDFAILED");
                }
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case INSERTANSWER:
                    bmobresult = (String) msg.obj;
                    if(sendanssuccess(bmobresult)==true){
                        Intent intent = new Intent();
                        intent.setClass(AnswerActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(AnswerActivity.this,"无法回答问题",Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void handlersendmessage(String str){
        Message msg = Message.obtain();
        msg.what = INSERTANSWER;
        msg.obj = str;
        handler.sendMessage(msg);
    }

    private boolean sendanssuccess(String str){
        if(str.equals("SENDSUCCESS")){
            return true;
        }
        return false;
    }
}
