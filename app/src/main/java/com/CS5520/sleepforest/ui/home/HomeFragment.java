package com.CS5520.sleepforest.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.CS5520.sleepforest.MovementListener;
import com.CS5520.sleepforest.R;
import com.CS5520.sleepforest.ScreenReceiver;
import com.CS5520.sleepforest.Time;

import java.util.Calendar;
import java.util.Date;

import static androidx.constraintlayout.widget.Constraints.TAG;



public class HomeFragment extends Fragment {
   // final int SLEEPHOUR = 9;
    private int imageSrc;
    private HomeViewModel homeViewModel;
    private BroadcastReceiver screenOffReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Date current = new Date();
            int[] diff = getTimeDiff(current, getBedtime());
            int diffh = diff[0];
            int diffm = diff[1];
            Log.e(TAG, "SCREEN_OFF");
            Log.e(TAG, diff + "");

            if ((diffh == 0 && diffm <=10) || (diffh <= 0 && diffm <=0)){
                return;
            }
            setGrowing(false);
        }
    };
    private Date bedtime;
    private boolean fail=false;
    private boolean growing = true;
    private ImageView mainImage;
    private SensorManager sensorManager;
    private MovementListener sensorEventListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageSrc = getArguments().getInt("image");
        }
        else {
            imageSrc = R.drawable.main_page2;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        mainImage = root.findViewById(R.id.imageViewMain);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

            }
        });
       // screenReceiver = new ScreenReceiver();
        IntentFilter screenStatusIF = new IntentFilter();
        //screenStatusIF.addAction(Intent.ACTION_SCREEN_ON);
        screenStatusIF.addAction(Intent.ACTION_SCREEN_OFF);
        getActivity().registerReceiver(screenOffReceiver, screenStatusIF);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensor != null) {
            Log.e(TAG, "GRAVITY supports");
        } else {
            Log.e(TAG, "no GRAVITY supports");
        }

        sensorEventListener = new MovementListener();
        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);

        mainImage.setImageResource(imageSrc);

        return root;
    }

    public void setBedtime(Calendar bedtime) {
        this.bedtime = bedtime.getTime();
        Log.e(TAG,this.bedtime.toString());

    }

    public void setGrowing(boolean growing) {
        this.growing = growing;
    }

    public Date getBedtime() {
        return bedtime;


    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if (imageSrc == 0) {
//            mainImage.setImageResource(R.drawable.main_page2);
//
//        } else {
//            mainImage.setImageResource(imageSrc);
//        }
//    }

    public void setImageSrc(int imageSrc) {
        this.imageSrc = imageSrc;
       // Log.e("image",this.imageSrc + "");
    }

    private int[] getTimeDiff(Date current, Date bedtime){
        if (bedtime == null){
            return new int[]{0, 0};
        }
        long mills = current.getTime() - bedtime.getTime();
        int hours = (int) (mills/(1000 * 60 * 60));
        int mins = (int) (mills % (1000*60*60));

        return new int[]{hours, mins};

    }
}