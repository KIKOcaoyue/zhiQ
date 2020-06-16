package com.nuistcy.zhiq1_0.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nuistcy.zhiq1_0.MainActivity;
import com.nuistcy.zhiq1_0.R;

public class RegistersuccessActivity extends AppCompatActivity {

    private String username;
    private Long userid;
    private TextView newnametxt;
    private TextView newidtxt;
    private Button btncomfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registersuccess);
        initView();
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        userid = intent.getLongExtra("userid",0);
        newnametxt.setText(username+",您好! 欢迎来到zhiQ, 您的登录账号如下，请妥善保管。");
        newidtxt.setText(userid.toString());
        btncomfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newintent = new Intent();
                newintent.setClass(RegistersuccessActivity.this, LoginActivity.class);
                startActivity(newintent);
            }
        });
    }

    private void initView(){
        newnametxt = (TextView) findViewById(R.id.newnametxt);
        newidtxt = (TextView) findViewById(R.id.newidtxt);
        btncomfirm = (Button) findViewById(R.id.btnconfirm);
    }
}