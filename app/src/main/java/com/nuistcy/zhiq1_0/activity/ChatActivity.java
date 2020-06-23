package com.nuistcy.zhiq1_0.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.nuistcy.zhiq1_0.R;

public class ChatActivity extends AppCompatActivity {

    private ImageButton imgyour;
    private ImageButton imgmy;
    private ListView chatlist;
    private EditText editmesstxt;
    private Button btnsendmessage;
    private String usrname;
    private String usrintro;
    private Long userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        imgyour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.setClass(ChatActivity.this,FriendinforActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView(){
        imgyour = (ImageButton) findViewById(R.id.imgyour);
        imgmy = (ImageButton) findViewById(R.id.imgmy);
        chatlist = (ListView) findViewById(R.id.chatlist);
        editmesstxt = (EditText) findViewById(R.id.editmesstxt);
        btnsendmessage = (Button) findViewById(R.id.btnsendmessage);
    }
}