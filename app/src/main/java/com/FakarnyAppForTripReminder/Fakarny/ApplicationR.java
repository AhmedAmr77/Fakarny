package com.FakarnyAppForTripReminder.Fakarny;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.lifecycle.Observer;

import com.FakarnyAppForTripReminder.Fakarny.database.Repository;
import com.FakarnyAppForTripReminder.Fakarny.database.TripData;

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
                Intent notifyIntent = new Intent(ApplicationR.this, AlarmReceiver.class);
                notifyPendingIntent = PendingIntent.getBroadcast(ApplicationR.this, 77, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.cancel(notifyPendingIntent);
                if (tripData.size() > 0) {
                    setAlarmToSystem(tripData.get(0));
                }
            }
        });
    }

    void setAlarmToSystem(TripData tripData) {
        Intent notifyIntent = new Intent(this, AlarmReceiver.class);
        notifyIntent.putExtra("tripData", tripData.getId());
        notifyPendingIntent = PendingIntent.getBroadcast(this, 77, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(tripData.getAlarmTime(),notifyPendingIntent),notifyPendingIntent);
        }else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, tripData.getAlarmTime(), notifyPendingIntent);

        }



    }
}
