package com.example.mycards.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
    public Subscription(String shortName, String fullName, String id, String issuer, List<Pair<String,String>> customString, List<Pair<String, LocalDate>> customDate, LocalDate renewDate)
    {
        this.setShortName(shortName);
        this.setFullName(fullName);
        this.setMembershipID(id);
        this.setIssuer(issuer);
        this.setTextProperties(customString);
        this.setDateProperties(customDate);
        this.renewDate = renewDate;
    }
}
