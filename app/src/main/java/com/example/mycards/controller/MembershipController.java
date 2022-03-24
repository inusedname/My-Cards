package com.example.mycards.controller;

import android.content.Context;
import android.util.Log;

import com.example.mycards.MainActivity;
import com.example.mycards.controller.util.FileLoader;
import com.example.mycards.model.Card;
import com.example.mycards.model.Coupon;
import com.example.mycards.model.Pair;
import com.example.mycards.model.Subscription;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MembershipController {
    private List<Subscription> mSubList;
    private List<Card> mCardList;
    private List<Coupon> mCouponList;

    public MembershipController(Context context)
    {
        loadGame(context);
    }
    public void addCard(String shortName, String fullName, String id, String issuer, List<Pair<String,String>> customString, List<Pair<String, LocalDate>> customDate)
    {
        mCardList.add(new Card(shortName, fullName, id, issuer, customString, customDate));
    }
    public void addCard(Card card)
    {
        mCardList.add(card);
    }
    public void addCoupon(String shortName, String fullName, String id, String issuer, List<Pair<String,String>> customString, List<Pair<String, LocalDate>> customDate, LocalDate expDate)
    {
        mCouponList.add(new Coupon(shortName, fullName, id, issuer, customString, customDate,  expDate));
    }
    public void addCoupon(Coupon coupon)
    {
        mCouponList.add(coupon);
    }
    public void addSubscription(String shortName, String fullName, String id, String issuer, List<Pair<String,String>> customString, List<Pair<String, LocalDate>> customDate, LocalDate renewDate)
    {
        mSubList.add(new Subscription(shortName, fullName, id, issuer, customString, customDate, renewDate));
    }
    public void addSubscription(Subscription sub)
    {
        mSubList.add(sub);
    }

    public void clearAll(Context context)
    {
        mSubList.clear();
        mCardList.clear();
        mCouponList.clear();
        saveGame(context);
    }
    public void saveGame(Context context)
    {
        FileLoader.saveToFile(context, "card_list.dat", mCardList);
        FileLoader.saveToFile(context, "coupon_list.dat", mCouponList);
        FileLoader.saveToFile(context, "subscription_list.dat",mSubList);
        Log.i("SAVE/LOAD", "Save complete");
    }
    public void loadGame(Context context)
    {
        mCardList = FileLoader.loadFromFile(context, "card_list.dat");
        mCouponList = FileLoader.loadFromFile(context, "coupon_list.dat");
        mSubList = FileLoader.loadFromFile(context, "subscription_list.dat");
        Log.i("SAVE/LOAD","Load complete");
    }
}
