package com.CS5520.sleepforest.ui.home;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.CS5520.sleepforest.CoinsListener;
import com.CS5520.sleepforest.R;
import com.CS5520.sleepforest.Shop;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;



public class HomeFragment extends Fragment implements SensorEventListener{
    /////////
    /////////
   // final int SLEEPHOUR = 9;
    private CoinsListener coinsListener;
    private int imageSrc;
    private TextView textView;
    private TextView currentCoin;
    private HomeViewModel homeViewModel;
    private BroadcastReceiver screenOffReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!growing){
                return ;
            }

            Date current = new Date();
            int[] diff = getTimeDiff(current, getBedtime());
            int diffh = diff[0];
            int diffm = diff[1];
            Log.e(TAG, "SCREEN_OFF");
            Log.e("time diffh", diffh + "");
            Log.e("time diffm", diffm + "");

            if ((diffh == 0 && diffm <=10) || (diffh <= 0 && diffm <=0)){
                return;
            }
            setGrowing(false);
            mainImage.setImageResource(R.drawable.main_fail);
        }
    };
    private Date bedtime;
    private Date sensorDetedtedTime;
    private boolean fail=false;
    private boolean growing = false;
    private ImageView mainImage;
    private SensorManager sensorManager;
    private int treeId ;
    private Sensor accelerometer;

    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    // private MovementListener sensorEventListener;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            imageSrc = getArguments().getInt("image");
        }
        else {
            imageSrc = R.drawable.main_page2;
        }



//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
       // View root = inflater.inflate(R.layout.fragment_home, container, false);
        textView = getView().findViewById(R.id.text_home);
        if (imageSrc != R.drawable.main_page2){
            textView.setText("Tree is growing while phone is locked");
        }else{
            textView.setText("Please go to setting to \nset your time to go to bed");
        }
        mainImage = getView().findViewById(R.id.imageViewMain);

//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//
//            }
//        });

        mainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (growing && sensorDetedtedTime != null) {
                    homeViewModel.getCoins().observe(getViewLifecycleOwner(), new Observer<List<Shop>>() {
                                @Override
                                public void onChanged(List<Shop> shops) {
                                    int earnedcoin = calculateCoins();
                                    int tempCoin = 0;
                                    if (shops.size() > 0) {
                                        tempCoin = shops.get(0).getTotalCoins();
                                        homeViewModel.deleteCoin();
                                        homeViewModel.insertCoin(new Shop(1, tempCoin + earnedcoin));
                                        currentCoin.setText(tempCoin + earnedcoin + "");
                                        coinsListener.sendCoins(shops.get(0).getTotalCoins());
                                    }

                                }
                            }

                            // TODO: get coins.

                            // TODO: reset state
                }
            }
        });
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

        // accelerometer sensor
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            Log.e(TAG, "GRAVITY supports");
        } else {
            Log.e(TAG, "no GRAVITY supports");
        }
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        // sensorEventListener = new MovementListener();
        // sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);

        mainImage.setImageResource(imageSrc);
        Log.e("tree", this.treeId + "");

        ///////////////////////////
        Button testDatabase = getView().findViewById(R.id.testCoin);
        final TextView displaycoin = getView().findViewById(R.id.getCoins);
        testDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // int coin = 30;
                homeViewModel.deleteCoin();
                homeViewModel.insertCoin(new Shop(1, 3000));

                homeViewModel.getCoins().observe(getViewLifecycleOwner(),new Observer<List<Shop>>() {
                    @Override
                    public void onChanged(@Nullable List<Shop> s) {
                        if (s.size() >0){
                        displaycoin.setText(s.get(0).getTotalCoins()+"");
                        coinsListener.sendCoins(s.get(0).getTotalCoins());}

                    }
                });
            }
        });

        currentCoin = getView().findViewById(R.id.currentCoin);
        homeViewModel.getCoins().observe(getViewLifecycleOwner(),new Observer<List<Shop>>() {
            @Override
            public void onChanged(@Nullable List<Shop> s) {
                if (s.size() >0){
                    if(treeId == 0){
                        displaycoin.setText(s.get(0).getTotalCoins()+"");
                        coinsListener.sendCoins(s.get(0).getTotalCoins());
                    }
                    if(treeId ==1){
                        int databaseCurrentCoin = s.get(0).getTotalCoins() -600;
                        homeViewModel.deleteCoin();
                        homeViewModel.insertCoin(new Shop(1, databaseCurrentCoin));
                        currentCoin.setText(databaseCurrentCoin +"");
                        coinsListener.sendCoins(s.get(0).getTotalCoins());
                    }
                    if(treeId ==2){
                        int databaseCurrentCoin = s.get(0).getTotalCoins() -800;
                        homeViewModel.deleteCoin();
                        homeViewModel.insertCoin(new Shop(1, databaseCurrentCoin));
                        currentCoin.setText(databaseCurrentCoin +"");
                        coinsListener.sendCoins(s.get(0).getTotalCoins());
                    }
                    if(treeId ==3){
                        int databaseCurrentCoin = s.get(0).getTotalCoins() -1200;
                        homeViewModel.deleteCoin();
                        homeViewModel.insertCoin(new Shop(1, databaseCurrentCoin));
                        currentCoin.setText(databaseCurrentCoin +"");
                        coinsListener.sendCoins(s.get(0).getTotalCoins());
                    }
                    if(treeId ==4){
                        int databaseCurrentCoin = s.get(0).getTotalCoins() -2000;
                        homeViewModel.deleteCoin();
                        homeViewModel.insertCoin(new Shop(1, databaseCurrentCoin));
                        currentCoin.setText(databaseCurrentCoin +"");
                        coinsListener.sendCoins(s.get(0).getTotalCoins());
                    }
                    if(treeId ==5){
                        int databaseCurrentCoin = s.get(0).getTotalCoins() -10000;
                        homeViewModel.deleteCoin();
                        homeViewModel.insertCoin(new Shop(1, databaseCurrentCoin));
                        currentCoin.setText(databaseCurrentCoin +"");
                        coinsListener.sendCoins(s.get(0).getTotalCoins());
                    }
                }
            }



        });

