package com.example.mycards.controller.util;

import android.content.res.Resources;

import com.example.mycards.R;
import com.example.mycards.controller.exceptions.StringInputFail;

public class NameValidator {
    static public void checkShortName(String shortName) throws StringInputFail
    {
        if (shortName.length() == 0)
            throw new StringInputFail(Resources.getSystem().getString(R.string.cantBeEmpty));
        if (shortName.length() > 12)
            throw new StringInputFail(Resources.getSystem().getString(R.string.cantLongerThan12));
        if (!shortName.matches(Resources.getSystem().getString(R.string.UniAlphaNumRegex)))
            throw new StringInputFail(Resources.getSystem().getString(R.string.canOnlyAlphaNum));
    }
    static public void checkFullName(String fullName) throws StringInputFail
    {
        if (fullName.length() == 0)
            throw new StringInputFail(Resources.getSystem().getString(R.string.cantBeEmpty));
        if (fullName.length() > 25)
            throw new StringInputFail(Resources.getSystem().getString(R.string.cantLongerThan25));
        if(!fullName.matches(Resources.getSystem().getString(R.string.UniAlphaNumRegex)))
            throw new StringInputFail(Resources.getSystem().getString(R.string.canOnlyAlphaNum));
    }
    static public void checkIssuer(String issuer) throws StringInputFail {
        if (issuer.length() == 0)
            throw new StringInputFail(Resources.getSystem().getString(R.string.cantBeEmpty));
        if (issuer.length() > 25)
            throw new StringInputFail(Resources.getSystem().getString(R.string.cantLongerThan25));
        if (!issuer.matches(Resources.getSystem().getString(R.string.UniAlphaNumRegex)))
            throw new StringInputFail(Resources.getSystem().getString(R.string.canOnlyAlphaNum));
    }
    static public void checkStringID(String id) throws StringInputFail {
        if (id.length() == 0)
            throw new StringInputFail(Resources.getSystem().getString(R.string.cantBeEmpty));
    }
}
