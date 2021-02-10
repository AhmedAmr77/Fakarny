package com.FakarnyAppForTripReminder.Fakarny.ui.upcoming;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.FakarnyAppForTripReminder.Fakarny.database.Repository;
import com.FakarnyAppForTripReminder.Fakarny.database.TripData;

import java.util.List;

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