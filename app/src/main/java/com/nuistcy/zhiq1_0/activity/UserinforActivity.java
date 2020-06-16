package com.nuistcy.zhiq1_0.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nuistcy.zhiq1_0.MainActivity;
import com.nuistcy.zhiq1_0.R;
import com.nuistcy.zhiq1_0.status.MyApplication;

public class UserinforActivity extends AppCompatActivity {

    private static MyApplication myapp;
    private TextView usernametxt;
    private TextView introtxt;
    private Button btnok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfor);
        myapp = MyApplication.getmyapp();
        initView();
        usernametxt.setText(myapp.getCurrentuser().getUsername());
        introtxt.setText(myapp.getCurrentuser().getIntroduction());
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserinforActivity.this, MainActivity.class));
            }
        });
    }

    private void initView(){
        usernametxt = (TextView) findViewById(R.id.usernametxt);
        introtxt = (TextView) findViewById(R.id.introtxt);
        btnok = (Button) findViewById(R.id.btnok);
    }
}
