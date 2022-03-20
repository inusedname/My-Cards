package com.example.mycards.controller;

import com.example.mycards.model.Card;
import com.example.mycards.model.Coupon;

import java.util.ArrayList;
import java.util.List;

public class CouponController extends MembershipController {
    private final List<Coupon> couponsList = new ArrayList<>();
    public void addCoupon(Coupon c)
    {
        couponsList.add(c);
    }
    public void removeCoupon(Coupon c)
    {
        couponsList.remove(c);
    }
    public int getIndex(String id)
    {
        for (int i = 0; i < couponsList.size();i++)
            if (couponsList.get(i).getSystemID().equals(id))
                return i;
        return -1;
    }
    public void removeCoupon(String id)
    {
        int pos = getIndex(id);
        couponsList.remove(pos);
    }
    public void editCoupon(String id, Coupon c)
    {
        couponsList.set(getIndex(id),c);
    }
}
