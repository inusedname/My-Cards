package com.example.mycards.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity(tableName = "cardEntity")
public class Card extends MembershipBase implements Serializable {
    @PrimaryKey (autoGenerate = true)
    public int systemID;
    public Card()
    {

    }
    public Card(String frontImg, String backImg, String shortName, String fullName, String id, String issuer, List<Pair<String,String>> customString, List<Pair<String, LocalDate>> customDate)
    {
        this.setFrontImgDir(frontImg);
        this.setBackImgDir(backImg);
        this.setShortName(shortName);
        this.setFullName(fullName);
        this.setMembershipID(id);
        this.setIssuer(issuer);
        this.setTextProperties(customString);
        this.setDateProperties(customDate);
    }
}
