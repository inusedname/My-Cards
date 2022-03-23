package com.example.mycards.controller.util;

import com.example.mycards.controller.exceptions.StringInputFail;

public class NameValidator {
    static public void checkShortName(String shortName) throws StringInputFail
    {
        if (shortName.length() == 0)
            throw new StringInputFail("Không được để trống");
        if (shortName.length() > 8)
            throw new StringInputFail("Tên không được dài hơn 8 ký tự");
        if (!shortName.matches("[a-zA-Z0-9 \\u0080-\\u9fff]+"))
            throw new StringInputFail("Tên chỉ chứa các chữ cái và số");
    }
    static public void checkFullName(String fullName) throws StringInputFail
    {
        if (fullName.length() == 0)
            throw new StringInputFail("Không được để trống");
        if (fullName.length() > 16)
            throw new StringInputFail("Tên quá dài.");
        if(!fullName.matches("[a-zA-Z0-9 \\u0080-\\u9fff]+"))
            throw new StringInputFail("Tên chỉ chứa các chữ cái và số");
    }
    static public void checkIssuer(String issuer) throws StringInputFail {
        if (issuer.length() == 0)
            throw new StringInputFail("Không được để trống");
        if (issuer.length() > 16)
            throw new StringInputFail("Tên quá dài.");
        if (!issuer.matches("[a-zA-Z \\u0080-\\u9fff]+"))
            throw new StringInputFail("Tên chỉ chứa các chữ cái.");
    }
    static public void checkStringID(String id) throws StringInputFail {
        if (id.length() == 0)
            throw new StringInputFail("Không được để trống");
        if (!id.matches("[a-zA-Z0-9- ]+"))
            throw new StringInputFail("Tên chỉ chứa các chữ cái và số.");
    }
}
