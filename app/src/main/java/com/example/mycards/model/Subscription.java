package com.example.mycards.model;

import androidx.room.Entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity (tableName = "subscriptionEntity")
public class Subscription extends MembershipBase implements Serializable {
    LocalDate renewDate;

    public LocalDate getRenewDate() {
        return renewDate;
    }

    public void setRenewDate(LocalDate renewDate) {
        this.renewDate = renewDate;
    }

    public Subscription()
    {

    }
    public Subscription(String frontImg, String backImg, String shortName, String fullName, String id, String issuer, List<Pair<String,String>> customString, List<Pair<String, LocalDate>> customDate, LocalDate renewDate)
    {
        this.setFrontImgDir(frontImg);
        this.setBackImgDir(backImg);
        this.setShortName(shortName);
        this.setFullName(fullName);
        this.setMembershipID(id);
        this.setIssuer(issuer);
        this.setTextProperties(customString);
        this.setDateProperties(customDate);
        this.renewDate = renewDate;
    }
}
