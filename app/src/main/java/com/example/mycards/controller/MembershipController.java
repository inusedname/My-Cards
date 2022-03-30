package com.example.mycards.controller;

import android.content.Context;

import com.example.mycards.controller.database.MembershipDB;
import com.example.mycards.model.Card;
import com.example.mycards.model.Coupon;
import com.example.mycards.model.MembershipBase;
import com.example.mycards.model.Subscription;

import java.util.List;

public class MembershipController {

    public static void addMembership(Context context, MembershipBase base)
    {
        if (Card.class.equals(base.getClass())) {
            MembershipDB.getInstance(context).cardDAO().insertCard((Card)base);
        }
        else if (Coupon.class.equals(base.getClass())) {
            MembershipDB.getInstance(context).cardDAO().insertCoupon((Coupon)base);
        }
        else if (Subscription.class.equals(base.getClass())) {
            MembershipDB.getInstance(context).cardDAO().insertSub((Subscription)base);
        }

    }
    public static List<MembershipBase> getCardList(Context context)
    {
        return MembershipDB.getInstance(context).cardDAO().getCardList();
    }
    public static List<MembershipBase> getCouponList(Context context)
    {
        return MembershipDB.getInstance(context).cardDAO().getCouponList();
    }
    public static List<MembershipBase> getSubscriptionList(Context context)
    {
        return MembershipDB.getInstance(context).cardDAO().getSubscriptionList();
    }
    public static void removeAll(Context context)
    {
        MembershipDB.getInstance(context).cardDAO().deleteAllCard();
        MembershipDB.getInstance(context).cardDAO().deleteAllCoupon();
        MembershipDB.getInstance(context).cardDAO().deleteAllSubscription();
    }
    public static int getCardCount(Context context)
    {
        return MembershipDB.getInstance(context).cardDAO().getCardCount();
    }
    public static int getCouponCount(Context context)
    {
        return MembershipDB.getInstance(context).cardDAO().getCouponCount();
    }
    public static int getSubCount(Context context)
    {
        return MembershipDB.getInstance(context).cardDAO().getSubCount();
    }
}
