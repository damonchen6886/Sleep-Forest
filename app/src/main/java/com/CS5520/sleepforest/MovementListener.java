package com.CS5520.sleepforest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MovementListener implements SensorEventListener {
    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.e(TAG, "onSensorChanged: X " + event.values[0] + "; Y " + event.values[1]+
                "; Z " + event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }
}
