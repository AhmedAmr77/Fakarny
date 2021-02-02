package com.example.tripreminder.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TripData {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String state;
    public String tripName, startPoint, enaPoint;
    public String date, time, repeatData, wayData;
    public String lat_long_startPoint, lat_long_endPoint;

}
