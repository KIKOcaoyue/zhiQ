package com.nuistcy.zhiq1_0.activity;

import androidx.appcompat.app.AppCompatActivity;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nuistcy.zhiq1_0.MainActivity;
import com.nuistcy.zhiq1_0.R;
import com.nuistcy.zhiq1_0.entity.User;
import com.nuistcy.zhiq1_0.nets.socketHandle;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private socketHandle sockethandle = null;
    private List<User> userlist = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnlogin = (Button) findViewById(R.id.btnlogin);
        Bmob.initialize(this,"b9194c9e79bfa98be319126e045f09d1");
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView editusername = (TextView) findViewById(R.id.editusername);
                TextView editpwd = (TextView) findViewById(R.id.editpwd);
                //String loginrequest = loginUtils.generateLoginRequest(editusername.getText().toString(),editpwd.getText().toString());
//                try{
//                    sockethandle.send(loginrequest);
//                }catch(Exception e) {
//                    Toast.makeText(LoginActivity.this,"发送失败",Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
//                }
                boolean canlogin = checklogin(editusername.getText().toString().trim(),editpwd.getText().toString().trim());
                if(canlogin==true){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }else{
                    Toast.makeText(LoginActivity.this,"无法登录",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean checklogin(String userid,String pwd){
        BmobQuery<User> userquery = new BmobQuery<User>();
        userquery.addWhereEqualTo("userid",userid);
        userquery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e==null){
                    LoginActivity.this.userlist = list;
                    Log.d("BMOB",LoginActivity.this.userlist.get(0).getUsername());
                }else{
                    Log.d("BMOB","NULL");
                }
            }
        });
        Log.d("BMOB",LoginActivity.this.userlist.get(0).getUsername());
        if(LoginActivity.this.userlist==null){
            return false;
        }else{
            if(LoginActivity.this.userlist.get(0).getUserpwd()==pwd){
                return true;
            }else{
                return false;
            }
        }
    }
}