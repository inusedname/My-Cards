package com.example.mycards.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Coupon extends MembershipBase implements Serializable {
    LocalDate expDate;

    public LocalDate getExpDate() {
        return expDate;
    }

    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }

    public Coupon() {}
    public Coupon(String shortName, String fullName, String id, String issuer, List<Pair<String,String>> customString, List<Pair<String, LocalDate>> customDate, LocalDate expDate)
    {
        this.setShortName(shortName);
        this.setFullName(fullName);
        this.setMembershipID(id);
        this.setIssuer(issuer);
        this.setTextProperties(customString);
        this.setDateProperties(customDate);
        this.expDate = expDate;
    }
}
