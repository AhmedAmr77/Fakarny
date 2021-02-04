package com.example.tripreminder.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@androidx.room.Database(entities = {TripData.class}, version = 1,exportSchema = false)
@TypeConverters(Converter.class)
public abstract class Database extends RoomDatabase {
    public abstract TripDao tripDao();
    private static Database INSTANCE;
    static Database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, "Trip_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
