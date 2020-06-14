package com.nuistcy.zhiq1_0.nets;

import android.os.AsyncTask;
import android.os.Message;
import android.widget.Toast;

import com.nuistcy.zhiq1_0.activity.LoginActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class socketHandle {
    public socketHandle(){ }
    private Socket socket;
    private BufferedReader bufferedreader;
    private BufferedWriter bufferedwriter;

    public void connect(){
        AsyncTask<Void,String,Void> read = new AsyncTask<Void, String, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    socket = new Socket("172.20.10.3",19605);
                    bufferedwriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));
                    bufferedreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line;
                    while((line=bufferedreader.readLine())!=null){
                        publishProgress(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
            }
        };
        read.execute();
    }

    public void send(String str){
        try {
            //if(socket==null) return;
            bufferedwriter.write(str+"\n");
            bufferedwriter.flush();
        } catch (IOException e) {
             e.printStackTrace();
        }
    }
}
