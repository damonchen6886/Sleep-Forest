package com.CS5520.sleepforest.ui.tools;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.CS5520.sleepforest.AlarmReceiverActivity;
import com.CS5520.sleepforest.OnRegisterSuccessListener;
import com.CS5520.sleepforest.R;
import com.CS5520.sleepforest.Time;

import java.util.Calendar;
import java.util.List;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    private OnRegisterSuccessListener onRegisterSuccessListener;

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
        picker.setHour(23);
        picker.setMinute(0);
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
            hour = 23;
            minute = 0;
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
            onRegisterSuccessListener.onRegisterSuccess(cal);

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
                    hour = 23;
                    minute = 0;
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
                onRegisterSuccessListener.onRegisterSuccess(cal);
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

//    @Override
//    public void onStart() {
//        super.onStart();
//    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {


        super.onAttach(activity);
        onRegisterSuccessListener = (OnRegisterSuccessListener) activity;
        //onRegisterSuccessListener.onRegisterSuccess(new Time(1, 23, 0));

    }

    public void setOnRegisterSuccessListener(OnRegisterSuccessListener onRegisterSuccessListener) {
        this.onRegisterSuccessListener = onRegisterSuccessListener;
    }
}