package com.example.tripreminder.database;

import android.app.Application;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Repository {
    private TripDao tripDao;
    private LiveData<List<TripData>> upcoming;
    private LiveData<List<TripData>> history;
    private LiveData<List<TripData>> doneHistory;
    private DatabaseReference myRef;

    public Repository(Application application) {
        Database db = Database.getDatabase(application);
        tripDao = db.tripDao();
        upcoming = tripDao.getUpcoming();
        history = tripDao.getHistory();
        doneHistory = tripDao.getDoneHistory();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        myRef = database.getReference().child(mAuth.getCurrentUser().getUid());
    }

    public LiveData<List<TripData>> getDoneHistory() {

        return doneHistory;
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

    public void start(TripData tripData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                tripDao.updateTripData(tripData);
                Log.e("sdds", tripData.getEndAlarmTime() + "" + tripData.getWayData() + "");
                if (tripData.getWayData().equals("Round Trip") && !tripData.getRepeatData().contains("No")) {
                    repeatAndRound(tripData);
                } else if (tripData.getWayData().equals("Round Trip")) {
                    if (tripData.getEndAlarmTime() > tripData.getAlarmTime()) {
                        round(tripData);
                    }
                } else if (!tripData.getRepeatData().contains("No")) {
                    repeat(tripData);
                }
            }
        }).start();
    }

    private void repeatAndRound(TripData tripData) {
        if (tripData.getEndAlarmTime() > tripData.getAlarmTime()) {
            round(tripData);
        } else {
            repeat(tripData);
        }
    }

    private void round(TripData tripData) {
        TripData data = new TripData();
        data.setTime(tripData.getBackTime());
        data.setDate(tripData.getBackDate());
        data.setAlarmTime(tripData.getEndAlarmTime());
        String end = tripData.getEnaPoint();
        String start = tripData.getStartPoint();
        data.setStartPoint(end);
        data.setEnaPoint(start);
        end = tripData.getLat_long_endPoint();
        start = tripData.getLat_long_startPoint();
        data.setLat_long_startPoint(end);
        data.setLat_long_endPoint(start);
        data.setTripName(tripData.getTripName());
        data.setRepeatData(tripData.getRepeatData());
        data.setWayData(tripData.getWayData());
        data.setEndAlarmTime(tripData.getAlarmTime());
        data.setState("upcoming");
        insert(data);
    }

    private void repeat(TripData tripData) {
        TripData data = new TripData();
        String end = tripData.getEnaPoint();
        String start = tripData.getStartPoint();
        data.setStartPoint(start);
        data.setEnaPoint(end);
        end = tripData.getLat_long_endPoint();
        start = tripData.getLat_long_startPoint();
        data.setLat_long_startPoint(start);
        data.setLat_long_endPoint(end);
        data.setTripName(tripData.getTripName());
        data.setRepeatData(tripData.getRepeatData());
        data.setWayData(tripData.getWayData());
        data.setState("upcoming");
        Calendar calendar = Calendar.getInstance();
        long cale = calendar.getTimeInMillis() + tripData.getRepeatPlus();
        data.setRepeatPlus(tripData.getRepeatPlus());
        calendar.setTimeInMillis(cale);
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR);
        int mints = calendar.get(Calendar.MINUTE);
        data.setAlarmTime(cale);
        data.setTime(hours + ":" + mints);
        data.setDate(mDay + "-" + (mMonth+1) + "-" + mYear);
        if (tripData.getEndAlarmTime() > 0) {
            cale = cale + Math.abs(tripData.getEndAlarmTime() - tripData.getAlarmTime());
            calendar.setTimeInMillis(cale);
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DAY_OF_MONTH);
            hours = calendar.get(Calendar.HOUR);
            mints = calendar.get(Calendar.MINUTE);
            data.setEndAlarmTime(cale);
            data.setBackTime(hours + ":" + mints);
            data.setBackDate(mDay + "-" + (mMonth+1) + "-" + mYear);
            Log.e("kkkk",data.getBackDate()+"   "+data.getDate());
        }
        insert(data);
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


    public void synchronizedTrips() {
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
