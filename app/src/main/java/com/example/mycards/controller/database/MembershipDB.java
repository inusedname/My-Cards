package com.example.mycards.controller.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.mycards.model.Card;
import com.example.mycards.model.Coupon;
import com.example.mycards.model.Subscription;


@Database(entities = {Card.class, Coupon.class, Subscription.class}, version = 1)
@TypeConverters({StringLocalDateConverter.class, StringStringConverter.class, DateConverter.class})
public abstract class MembershipDB extends RoomDatabase {
    private static final String DATABASE_NAME = "Memberships.db";
    private static MembershipDB instance;
    public static synchronized MembershipDB getInstance(Context context){
        if (instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext(), MembershipDB.class, DATABASE_NAME).allowMainThreadQueries().build();
        return instance;
    }
    public abstract MembershipDAO cardDAO();
}
