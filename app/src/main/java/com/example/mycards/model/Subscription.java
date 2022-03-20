package com.example.mycards.model;

import java.util.Date;

public class Subscription extends MembershipBase{
    Date renewDate;

    public Date getRenewDate() {
        return renewDate;
    }

    public void setRenewDate(Date renewDate) {
        this.renewDate = renewDate;
    }
}
