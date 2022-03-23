package com.example.mycards.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Card extends MembershipBase implements Serializable {
    public Card()
    {

    }
    public Card(String shortName, String fullName, String id, String issuer, List<Pair<String,String>> customString, List<Pair<String, LocalDate>> customDate)
    {
        this.setShortName(shortName);
        this.setFullName(fullName);
        this.setMembershipID(id);
        this.setIssuer(issuer);
        this.setTextProperties(customString);
        this.setDateProperties(customDate);
    }
}
