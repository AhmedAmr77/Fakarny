package com.FakarnyAppForTripReminder.Fakarny.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converter {
    @TypeConverter
    public String fromListToGson(List<NoteData> notes){
        Type listType = new TypeToken<List<NoteData>>() {}.getType();
        return new Gson().toJson(notes,listType);
    }

    @TypeConverter
    public List<NoteData> fromGsonToList(String notes){
        return new Gson().fromJson(notes, new TypeToken<List<NoteData>>(){}.getType());
    }
}
