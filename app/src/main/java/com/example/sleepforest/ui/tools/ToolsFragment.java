package com.example.sleepforest.ui.tools;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.sleepforest.R;
import com.example.sleepforest.Time;

import java.util.List;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    public static ToolsFragment newInstance(){
        return new ToolsFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tools, container, false);
    }
    public void onActivityCreated(@NonNull Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
      //  View root = inflater.inflate(R.layout.fragment_tools, container, false);
        final TextView textView = getView().findViewById(R.id.text_tools);
        final TextView tvw=(TextView)getView().findViewById(R.id.textView1);
        final TimePicker picker = getView().findViewById(R.id.timePicker1);
        picker.setIs24HourView(true);
        Button ok = getView().findViewById(R.id.settingOK);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour, minute;
                String am_pm;
                if (Build.VERSION.SDK_INT >= 23 ){
                    hour = picker.getHour();
                    minute = picker.getMinute();
                    toolsViewModel.deleteTime();
                    toolsViewModel.insertTime(new Time(1, hour, minute));
                }
                else{
                    hour = picker.getCurrentHour();
                    minute = picker.getCurrentMinute();
                }
                if(hour > 12) {
                    am_pm = "PM";
                    hour = hour - 12;
                }
                else
                {
                    am_pm="AM";
                }
                //tvw.setText("Selected Date: "+ hour +":"+ minute+" "+am_pm);

            }

        });
        toolsViewModel.getAlltimes().observe(this, new Observer<List<Time>>() {
            @Override
            public void onChanged(List<Time> times) {
                textView.setText(times.get(0).toString());
            }
        });
        toolsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        //return root;
    }
}