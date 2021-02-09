package com.example.tripreminder;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;

import androidx.lifecycle.Observer;

import com.example.tripreminder.database.Repository;
import com.example.tripreminder.database.TripData;

import java.util.List;

public class ApplicationR extends Application {
    AlarmManager alarmManager;
    PendingIntent notifyPendingIntent;

    @Override
    public void onCreate() {
        super.onCreate();
        Repository mRepository = new Repository(this);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mRepository.getUpcomingTrips().observeForever(new Observer<List<TripData>>() {
            @Override
            public void onChanged(List<TripData> tripData) {
                if (tripData.size() > 0) {
                    setAlarmToSystem(tripData.get(0));
                }
            }
        });
    }

    void setAlarmToSystem(TripData tripData) {
        Log.i("trip", "setAlaToSys " + tripData);
        Intent notifyIntent = new Intent(this, AlarmReceiver.class);
        notifyIntent.putExtra("tripData", tripData.getId());
        notifyPendingIntent = PendingIntent.getBroadcast(this, 77, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(notifyPendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, tripData.getAlarmTime(), notifyPendingIntent);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, tripData.getAlarmTime(), notifyPendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, tripData.getAlarmTime(), notifyPendingIntent);
            }
        }

    }
}
