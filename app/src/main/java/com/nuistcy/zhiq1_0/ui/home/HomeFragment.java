package com.nuistcy.zhiq1_0.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.nuistcy.zhiq1_0.MainActivity;
import com.nuistcy.zhiq1_0.R;
import com.nuistcy.zhiq1_0.activity.ChatActivity;
import com.nuistcy.zhiq1_0.activity.ViewtopicActivity;
import com.nuistcy.zhiq1_0.status.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private List<Map<String,String>> data = new ArrayList<>();
    private String[] from = {"name","message","userid"};
    private int[] to = {R.id.nametxt,R.id.messagetxt,R.id.useridtxt};
    private ListView messagelist;
    private HomeViewModel homeViewModel;
    private SimpleAdapter adapter;
    private MyApplication myapp;
    private Map<String,Long> mp = new HashMap<>();

    @Override
    public void onStart(){
        myapp = MyApplication.getmyapp();
        initData();
        super.onStart();
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        //final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                initView();
//            }
//        });
//        return root;
        messagelist = (ListView) root.findViewById(R.id.messagelist);
        adapter = new SimpleAdapter(getActivity(),data,R.layout.messagelistitem,from,to);
        messagelist.setAdapter(adapter);
        return root;
    }

    public void initData(){
        data.clear();
        mp.clear();
        if(myapp.getFriendlist()!=null){
            for(int i=0;i<myapp.getFriendlist().size();i++) {
                mp.put(myapp.getFriendlist().get(i).getMyid().toString() + myapp.getFriendlist().get(i).getYourid(), 1L);
                Log.d("HOMETEST",myapp.getFriendlist().get(i).getMyid().toString() + myapp.getFriendlist().get(i).getYourid());
            }
            for(int i=0;i<myapp.getFriendlist().size();i++){
                Log.d("HOMETEST","我的:"+myapp.getFriendlist().get(i).getMyid()+","+"还是我的:"+myapp.getCurrentuser().getUserid());
                if(myapp.getFriendlist().get(i).getMyid().equals(myapp.getCurrentuser().getUserid())){
                    if(mp.containsKey(myapp.getFriendlist().get(i).getYourid().toString() + myapp.getFriendlist().get(i).getMyid())){
                        Log.d("HOMETEST","成功找到了");
                        Map<String,String> item = new HashMap<>();
                        String username = getusernamebyid(myapp.getFriendlist().get(i).getYourid());
                        String userintro = getuserintrobyid(myapp.getFriendlist().get(i).getYourid());
                        item.put("name",username);
                        item.put("message",userintro);
                        item.put("userid",myapp.getFriendlist().get(i).getYourid().toString());
                        Log.d("HOMETEST","我的好友有:"+username);
                        data.add(item);
                    }
                }
            }
        }

//        for(int i=0;i<myapp.getTopiclist().size();i++) {
//            Map<String, String> item = new HashMap<>();
//            item.put("title", myapp.getTopiclist().get(i).getTitle());
//            item.put("intro", myapp.getTopiclist().get(i).getIntroduction());
//            item.put("hot", myapp.getTopiclist().get(i).getHot()+"热度    "+myapp.getTopiclist().get(i).getThumbsups()+"个赞");
//            data.add(item);
//        }
        adapter.notifyDataSetChanged();
        messagelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String,String> map=(HashMap<String, String>) messagelist.getItemAtPosition(i);
                String name = map.get("name");
                String message = map.get("message");
                String userid = map.get("userid");
                Intent intent = new Intent();
                intent.putExtra("name",name);
                intent.putExtra("message",message);
                intent.putExtra("userid",userid);
                intent.setClass(getActivity(), ChatActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
    private String getusernamebyid(Long userid){
        for(int i=0;i<myapp.getAlluserlist().size();i++){
            if(myapp.getAlluserlist().get(i).getUserid().equals(userid)){
                return myapp.getAlluserlist().get(i).getUsername();
            }
        }
        return "NULL";
    }

    private String getuserintrobyid(Long userid){
        for(int i=0;i<myapp.getAlluserlist().size();i++){
            if(myapp.getAlluserlist().get(i).getUserid().equals(userid)){
                return myapp.getAlluserlist().get(i).getIntroduction();
            }
        }
        return "NULL";
    }
}
