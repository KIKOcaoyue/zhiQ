package com.nuistcy.zhiq1_0.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.nuistcy.zhiq1_0.R;
import com.nuistcy.zhiq1_0.activity.LaunchtopicActivity;
import com.nuistcy.zhiq1_0.activity.UserinforActivity;
import com.nuistcy.zhiq1_0.activity.ViewNotificationActivity;
import com.nuistcy.zhiq1_0.activity.ViewtopicActivity;
import com.nuistcy.zhiq1_0.status.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private MyApplication myapp;
    private TextView usernametxt3;
    private TextView introductiontxt3;
    private Button btnlaunch;
    private Button btninfor;
    private ListView notificationlist;
    private SimpleAdapter adapter;
    private List<Map<String,String>> data = new ArrayList<>();
    private String[] from = {"notifitxt","notifitimetxt","notificationidtxt","noticontent","requestidtxt"};
    private int[] to = {R.id.notifitxt,R.id.notifitimetxt,R.id.notificationidtxt,R.id.noticontent,R.id.requestidtxt};

    @Override
    public void onStart(){
        myapp = MyApplication.getmyapp();
        initData();
        super.onStart();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        notificationsViewModel =
//                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
//        //final TextView textView = root.findViewById(R.id.text_notifications);
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                //textView.setText(s);
//            }
//        });
//        return root;
        notificationlist = (ListView) root.findViewById(R.id.notificationlist);
        adapter = new SimpleAdapter(getActivity(),data,R.layout.notificationitem,from,to);
        notificationlist.setAdapter(adapter);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        btnlaunch = (Button) getActivity().findViewById(R.id.btnlaunch);
        btninfor = (Button) getActivity().findViewById(R.id.btninfor);
        usernametxt3 = (TextView) getActivity().findViewById(R.id.usernametxt3);
        introductiontxt3 = (TextView) getActivity().findViewById(R.id.introductiontxt3);
        myapp = MyApplication.getmyapp();
        usernametxt3.setText(myapp.getCurrentuser().getUsername());
        if(!myapp.getCurrentuser().getIntroduction().equals("")){
            introductiontxt3.setText(myapp.getCurrentuser().getIntroduction());
        }else{
            introductiontxt3.setText("还没有简介...");
        }
        btnlaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LaunchtopicActivity.class));
            }
        });
        btninfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserinforActivity.class));
            }
        });
    }

    private void initData(){
        data.clear();
        if(myapp.getNotificationlist()!=null){
            for(int i=0;i<myapp.getNotificationlist().size();i++) {
                Map<String, String> item = new HashMap<>();
                item.put("notifitxt", myapp.getNotificationlist().get(i).getTitle());
                item.put("notifitimetxt", myapp.getNotificationlist().get(i).getTime());
                item.put("notificationidtxt", myapp.getNotificationlist().get(i).getNotificationid());
                item.put("noticontent",myapp.getNotificationlist().get(i).getContent());
                item.put("requestidtxt",myapp.getNotificationlist().get(i).getRequestid().toString());
                data.add(item);
            }
        }
        adapter.notifyDataSetChanged();
        notificationlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String,String> map=(HashMap<String, String>) notificationlist.getItemAtPosition(i);
                String title = map.get("notifitxt");
                String time = map.get("notifitimetxt");
                String notifiid = map.get("notificationidtxt");
                String content = map.get("noticontent");
                String requestid = map.get("requestidtxt");
                Intent intent = new Intent();
                intent.putExtra("notifitxt",title);
                intent.putExtra("notifitimetxt",time);
                intent.putExtra("notificationidtxt",notifiid);
                intent.putExtra("noticontent",content);
                intent.putExtra("requestidtxt",requestid);
                intent.setClass(getActivity(), ViewNotificationActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
}
