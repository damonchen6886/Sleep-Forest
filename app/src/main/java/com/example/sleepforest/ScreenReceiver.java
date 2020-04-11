package com.example.sleepforest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ScreenReceiver extends BroadcastReceiver {
    String onTime;
    boolean isOn;

    String SCREEN_ON = "android.intent.action.SCREEN_ON";
    String SCREEN_OFF = "android.intent.action.SCREEN_OFF";
    @Override
    public void onReceive(Context context, Intent intent) {

        if(SCREEN_ON.equals(intent.getAction())){
            Log.e(TAG, SCREEN_ON);
            Log.e(TAG, String.valueOf(Calendar.getInstance().getTime()));
            isOn = true;
            onTime = String.valueOf(Calendar.getInstance().getTime());


        }

        else if(SCREEN_OFF.equals(intent.getAction())){
            Log.e(TAG, SCREEN_OFF);
            isOn = false;
        }

    }

    public boolean isOn() {
        return isOn;
    }

    public String getOnTime() {
        return onTime;
    }
}
