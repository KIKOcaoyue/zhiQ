package com.nuistcy.zhiq1_0.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private List<Map<String,String>> data = new ArrayList<>();
    private String[] from = {"name","message","time"};
    private int[] to = {R.id.nametxt,R.id.messagetxt,R.id.timetxt};
    private ListView messagelist;
    private HomeViewModel homeViewModel;
    private SimpleAdapter adapter;

    @Override
    public void onStart(){
        initView();
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

    private void initView(){
        data.clear();
        Map<String,String> item = new HashMap<>();
        Map<String,String> item2 = new HashMap<>();
        item.put("name","cy");
        item.put("message","hello");
        item.put("time","7:42PM");
        data.add(item);
        item2.put("name","tq");
        item2.put("message","world");
        item2.put("time","8:35PM");
        data.add(item2);
        adapter.notifyDataSetChanged();
    }
}
