package com.example.mycards.controller;

import android.content.Context;

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
    private final List<Subscription> mSubList;
    private final List<Card> mCardList;
    private final List<Coupon> mCouponList;

    public MembershipController(Context context)
    {
        // Load user data
        mCardList = FileLoader.loadFromFile(context, "card_list.dat");
        mCouponList = FileLoader.loadFromFile(context, "coupon_list.dat");
        mSubList = FileLoader.loadFromFile(context, "subscription_list.dat");
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
}
