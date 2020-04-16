package com.CS5520.sleepforest;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * This is the page for users to adjust progress and volume when white noises are playing.
 * Modified from https://codingwithsara.com/java-android-studio-tutorial-simple-music-player/
 */
public class WhiteNoisePlayer extends AppCompatActivity {

    Button playBtn;
    SeekBar positionBar;
    SeekBar volumeBar;
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

        // position
        positionBar = (SeekBar)findViewById(R.id.positionBar);
        positionBar.setMax(totalTime);
        positionBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            mp.seekTo(progress);
                            positionBar.setProgress(progress);
                        }

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );

        // volume
        volumeBar = (SeekBar)findViewById(R.id.volumnBar);
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

        new Thread(new Runnable() {

            @Override
            public void run() {
                while(mp != null) {
                    try {
                        Message msg = new Message();
                        msg.what = mp.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                }
            }
        }).start();

    }

    private void play(final int extra) {
        //R.raw.pn01
        mp = MediaPlayer.create(this, extra);
        mp.seekTo(0);
        mp.setVolume(0.5f, 0.5f);
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

//    private void stopPlayer(int extra) {
//        mp.release();
//        mp = null;
//        mp = MediaPlayer.create(this, extra);
//        Log.i("Media Player", "release player");
//    }

    public void playBtnClick(View view) {
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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currPosition = msg.what;
            positionBar.setProgress(currPosition);

            String elapsedTime = createTimeLabel(currPosition);
            elapsedTimeLabel.setText(elapsedTime);

            String remainingTime = createTimeLabel(totalTime - currPosition);
            remainingTimeLabel.setText("-" + remainingTime);
        }
    };

    public String createTimeLabel(int time) {
        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        timeLabel = min + ":";
        if (sec < 0) sec = 0;
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;
        return timeLabel;
    }

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
