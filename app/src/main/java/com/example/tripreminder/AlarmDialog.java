package com.example.tripreminder;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.tripreminder.database.Repository;
import com.example.tripreminder.database.TripData;


public class AlarmDialog extends AppCompatActivity {
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 77;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    long repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;//SystemClock.elapsedRealtime();    //AlarmManager.INTERVAL_TWO_MINUTES;
    long triggerTime = SystemClock.elapsedRealtime() + repeatInterval;

    private Repository repository;
    private int tripData;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository = new Repository(getApplication());
        tripData = (int) getIntent().getIntExtra("tripData", -1);

        createNotificationChannel();
        showDialogWithSound();
    }

    public void createNotificationChannel() {
        // Create a notification manager object.
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel (PRIMARY_CHANNEL_ID,
                    "Stand up notification",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifies every 15 minutes to stand up and walk");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }
    public void showDialogWithSound() {
        MediaPlayer player =MediaPlayer.create(AlarmDialog.this,R.raw.fail);
        player.setLooping(true);
        player.start();


        AlertDialog.Builder builder = new AlertDialog.Builder(AlarmDialog.this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setTitle("Reminder");
        builder.setMessage("Is this what you intended to do?");
        builder.setPositiveButton("Start ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("trip","onClick "+tripData);
                repository.start(tripData);
                player.stop();
                finish();
            }
        });
        builder.setNeutralButton("Snooze", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deliverNotification(AlarmDialog.this);
                player.stop();
                finish();
            }
        });
        builder.setNegativeButton("Cancel",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //tripData.setState("cancel");
                //repository.update(tripData);
                player.stop();
                finish();
            }
        });
        // create and show the alert dialog
        builder.create();
        builder.show();
    }
    private void deliverNotification(Context context) {
        Intent contentIntent = new Intent(context.getApplicationContext(), AlarmDialog.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Reminder")
                .setContentText("You Are waiting for trip ITI Damitta")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setOngoing(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());

    }

}