package com.nuistcy.zhiq1_0.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import cn.bmob.v3.Bmob;

import com.nuistcy.zhiq1_0.R;
import com.nuistcy.zhiq1_0.activity.ViewtopicActivity;
import com.nuistcy.zhiq1_0.status.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private ListView worldlist;
    private ArrayList<Map<String,String>> data = new ArrayList<>();
    private String[] from = {"title","intro","hot"};
    private int[] to = {R.id.titletxt,R.id.introducetxt,R.id.hottxt};
    private SimpleAdapter adapter;
    private MyApplication myapp;

    @Override
    public void onStart(){
        myapp = MyApplication.getmyapp();
        initView();
        super.onStart();
    }



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        //final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                //textView.setText(s);
//            }
//        });
//        return root;
        worldlist = (ListView) root.findViewById(R.id.worldlist);
        adapter = new SimpleAdapter(getActivity(),data,R.layout.worlditem,from,to);
        worldlist.setAdapter(adapter);
        return root;
    }
    public void initView(){
        data.clear();
        for(int i=0;i<myapp.getTopiclist().size();i++) {
            Map<String, String> item = new HashMap<>();
            item.put("title", myapp.getTopiclist().get(i).getTitle());
            item.put("intro", myapp.getTopiclist().get(i).getIntroduction());
            item.put("hot", myapp.getTopiclist().get(i).getHot()+"热度    "+myapp.getTopiclist().get(i).getThumbsups()+"个赞");
            data.add(item);
        }
        adapter.notifyDataSetChanged();
        worldlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String,String> map=(HashMap<String, String>) worldlist.getItemAtPosition(i);
                String title = map.get("title");
                String intro = map.get("intro");
                Intent intent = new Intent();
                intent.putExtra("title",title);
                intent.putExtra("intro",intro);
                intent.setClass(getActivity(),ViewtopicActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
}
