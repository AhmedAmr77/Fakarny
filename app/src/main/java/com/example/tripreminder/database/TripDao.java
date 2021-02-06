package com.example.tripreminder.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TripDao {
    @Query("select * from TripData where state= 'upcoming' order by alarmTime")
    LiveData<List<TripData>> getUpcoming();

    @Query("select * from TripData where state='done' OR state='cancel'")
    LiveData<List<TripData>> getHistory();

    @Query("select * from TripData where id =:getId")
    TripData getTripById(int getId);

    @Query("select * from TripData where state='done'")
    LiveData<List<TripData>> getDoneHistory();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTrip(TripData tripData);

    @Delete
    void Delete(TripData tripData);

    @Update
    void updateTripData(TripData tripData);

    @Query("delete from TripData")
    void deleteAll();



    @Query("select * from TripData  ")
    List<TripData> getAllData();
}
