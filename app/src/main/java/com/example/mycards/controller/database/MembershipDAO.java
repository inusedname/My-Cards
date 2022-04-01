package com.example.mycards.controller.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mycards.model.Card;
import com.example.mycards.model.Coupon;
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
    List<Card> getCardList();

    @Query("SELECT * FROM couponEntity")
    List<Coupon> getCouponList();

    @Query("SELECT * FROM subscriptionEntity")
    List<Subscription> getSubscriptionList();

    @Query("SELECT * FROM cardEntity WHERE systemID=:sysId")
    Card getCard(int sysId);

    @Query("SELECT * FROM couponEntity WHERE systemID=:sysId")
    Coupon getCoupon(int sysId);

    @Query("SELECT * FROM subscriptionEntity WHERE systemID=:sysId")
    Subscription getSub(int sysId);

    @Query("DELETE FROM cardEntity WHERE systemID=:sysID")
    void deleteCard(int sysID);

    @Query("DELETE FROM couponEntity WHERE systemID=:sysID")
    void deleteCoupon(int sysID);

    @Query("DELETE FROM subscriptionEntity WHERE systemID=:sysID")
    void deleteSub(int sysID);

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
