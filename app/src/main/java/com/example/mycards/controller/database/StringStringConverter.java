package com.example.mycards.controller.database;

import androidx.room.TypeConverter;

import com.example.mycards.model.Pair;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class StringStringConverter {
    @TypeConverter
    public List<Pair<String,String>> StringToList (String value)
    {
        if (value== null) {
            return (null);
        }
        Type listType = new TypeToken<List<Pair<String,String>>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public String ListToString(List<Pair<String,String>> list) {
        if (list == null) {
            return (null);
        }
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
