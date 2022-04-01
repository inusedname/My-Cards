package com.example.mycards.controller;

import android.content.Context;

import com.example.mycards.controller.database.MembershipDB;
import com.example.mycards.model.Card;
import com.example.mycards.model.Coupon;
import com.example.mycards.model.MembershipBase;
import com.example.mycards.model.Subscription;

import java.util.List;

public class MembershipController {

    public static void addMembership(Context context, MembershipBase base) {
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
    public static List<Card> getCardList(Context context) {
        return MembershipDB.getInstance(context).cardDAO().getCardList();
    }
    public static List<Coupon> getCouponList(Context context) {
        return MembershipDB.getInstance(context).cardDAO().getCouponList();
    }
    public static List<Subscription> getSubscriptionList(Context context) {
        return MembershipDB.getInstance(context).cardDAO().getSubscriptionList();
    }
    public static void removeAll(Context context) {
        MembershipDB.getInstance(context).cardDAO().deleteAllCard();
        MembershipDB.getInstance(context).cardDAO().deleteAllCoupon();
        MembershipDB.getInstance(context).cardDAO().deleteAllSubscription();
    }
    public static int getCardCount(Context context) {
        return MembershipDB.getInstance(context).cardDAO().getCardCount();
    }
    public static int getCouponCount(Context context) {
        return MembershipDB.getInstance(context).cardDAO().getCouponCount();
    }
    public static int getSubCount(Context context) {
        return MembershipDB.getInstance(context).cardDAO().getSubCount();
    }
    public static void remove(Context context, int sysID, int whichType) {
        switch (whichType)
        {
            case CONSTANT.CARD:
                for (MembershipBase mBase: getCardList(context))
                    if (mBase.systemID == sysID)
                    {
                        mBase.clearImage();
                        break;
                    }
                MembershipDB.getInstance(context).cardDAO().deleteCard(sysID);
                break;
            case CONSTANT.COUPON:
                for (MembershipBase mBase: getCouponList(context))
                    if (mBase.systemID == sysID)
                    {
                        mBase.clearImage();
                        break;
                    }
                MembershipDB.getInstance(context).cardDAO().deleteCoupon(sysID);
                break;
            case CONSTANT.SUB:
                for (MembershipBase mBase: getSubscriptionList(context))
                    if (mBase.systemID == sysID)
                    {
                        mBase.clearImage();
                        break;
                    }
                MembershipDB.getInstance(context).cardDAO().deleteSub(sysID);
                break;
        }
    }
    public static void updateCard(Context context, MembershipBase mBase) {
        MembershipDB.getInstance(context).cardDAO().updateCard((Card)mBase);
    }
    public static void updateCoupon(Context context, MembershipBase mBase) {
        MembershipDB.getInstance(context).cardDAO().updateCoupon((Coupon)mBase);
    }
    public static void updateSub(Context context, MembershipBase mBase) {
        MembershipDB.getInstance(context).cardDAO().updateSubscription((Subscription)mBase);
    }
}
