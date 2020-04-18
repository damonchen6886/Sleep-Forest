package com.CS5520.sleepforest.ui.slideshow;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.CS5520.sleepforest.Music;
import com.CS5520.sleepforest.MusicAdapter;
import com.CS5520.sleepforest.R;
import android.os.Handler;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    // private Context cont;
    private SlideshowViewModel slideshowViewModel;

    // music list
    ListView musicList;
    final ArrayList<Music> listContent = new ArrayList<>();
    final int[] ids = {R.raw.wn01_10min, R.raw.wn01_60min,
            R.raw.wn02_10min, R.raw.wn02_60min,
            R.raw.wn03_10min, R.raw.wn03_60min};

    // music player
    Button playBtn;
    SeekBar volumeBar;
    TextView currMusic;
    MediaPlayer mp;
    int totalTime;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        // ListView and Adapter
        musicList = (ListView)root.findViewById(R.id.musicList);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, listContent);
        listContent.add(new Music("waves", "10 minutes"));
        listContent.add(new Music("waves", "60 minutes"));
        listContent.add(new Music("dryer ", " 10 minutes"));
        listContent.add(new Music("dryer ", " 60 minutes"));
        listContent.add(new Music("heater", "10 minutes"));
        listContent.add(new Music("heater", "60 minutes"));
        ArrayAdapter<Music> adapter = new MusicAdapter(getActivity().getApplicationContext(), 0, listContent);
        musicList.setAdapter(adapter);
        currMusic = root.findViewById(R.id.currMusic);
        musicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //play the music
                if (mp != null) {
                    mp.release();
                    mp = null;
                }
                mp = MediaPlayer.create(getActivity().getApplicationContext(), ids[position]);
                mp.seekTo(0);
                mp.setVolume(0.5f, 0.5f);
                currMusic.setText(listContent.get(position).getName());
                playBtn.setBackgroundResource(R.drawable.stop);
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.seekTo(0);
                        mp.pause();
                        playBtn.setBackgroundResource(R.drawable.play);
                    }
                });
                totalTime = mp.getDuration();
                mp.start();
            }
        });


        // player
        playBtn = (Button)root.findViewById(R.id.playBtn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp != null) {
                    if (mp.isPlaying()) {
                        // playing
                        mp.pause();
                        playBtn.setBackgroundResource(R.drawable.play);
                    }
                    else {
                        // stopping
                        mp.start();
                        playBtn.setBackgroundResource(R.drawable.stop);
                    }
                }
            }
        });
//        elapsedTimeLabel = (TextView)root.findViewById(R.id.elapsedTimeLabel);
//        remainingTimeLabel = (TextView)root.findViewById(R.id.remainingTimeLabel);

        // position
//        positionBar = (SeekBar)root.findViewById(R.id.positionBar);
//        positionBar.setMax(totalTime);
//        positionBar.setOnSeekBarChangeListener(
//                new SeekBar.OnSeekBarChangeListener() {
//                    @Override
//                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                        if (fromUser) {
//                            mp.seekTo(progress);
//                            positionBar.setProgress(progress);
//                        }
//
//                    }
//
//                    @Override
//                    public void onStartTrackingTouch(SeekBar seekBar) {
//
//                    }
//
//                    @Override
//                    public void onStopTrackingTouch(SeekBar seekBar) {
//
//                    }
//                }
//        );

        // volume
        volumeBar = (SeekBar)root.findViewById(R.id.volumnBar);
        volumeBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        float volNum = progress / 100f;
                        mp.setVolume(volNum, volNum);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );

//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                while(mp != null) {
//                    try {
//                        Message msg = new Message();
//                        msg.what = mp.getCurrentPosition();
//                        handler.sendMessage(msg);
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {}
//                }
//            }
//        }).start();



        return root;
    }

//    public Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            int currPosition = msg.what;
//            positionBar.setProgress(currPosition);
//
//            String elapsedTime = createTimeLabel(currPosition);
//            elapsedTimeLabel.setText(elapsedTime);
//
//            String remainingTime = createTimeLabel(totalTime - currPosition);
//            remainingTimeLabel.setText("-" + remainingTime);
//        }
//    };



//    public String createTimeLabel(int time) {
//        String timeLabel = "";
//        int min = time / 1000 / 60;
//        int sec = time / 1000 % 60;
//
//        timeLabel = min + ":";
//        if (sec < 0) sec = 0;
//        if (sec < 10) timeLabel += "0";
//        timeLabel += sec;
//        return timeLabel;
//    }

    @Override
    public void onPause() {
        super.onPause();
        mp.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.stop();
    }
}