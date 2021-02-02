package com.example.tripreminder.database;

import android.app.Application;

import androidx.lifecycle.LiveData;


import java.util.List;

public class Repository {
    private TripDao tripDao;
    private LiveData<List<TripData>> upcoming;
    private LiveData<List<TripData>> history;

    public Repository(Application application) {
        Database db = Database.getDatabase(application);
        tripDao = db.tripDao();
        upcoming = tripDao.getUpcoming();
        history = tripDao.getHistory();
    }


    public LiveData<List<TripData>> getUpcomingTrips() {
        return upcoming;
    }

    public LiveData<List<TripData>> getHistory() {
        return history;
    }

    public void insert(TripData tripData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                tripDao.addTrip(tripData);
            }
        }).start();
    }

    public void update(TripData tripData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                tripDao.updateTripData(tripData);
            }
        }).start();
    }

    public void deleteAll() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                tripDao.deleteAll();
            }
        }).start();
    }

}
