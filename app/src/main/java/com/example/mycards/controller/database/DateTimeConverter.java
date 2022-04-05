package com.example.mycards.controller.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class DateTimeConverter {
    @TypeConverter
    public LocalDateTime StringToLD (String value) {
        if (value== null) {
            return (null);
        }
        Type listType = new TypeToken<LocalDateTime>(){}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public String LDToString(LocalDateTime list) {
        if (list == null) {
            return (null);
        }
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
