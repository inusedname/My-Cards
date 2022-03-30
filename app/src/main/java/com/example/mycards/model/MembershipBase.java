package com.example.mycards.model;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MembershipBase implements Serializable {
    protected String membershipID = null;
    protected String shortName = null;
    protected String fullName = null;
    protected String issuer = null;
    protected int color;
    protected String frontImgDir = null;
    protected String backImgDir = null;
    protected List<Pair<String,String>> textProperties = new ArrayList<>();
    protected List<Pair<String, LocalDate>> dateProperties = new ArrayList<>();

    public String getShortName() {
        return shortName;
    }
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIssuer() {
        return issuer;
    }
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
    public void setFrontImgDir(String dir)
    {
        if (this.frontImgDir != null) {
            new File(frontImgDir).delete();
        }
        frontImgDir = dir;
    }
    public String getFrontImgDir() {
        return frontImgDir;
    }
    public void setBackImgDir(String dir)
    {
        if (this.backImgDir != null) {
            new File(backImgDir).delete();
        }
        backImgDir = dir;
    }
    public String getBackImgDir() {
        return backImgDir;
    }

    public String getMembershipID() {
        return membershipID;
    }

    public void setMembershipID(String membershipID) {
        this.membershipID = membershipID;
    }

    public List<Pair<String, String>> getTextProperties() {
        return textProperties;
    }

    public List<Pair<String, LocalDate>> getDateProperties() {
        return dateProperties;
    }

    public void setTextProperties(List<Pair<String, String>> textProperties) {
        this.textProperties = textProperties;
    }

    public void setDateProperties(List<Pair<String, LocalDate>> dateProperties) {
        this.dateProperties = dateProperties;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
