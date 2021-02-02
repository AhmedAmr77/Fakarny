package com.example.tripreminder.database;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private TripDao tripDao;
    private LiveData<List<TripData>> upcoming;
    private LiveData<List<TripData>> history;
    private DatabaseReference myRef;

    public Repository(Application application) {
        Database db = Database.getDatabase(application);
        tripDao = db.tripDao();
        upcoming = tripDao.getUpcoming();
        history = tripDao.getHistory();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        myRef = database.getReference().child(mAuth.getCurrentUser().getUid());
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

    public void delete(TripData tripData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                tripDao.Delete(tripData);
            }
        }).start();
    }


    public void backup() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                myRef.setValue(null);
                List<TripData> list = tripDao.getAllData();
                for (TripData t : list) {
                    myRef.child(t.getId() + "").setValue(t);
                }
            }
        }).start();

    }

    public void initDatabase() {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    TripData data = snapshot1.getValue(TripData.class);
                    insert(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}
