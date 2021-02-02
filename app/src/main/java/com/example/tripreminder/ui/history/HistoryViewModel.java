package com.example.tripreminder.ui.history;

import android.app.Application;
import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.tripreminder.database.Database;
import com.example.tripreminder.database.Repository;
import com.example.tripreminder.database.TripData;

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