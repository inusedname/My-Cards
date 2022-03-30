package com.example.mycards.controller.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mycards.model.Card;
import com.example.mycards.model.Coupon;
import com.example.mycards.model.MembershipBase;
import com.example.mycards.model.Subscription;

import java.util.List;

@Dao
public interface MembershipDAO {

    @Insert
    void insertCard(Card card);

    @Insert
    void insertCoupon(Coupon coupon);

    @Insert
    void insertSub(Subscription subscription);

    @Query("SELECT * FROM cardEntity")
    List<MembershipBase> getCardList();

    @Query("SELECT * FROM couponEntity")
    List<MembershipBase> getCouponList();

    @Query("SELECT * FROM subscriptionEntity")
    List<MembershipBase> getSubscriptionList();

    @Delete
    void deleteCard(Card card);

    @Delete
    void deleteCoupon(Coupon coupon);

    @Delete
    void deleteSub(Subscription sub);

    @Update
    void updateCard(Card card);

    @Update
    void updateCoupon(Coupon coupon);

    @Update
    void updateSubscription(Subscription sub);

    @Query("DELETE FROM cardEntity")
    void deleteAllCard();

    @Query("DELETE FROM couponEntity")
    void deleteAllCoupon();

    @Query("DELETE FROM subscriptionEntity")
    void deleteAllSubscription();

    @Query("SELECT COUNT(systemID) FROM cardEntity")
    int getCardCount();

    @Query("SELECT COUNT(systemID) FROM couponEntity")
    int getCouponCount();

    @Query("SELECT COUNT(systemID) FROM subscriptionEntity")
    int getSubCount();
}
