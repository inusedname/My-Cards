package com.example.mycards.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MembershipBase implements Serializable {
    String membershipID = null;
    String systemID = null;
    String shortName = null;
    String fullName = null;
    String issuer = null;
    List<Pair<String,String>> textProperties = new ArrayList<>();
    List<Pair<String, LocalDate>> dateProperties = new ArrayList<>();

    public void setTextProperties(List<Pair<String, String>> textProperties) {
        this.textProperties = textProperties;
    }

    public void setDateProperties(List<Pair<String, LocalDate>> dateProperties) {
        this.dateProperties = dateProperties;
    }

    public String getShortName() {
        return shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getMembershipID() {
        return membershipID;
    }

    public void setMembershipID(String membershipID) {
        this.membershipID = membershipID;
    }

    public String getSystemID() {
        return systemID;
    }

    public void setSystemID(String systemID) {
        this.systemID = issuer + "-" + getMembershipID() ;
    }

    public void addTextProperty(String key, String value){
        textProperties.add(new Pair<>(key,value));
    }
    public void editTextProperty(String key, String newValue){
        for (int i = 0; i < textProperties.size();i++)
        {
            if (textProperties.get(i).getKey().equals(key))
            {
                textProperties.set(i, new Pair<>(key, newValue));
                break;
            }
        }
    }
    public void removeTextProperty(String key) {
        for (int i = 0; i < textProperties.size();i++)
        {
            if (textProperties.get(i).getKey().equals(key))
            {
                textProperties.remove(i);
                break;
            }
        }
    }
    public void addDateProperty(String key, LocalDate value){
        dateProperties.add(new Pair<>(key,value));
    }
    public void editDateProperty(String key, LocalDate newValue){
        for (int i = 0; i < textProperties.size();i++)
        {
            if (dateProperties.get(i).getKey().equals(key))
            {
                dateProperties.set(i, new Pair<>(key, newValue));
                break;
            }
        }
    }
    public void removeDateProperty(String key) {
        for (int i = 0; i < dateProperties.size();i++)
        {
            if (dateProperties.get(i).getKey().equals(key))
            {
                dateProperties.remove(i);
                break;
            }
        }
    }
}
