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
    final int[] ids = {R.raw.wn01_10min,
            R.raw.wn02_10min, R.raw.wn02_60min,
            R.raw.wn03_10min};

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
        listContent.add(new Music("dryer ", " 10 minutes"));
        listContent.add(new Music("dryer ", " 60 minutes"));
        listContent.add(new Music("heater", "10 minutes"));
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


        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mp != null) mp.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mp != null) mp.stop();
    }
}