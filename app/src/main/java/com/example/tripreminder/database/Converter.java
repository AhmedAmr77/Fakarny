package com.example.tripreminder.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Converter {
    @TypeConverter
    public String fromListToGson(List<String> notes){
        return new Gson().toJson(notes);
    }

    @TypeConverter
    public List fromGsonToList(String notes){
        return new Gson().fromJson(notes, List.class);
    }
}
