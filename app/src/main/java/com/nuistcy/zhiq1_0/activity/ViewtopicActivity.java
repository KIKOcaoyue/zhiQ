package com.nuistcy.zhiq1_0.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.nuistcy.zhiq1_0.R;
import com.nuistcy.zhiq1_0.ui.dashboard.DashboardFragment;

public class ViewtopicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewtopic);
        Button btnanswer = (Button) findViewById(R.id.btnanswer);
        btnanswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewtopicActivity.this,AnswerActivity.class));
            }
        });
    }

}