//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                displaycoin.setText();
//            }
//        });
      //  return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        Log.e(TAG, "nResume");
    }

    @Override
    public void onPause() {
        super.onPause();
//        sensorManager.unregisterListener(this);
        Log.e(TAG, "nPause");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
          //  Log.e(TAG, "onSensorChanged");
            mGravity = event.values.clone();
            // Shake detection
            float x = mGravity[0];
            float y = mGravity[1];
            float z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float)Math.sqrt(x*x + y*y + z*z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            // Make this higher or lower according to how much
            // motion you want to detect
            if(mAccel > 3 && growing){
                // do something
                sensorDetedtedTime = new Date();
                int[] diff = getTimeDiff(sensorDetedtedTime, getBedtime());
                int diffh = diff[0];
                int diffm = diff[1];
                Log.e(TAG, "SENSOR_DETEDTED_TIME");
                Log.e("time diffh", diffh + "");
                Log.e("time diffm", diffm + "");
                if (growing && diffh >= 6){
                    textView.setText("tap the tree to get coins");
                    switch(treeId){
                        case 0:
                            setGrowing(false);
                            break;
                        case 1:
                            mainImage.setImageResource(R.drawable.main_shop1);
                            setGrowing(false);
                            break;
                        case 2:
                            mainImage.setImageResource(R.drawable.main_shop2);
                            setGrowing(false);
                            break;
                        case 3:
                            mainImage.setImageResource(R.drawable.main_shop3);
                            setGrowing(false);
                            break;
                        case 4:
                            mainImage.setImageResource(R.drawable.main_shop4);
                            setGrowing(false);
                            break;
                        default:


                    }

                }
                Log.e(TAG, "HANDPHONE_SHAKE: " + sensorDetedtedTime.toString());

            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

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
        int mins = (int) (mills % (1000*60*60)/(60*1000));

        return new int[]{hours, mins};

    }

    public void setTreeId(int treeId) {
        this.treeId = treeId;
    }

    public int calculateCoins(){
        // TODO int[] rate = {0,600,800,1200,2000,10000};
        return 10;
    }

//    public void insertShop(Shop shop){
//        shopRepository = new ShopRepository(get);
//        shopRepository.insertShop(shop);
//    }
@SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        coinsListener = (CoinsListener)activity;
    }
}