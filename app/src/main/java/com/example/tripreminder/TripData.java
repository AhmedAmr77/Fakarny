package com.example.tripreminder;

public class TripData {

    private int id;
    private String tripName,startPoint,enaPoint;
    private String date ,time ,repeatData,wayData ;

    public TripData(int id, String tripName, String startPoint, String enaPoint, String date, String time, String repeatData, String wayData) {
        this.id = id;
        this.tripName = tripName;
        this.startPoint = startPoint;
        this.enaPoint = enaPoint;
        this.date = date;
        this.time = time;
        this.repeatData = repeatData;
        this.wayData = wayData;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
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

    public void setRepeatData(String repeatData) {
        this.repeatData = repeatData;
    }

    public void setWayData(String wayData) {
        this.wayData = wayData;
    }

    public int getId() {
        return id;
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
}
