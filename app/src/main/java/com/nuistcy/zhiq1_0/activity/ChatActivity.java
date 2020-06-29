package com.nuistcy.zhiq1_0.activity;

import androidx.appcompat.app.AppCompatActivity;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.util.V;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.nuistcy.zhiq1_0.R;
import com.nuistcy.zhiq1_0.entity.Message;
import com.nuistcy.zhiq1_0.entity.Notification;
import com.nuistcy.zhiq1_0.status.MyApplication;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static android.os.SystemClock.sleep;

public class ChatActivity extends AppCompatActivity {

    private Button btnfriendinfro;
    private Button btnreturn;
    private ListView chatlist;
    private EditText editmesstxt;
    private Button btnsendmessage;
    private String usrname;
    private String usrintro;
    private Long userid;
    private MyApplication myapp;
    private ArrayList<Map<String,String>> data = new ArrayList<>();
    private String[] from = {"usrnme1","messtime1","message1","usrnme2","messtime2","message2"};
    private int[] to = {R.id.usrnmetxt1,R.id.messtimetxt1,R.id.messagetxt1,R.id.usrnmetxt2,R.id.messtimetxt2,R.id.messagetxt2};
    private SimpleAdapter adapter;
    private TextView nametext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        Intent intent = getIntent();
        userid = Long.parseLong(intent.getStringExtra("userid"));
        usrname = intent.getStringExtra("name");
        myapp = MyApplication.getmyapp();
        chatlist = (ListView) findViewById(R.id.chatlist);
        adapter = new SimpleAdapter(ChatActivity.this,data,R.layout.chatlistitem,from,to);
        chatlist.setAdapter(adapter);
        timer.schedule(task,0,1500);
        nametext.setText(usrname);
        btnsendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendmessage(myapp.getCurrentuser().getUserid(),userid,editmesstxt.getText().toString());
                editmesstxt.setText("");
            }
        });
        btnfriendinfro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = getIntent();
                intent1.setClass(ChatActivity.this,FriendinforActivity.class);
                startActivity(intent1);
            }
        });
        btnreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    private void initView(){
        nametext = (TextView) findViewById(R.id.nametext);
        btnfriendinfro = (Button) findViewById(R.id.frienginfo);
        btnreturn = (Button) findViewById(R.id.back);
        editmesstxt = (EditText) findViewById(R.id.editmesstxt);
        btnsendmessage = (Button) findViewById(R.id.btnsendmessage);
    }



    private void freshdata(){
        adapter.notifyDataSetChanged();
    }

    private void sendmessage(Long sender,Long receiver,String content){
        Message message = new Message();
        message.setSenderid(sender);
        message.setReceiverid(receiver);
        message.setContent(content);
        message.setTime((new Date()).toString());
        message.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
            }
        });
    }

    private void getdata(){
        BmobQuery<Message> msg11 = new BmobQuery<>();
        msg11.addWhereEqualTo("senderid",myapp.getCurrentuser().getUserid());
        BmobQuery<Message> msg12 = new BmobQuery<>();
        msg12.addWhereEqualTo("receiverid",userid);
        BmobQuery<Message> msg21 = new BmobQuery<>();
        msg21.addWhereEqualTo("senderid",userid);
        BmobQuery<Message> msg22 = new BmobQuery<>();
        msg22.addWhereEqualTo("receiverid",myapp.getCurrentuser().getUserid());
        List<BmobQuery<Message>> andquery1 = new ArrayList<>();
        andquery1.add(msg11);
        andquery1.add(msg12);
        List<BmobQuery<Message>> andquery2 = new ArrayList<>();
        andquery2.add(msg21);
        andquery2.add(msg22);
        BmobQuery<Message> mainquery1 = new BmobQuery<>();
        BmobQuery<Message> and1 = mainquery1.and(andquery1);
        BmobQuery<Message> mainquery2 = new BmobQuery<>();
        BmobQuery<Message> and2 = mainquery2.and(andquery2);
        List<BmobQuery<Message>> orquery = new ArrayList<>();
        orquery.add(and1);
        orquery.add(and2);
        BmobQuery<Message> query = new BmobQuery<>();
        query.or(orquery);
        query.order("createdAt");
        query.findObjects(new FindListener<Message>() {
            @Override
            public void done(List<Message> list, BmobException e) {
                if(e==null){
                    data.clear();
                    for(int i=0;i<list.size();i++){
                        Map<String, String> item = new HashMap<>();
                        if(list.get(i).getSenderid().equals(myapp.getCurrentuser().getUserid())){
                            item.put("usrnme2",getusernamebyid(list.get(i).getSenderid()));
                            item.put("messtime2",list.get(i).getTime());
                            item.put("message2",list.get(i).getContent());
                            item.put("usrnme1","");
                            item.put("messtime1","");
                            item.put("message1","");
                        }else{
                            item.put("usrnme1",getusernamebyid(list.get(i).getSenderid()));
                            item.put("messtime1",list.get(i).getTime());
                            item.put("message1",list.get(i).getContent());
                            item.put("usrnme2","");
                            item.put("messtime2","");
                            item.put("message2","");
                        }
                        data.add(item);
                    }
                    freshdata();
                }else{

                }
            }
        });
    }

    private String getusernamebyid(Long id){
        for(int i=0;i<myapp.getAlluserlist().size();i++){
            if(myapp.getAlluserlist().get(i).getUserid().equals(id)){
                return myapp.getAlluserlist().get(i).getUsername();
            }
        }
        return "NULL";
    }

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            try {
                getdata();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}