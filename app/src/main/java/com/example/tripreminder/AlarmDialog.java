package com.example.tripreminder;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.tripreminder.database.Repository;
import com.example.tripreminder.database.TripData;
import com.example.tripreminder.ui.upcoming.RecyclerViAdapter;


public class AlarmDialog extends AppCompatActivity {
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 77;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private Repository repository;

    private int ID;
    private static final int SYSTEM_ALERT_WINDOW_PERMISSION = 2084;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository = new Repository(getApplication());

        ID = getIntent().getIntExtra("tripData", -1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                TripData tripData = repository.getByID(ID);
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        createNotificationChannel();
                        showDialogWithSound(tripData);
                    }
                });

            }
        }).start();


    }


    public void createNotificationChannel() {
        // Create a notification manager object.
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Stand up notification",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifies every 15 minutes to stand up and walk");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void showDialogWithSound(TripData tripData) {
        MediaPlayer player = MediaPlayer.create(AlarmDialog.this, R.raw.fail);
        player.setLooping(true);
        player.start();


        AlertDialog.Builder builder = new AlertDialog.Builder(AlarmDialog.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setTitle("Reminder");
        builder.setMessage("Is this what you intended to do?");
        builder.setPositiveButton("Start ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                tripData.setState("done");
                repository.start(tripData);
                player.stop();
                start(tripData);
                finish();

            }
        });
        builder.setNeutralButton("Snooze", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deliverNotification(AlarmDialog.this ,tripData);
                player.stop();
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                tripData.setState("cancel");
                repository.update(tripData);
                player.stop();
                finish();

            }
        });
        builder.create();
        builder.show();
    }

    void start(TripData tripData) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=" + tripData.getLat_long_endPoint()));
        String title = "TripyyReminder ";
        Intent chooser = Intent.createChooser(intent, title);

        try {
            showWidget(tripData.getId());
            this.startActivity(chooser);
            updateTrip(tripData, "done");
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "NO APP Can Open THIS !!!", Toast.LENGTH_SHORT).show();
        }
    }

    void showWidget(int id) {
        Intent intent = new Intent(this.getApplicationContext(), FloatingViewService.class);
        intent.putExtra("tripID", id);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            (this.getApplicationContext()).startService(intent);
        } else if (Settings.canDrawOverlays(this)) {
            (this.getApplicationContext()).startService(intent);
        } else {
            askPermission();
            Toast.makeText(this, "You need System Alert Window Permission to do this", Toast.LENGTH_SHORT).show();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void askPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + this.getPackageName()));
        startActivityForResult(intent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }

    public void updateTrip(TripData tripData, String state) {
        tripData.setState(state);
        if (state.equals("done")) {
            repository.start(tripData);
        } else {
            repository.update(tripData);
        }
    }

    private void deliverNotification(Context context, TripData tripData) {
        Intent contentIntent = new Intent(context.getApplicationContext(), AlarmDialog.class);
        contentIntent.putExtra("tripData", ID);
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Reminder for Trip "+tripData.getTripName())
                .setContentText(tripData.getEnaPoint())
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setOngoing(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());

    }

}