package com.CS5520.sleepforest;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class WhiteNoisePlayer extends AppCompatActivity {

    Button playBtn;
    SeekBar positionBar;
    SeekBar volumnBar;
    TextView elapsedTimeLabel;
    TextView remainingTimeLabel;
    MediaPlayer mp;
    int totalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white_noise_player);

        playBtn = (Button)findViewById(R.id.playBtn);
        elapsedTimeLabel = (TextView)findViewById(R.id.elapsedTimeLabel);
        remainingTimeLabel = (TextView)findViewById(R.id.remainingTimeLabel);

        // media player
        int extra = getIntent().getIntExtra("soundFile", 0);
        play(extra);

        positionBar = (SeekBar)findViewById(R.id.positionBar);
        positionBar.setMax(totalTime );

        volumnBar = (SeekBar)findViewById(R.id.volumnBar);

    }

    private void play(int extra) {
        //R.raw.pn01
        mp = MediaPlayer.create(this, extra);
        mp.setLooping(true);
        mp.seekTo(0);
        mp.setVolume(0.5f, 0.5f);
        totalTime = mp.getDuration();
    }

    public void playBtnClick(View view) {
        if (!mp.isPlaying()) {
            // stopping
            mp.start();
            playBtn.setBackgroundResource(R.drawable.stop);
        }
        else {
            // playing
            mp.stop();
            playBtn.setBackgroundResource(R.drawable.play );
        }
    }
}
