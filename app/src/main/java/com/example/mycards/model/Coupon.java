package com.example.mycards.model;

import androidx.room.Entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
@Entity (tableName = "couponEntity")
public class Coupon extends MembershipBase implements Serializable {
    LocalDate expDate;

    public LocalDate getExpDate() {
        return expDate;
    }

    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }

    public Coupon() {}
    public Coupon(String frontImg, String backImg, String shortName, String fullName, String id, String issuer, List<Pair<String,String>> customString, List<Pair<String, LocalDate>> customDate, LocalDate expDate)
    {
        this.setFrontImgDir(frontImg);
        this.setBackImgDir(backImg);
        this.setShortName(shortName);
        this.setFullName(fullName);
        this.setMembershipID(id);
        this.setIssuer(issuer);
        this.setTextProperties(customString);
        this.setDateProperties(customDate);
        this.expDate = expDate;
    }
}
