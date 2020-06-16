package com.nuistcy.zhiq1_0.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.nuistcy.zhiq1_0.R;
import com.nuistcy.zhiq1_0.activity.LaunchtopicActivity;
import com.nuistcy.zhiq1_0.activity.UserinforActivity;
import com.nuistcy.zhiq1_0.activity.ViewtopicActivity;
import com.nuistcy.zhiq1_0.status.MyApplication;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private MyApplication myapp;
    private TextView usernametxt3;
    private TextView introductiontxt3;
    private Button btnlaunch;
    private Button btninfor;

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
}
