package com.nuistcy.zhiq1_0.activity;

import androidx.appcompat.app.AppCompatActivity;
import cn.bmob.v3.Bmob;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nuistcy.zhiq1_0.R;
import com.nuistcy.zhiq1_0.status.MyApplication;

public class FriendinforActivity extends AppCompatActivity {

    private TextView friendnametxt;
    private TextView friendintrotxt;
    private TextView frienddaytxt;
    private Button btndeletefriend;
    private String usrname;
    private Long usrid;
    private String usrintro;
    private MyApplication myapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendinfor);
        myapp = MyApplication.getmyapp();
        initView();
        Intent intent = getIntent();
        usrname = intent.getStringExtra("name");
        usrintro = intent.getStringExtra("message");
        usrid = Long.parseLong(intent.getStringExtra("userid"));
        Bmob.initialize(this,myapp.getBmobkey());
        friendnametxt.setText(usrname);
        friendintrotxt.setText(usrintro);
        btndeletefriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initView(){
        friendnametxt = (TextView) findViewById(R.id.friendnametxt);
        friendintrotxt = (TextView) findViewById(R.id.friendintrotxt);
        frienddaytxt = (TextView) findViewById(R.id.frienddaytxt);
        btndeletefriend = (Button) findViewById(R.id.btndeletefriend);
    }
}