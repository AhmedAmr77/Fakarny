package com.example.tripreminder.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TripData {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String state;
    private String tripName, startPoint, enaPoint;
    private String date, time, repeatData, wayData;
    private String lat_long_startPoint, lat_long_endPoint;

    public int getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getTripName() {
        return tripName;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public String getEnaPoint() {
        return enaPoint;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getRepeatData() {
        return repeatData;
    }

    public String getWayData() {
        return wayData;
    }

    public String getLat_long_startPoint() {
        return lat_long_startPoint;
    }

    public String getLat_long_endPoint() {
        return lat_long_endPoint;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public void setRepeatData(String repeatData) {
        this.repeatData = repeatData;
    }

    public void setWayData(String wayData) {
        this.wayData = wayData;
    }

    public void setLat_long_startPoint(String lat_long_startPoint) {
        this.lat_long_startPoint = lat_long_startPoint;
    }

    public void setLat_long_endPoint(String lat_long_endPoint) {
        this.lat_long_endPoint = lat_long_endPoint;
    }

    public void setEnaPoint(String enaPoint) {
        this.enaPoint = enaPoint;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
