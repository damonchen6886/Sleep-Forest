package com.example.sleepforest.ui.tools;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sleepforest.AlarmReceiverActivity;
import com.example.sleepforest.R;
import com.example.sleepforest.Time;

import java.util.Calendar;
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
        final TimePicker picker = getView().findViewById(R.id.timePicker1);
        picker.setIs24HourView(true);
//        picker.setHour(toolsViewM);

        final AlarmManager alarmManager = (AlarmManager) getActivity()
                .getSystemService(Context.ALARM_SERVICE);
        final Intent intent = new Intent(getActivity(), AlarmReceiverActivity.class);
        final PendingIntent pendint = PendingIntent.getBroadcast(getActivity(),
                2, intent, 0);
        int hour, minute;
        if (Build.VERSION.SDK_INT >= 23 ){
            hour = picker.getHour();
            minute = picker.getMinute();

        }
        else{
            hour = picker.getCurrentHour();
            minute = picker.getCurrentMinute();
        }
//        if (setHour != 0&& setMin != 0){
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, minute);
            cal.set(Calendar.SECOND, 00);
            //Intent intent = new Intent(getActivity(), AlarmReceiverActivity.class);
            //PendingIntent pendint = PendingIntent.getBroadcast(getActivity(),
            //        2, intent, 0);

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    cal.getTimeInMillis(), 1000 * 60 * 60 * 24, pendint);

            Toast.makeText(getActivity(), "Alarm Set", Toast.LENGTH_SHORT).show();

        //}
        Button ok = getView().findViewById(R.id.settingOK);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int hour, minute;
                if (Build.VERSION.SDK_INT >= 23 ){
                    hour = picker.getHour();
                    minute = picker.getMinute();

                }
                else{
                    hour = picker.getCurrentHour();
                    minute = picker.getCurrentMinute();
                }
                toolsViewModel.deleteTime();
                toolsViewModel.insertTime(new Time(1, hour, minute));

                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                cal.set(Calendar.HOUR_OF_DAY, hour);
                cal.set(Calendar.MINUTE, minute);
                cal.set(Calendar.SECOND, 00);
                //Intent intent = new Intent(getActivity(), AlarmReceiverActivity.class);
                //PendingIntent pendint = PendingIntent.getBroadcast(getActivity(),
                //        2, intent, 0);

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                        cal.getTimeInMillis(), 1000 * 60 * 60 * 24, pendint);

                Toast.makeText(getActivity(), "Alarm Set", Toast.LENGTH_SHORT).show();
            }

        });
        toolsViewModel.getAlltimes().observe(this, new Observer<List<Time>>() {
            @Override
            public void onChanged(List<Time> times) {
                if (times.size() >0){
                picker.setHour(times.get(0).getHour());
                picker.setMinute(times.get(0).getMinute());
                //textView.setText(times.get(0).toString());
                    }
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