package com.FakarnyAppForTripReminder.Fakarny.ui.history;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.FakarnyAppForTripReminder.Fakarny.database.Repository;
import com.FakarnyAppForTripReminder.Fakarny.database.TripData;

import java.util.List;


public class HistoryViewModel extends AndroidViewModel {

    private LiveData<List<TripData>> data;
    private Repository repository;


    public HistoryViewModel(Application application) {
        super(application);
        repository = new Repository(application);
        data = repository.getHistory();
    }

    public LiveData<List<TripData>> getData() {
        return data;
    }

    public void remove(TripData tripData) {
      repository.delete(tripData);
    }
}