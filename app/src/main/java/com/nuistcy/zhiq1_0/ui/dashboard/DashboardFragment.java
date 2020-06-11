package com.nuistcy.zhiq1_0.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.nuistcy.zhiq1_0.R;

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

    @Override
    public void onStart(){
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
        SimpleAdapter adapter = new SimpleAdapter(getActivity(),data,R.layout.worlditem,from,to);
        worldlist.setAdapter(adapter);
        return root;
    }
    public void initView(){
        Map<String,String> item = new HashMap<>();
        item.put("title","Android开发到底应该怎么学？");
        item.put("intro","Android开发是一个很困难的事情，作为一名大学生，到底应当怎么学习呢？请大家畅所欲言。");
        item.put("hot","热度：5902");
        data.add(item);
    }
}
