package com.FakarnyAppForTripReminder.Fakarny;

import java.io.Serializable;

public class HistoryTripInfo implements Serializable {
    String tripName;
    long tripTime;
    int tripDistance;
    int tripDuration;

    public HistoryTripInfo(String tripName, long tripTime, int tripDistance, int tripDuration) {
        this.tripName = tripName;
        this.tripTime = tripTime;
        this.tripDistance = tripDistance;
        this.tripDuration = tripDuration;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public long getTripTime() {
        return tripTime;
    }

    public void setTripTime(long tripTime) {
        this.tripTime = tripTime;
    }

    public int getTripDistance() {
        return tripDistance;
    }

    public void setTripDistance(int tripDistance) {
        this.tripDistance = tripDistance;
    }

    public int getTripDuration() {
        return tripDuration;
    }

    public void setTripDuration(int tripDuration) {
        this.tripDuration = tripDuration;
    }
}
