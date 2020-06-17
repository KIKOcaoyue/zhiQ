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
import com.nuistcy.zhiq1_0.entity.Answer;
import com.nuistcy.zhiq1_0.entity.Topic;
import com.nuistcy.zhiq1_0.entity.User;
import com.nuistcy.zhiq1_0.status.MyApplication;
import com.nuistcy.zhiq1_0.ui.dashboard.DashboardFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ViewtopicActivity extends AppCompatActivity {

    private static final int QUERYNET = 5;
    private TextView topictxt;
    private TextView introtxt;
    private TextView answertxt;
    private TextView authoridtxt;
    private Button btnanswer;
    private Button btnnext;
    private String bmobresult;
    private String title;
    private String intro;
    private ArrayList<Answer> answerlist = new ArrayList<>();
    private int cur = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewtopic);
        initView();
        Bmob.initialize(this,"mykey");
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        intro = intent.getStringExtra("intro");
        topictxt.setText(title);
        introtxt.setText(intro);
        queryallanswer();
        answertxt.setText("点击(下一个回答)按钮查看回答)");
        btnanswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("title",title);
                intent.setClass(ViewtopicActivity.this,AnswerActivity.class);
                startActivity(intent);
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cur<answerlist.size()-1){
                    answertxt.setText(answerlist.get(++cur).getContent());
                    authoridtxt.setText(answerlist.get(cur).getAuthorid().toString());
                }else{
                    Toast.makeText(ViewtopicActivity.this,"这是最后一个回答了",Toast.LENGTH_LONG).show();
                }
            }
        });
        authoridtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.putExtra("authorid",Long.parseLong(authoridtxt.getText().toString()));
                intent1.setClass(ViewtopicActivity.this,OtherinforActivity.class);
                startActivity(intent1);
            }
        });
    }

    private void initView(){
        topictxt = (TextView) findViewById(R.id.topictxt);
        introtxt = (TextView) findViewById(R.id.introtxt);
        answertxt = (TextView) findViewById(R.id.answertxt);
        authoridtxt = (TextView) findViewById(R.id.authoridtxt);
        btnanswer = (Button) findViewById(R.id.btnanswer);
        btnnext = (Button) findViewById(R.id.btnnext);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case QUERYNET:
                    bmobresult = (String) msg.obj;
                    if(querysuccess(bmobresult)==true){
                        ArrayList<Answer> tmpans = getanswerlist(bmobresult);
                        Log.d("ANSWERTEST","先填充:"+tmpans.size());
                        answerlist.clear();
                        for(int i=0;i<tmpans.size();i++){
                            answerlist.add(tmpans.get(i));
                        }
                    }else{
                        Toast.makeText(ViewtopicActivity.this,"无法获取回答",Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void handlersendmessage(String str){
        Message msg = Message.obtain();
        msg.what = QUERYNET;
        msg.obj = str;
        handler.sendMessage(msg);
    }

    private boolean querysuccess(String str){
        if(!str.equals("NULL")) {
            return true;
        }
        return false;
    }

    private void queryallanswer(){
        BmobQuery<Answer> ansquery = new BmobQuery<>();
        ansquery.addWhereEqualTo("title",title);
        ansquery.findObjects(new FindListener<Answer>() {
            @Override
            public void done(List<Answer> list, BmobException e) {
                if(e==null){
                    String tmp = "";
                    for(int i=0;i<list.size();i++){
                        tmp += list.get(i).getAnswerid();
                        tmp += "!";
                        tmp += list.get(i).getAuthorid();
                        tmp += "@";
                        tmp += list.get(i).getTitle();
                        tmp += "#";
                        tmp += list.get(i).getContent();
                        tmp += "$";
                    }
                    handlersendmessage(tmp);
                }else{
                    handlersendmessage("NULL");
                }
            }
        });
    }

    private ArrayList<Answer> getanswerlist(String str){
        ArrayList<Answer> tmplist = new ArrayList<>();
        Answer tmpanswer = new Answer();
        String tmp = "";
        int state = 0;
        for(int i=0;i<str.length();i++){
            if(state==0){
                tmp += str.charAt(i);
                state = 1;
                continue;
            }
            if(state==1){
                if(str.charAt(i)=='!'){
                    state = 2;
                    tmpanswer.setAnswerid(Long.parseLong(tmp));
                    tmp = "";
                    continue;
                }
                tmp += str.charAt(i);
            }
            if(state==2){
                if(str.charAt(i)=='@'){
                    state = 3;
                    tmpanswer.setAuthorid(Long.parseLong(tmp));
                    tmp = "";
                    continue;
                }
                tmp += str.charAt(i);
            }
            if(state==3){
                if(str.charAt(i)=='#'){
                    state = 4;
                    tmpanswer.setTitle(tmp);
                    tmp = "";
                    continue;
                }
                tmp += str.charAt(i);
            }
            if(state==4){
                if(str.charAt(i)=='$'){
                    state = -1;
                    tmpanswer.setContent(tmp);
                    tmp = "";
                }else{
                    tmp += str.charAt(i);
                }
            }
            if(state==-1){
                tmplist.add(tmpanswer);
                state = 0;
                tmpanswer = new Answer();
                continue;
            }
        }
        return tmplist;
    }
}
