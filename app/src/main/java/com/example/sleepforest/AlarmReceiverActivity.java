package com.example.sleepforest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;


public class AlarmReceiverActivity extends BroadcastReceiver {
    NotificationManager notificationManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("status", "received");


        notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel("com.example.sleepforest.bedtime", "Sleep Forest",
                "Time to go to bed.");

        Toast.makeText(context, Calendar.getInstance().getTime().toString() , Toast.LENGTH_LONG).show();
        sendNotification(context);


    }


    private void createNotificationChannel(String id, String name, String description) {
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        channel.setDescription(description);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setVibrationPattern(
                new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400}
        );

        AudioAttributes att = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();
        channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), att);

        notificationManager.createNotificationChannel(channel);
    }

    protected void sendNotification(Context context) {
        int notificationID = 101;
        String channelID = "com.example.sleepforest.bedtime";
        Notification notification = new Notification.Builder(context, channelID)
                .setContentTitle("Sleep Forest")
                .setContentText("Time to go to bed.")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setChannelId(channelID)
                .build();
        notificationManager.notify(notificationID, notification);

    }
}
