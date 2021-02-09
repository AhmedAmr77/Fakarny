package com.example.tripreminder.ui.upcoming;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.tripreminder.AlarmReceiver;
import com.example.tripreminder.MainActivity;
import com.example.tripreminder.database.Database;
import com.example.tripreminder.database.Repository;
import com.example.tripreminder.database.TripData;

import java.util.Calendar;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class UpcomingViewModel extends AndroidViewModel {

    private final LiveData<List<TripData>> upcoming;
    private Repository mRepository;
    Application application;

    public UpcomingViewModel(Application application) {
        super(application);
        this.application = application;

        mRepository = new Repository(application);
        upcoming = mRepository.getUpcomingTrips();

    }


    LiveData<List<TripData>> getUpcoming() {
        return upcoming;
    }

    public void remove(TripData tripData) {
        mRepository.delete(tripData);
    }
}