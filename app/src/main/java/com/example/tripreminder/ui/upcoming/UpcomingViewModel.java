package com.example.tripreminder.ui.upcoming;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.tripreminder.database.Database;
import com.example.tripreminder.database.Repository;
import com.example.tripreminder.database.TripData;

import java.util.List;

public class UpcomingViewModel extends AndroidViewModel {

    private final LiveData<List<TripData>> upcoming;

    public UpcomingViewModel(Application application) {
        super(application);
        Repository mRepository = new Repository(application);
        upcoming = mRepository.getUpcomingTrips();
    }

    LiveData<List<TripData>> getUpcoming() {
        return upcoming;
    }

}