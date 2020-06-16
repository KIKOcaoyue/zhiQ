package com.nuistcy.zhiq1_0.activity;

import androidx.appcompat.app.AppCompatActivity;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

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
import com.nuistcy.zhiq1_0.entity.Topic;
import com.nuistcy.zhiq1_0.status.MyApplication;

public class LaunchtopicActivity extends AppCompatActivity {

    private static final int INSERTTOPIC = 3;
    private MyApplication myapp;
    private EditText edittitletxt;
    private EditText editintrotxt;
    private Button btnlaunchtopic;
    private String bmobresult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launchtopic);
        myapp = MyApplication.getmyapp();
        Bmob.initialize(this,myapp.getBmobkey());
        initView();
        btnlaunchtopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchtopic();
            }
        });
    }
    private void initView(){
        edittitletxt = (EditText) findViewById(R.id.edittitletxt);
        editintrotxt = (EditText) findViewById(R.id.editintrotxt);
        btnlaunchtopic = (Button) findViewById(R.id.btnlaunchtopic);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case INSERTTOPIC:
                    bmobresult = (String) msg.obj;
                    if(launchsuccess(bmobresult)==true){
                        Toast.makeText(LaunchtopicActivity.this,"发布成功",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.setClass(LaunchtopicActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LaunchtopicActivity.this,"无法发布问题",Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void handlersendmessage(String str){
        Message msg = Message.obtain();
        msg.what = INSERTTOPIC;
        msg.obj = str;
        handler.sendMessage(msg);
    }

    private boolean launchsuccess(String str){
        if(str.equals("LAUNCHSUCCESS")){
            return true;
        }
        return false;
    }

    private void launchtopic() {
        Topic topic = new Topic();
        topic.setHot("0");
        topic.setThumbsups("0");
        topic.setIntroduction(editintrotxt.getText().toString().trim());
        topic.setTitle(edittitletxt.getText().toString().trim());
        topic.setAskerid(myapp.getCurrentuser().getUserid());
        topic.setAskername(myapp.getCurrentuser().getUsername());
        //topic.setTopicid("0000");                                                                   //TODO
        topic.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    handlersendmessage("LAUNCHSUCCESS");
                }else{
                    handlersendmessage("LAUNCHFAILED");
                }
            }
        });
    }
}
