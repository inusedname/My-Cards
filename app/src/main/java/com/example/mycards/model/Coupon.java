package com.example.mycards.model;

import java.util.Date;

public class Coupon extends MembershipBase{
    Date expDate;

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }
}
