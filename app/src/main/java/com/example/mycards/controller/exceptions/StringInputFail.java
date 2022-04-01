package com.example.mycards.controller.exceptions;

public class StringInputFail extends Exception{
    private final String msg;
    public StringInputFail(String msg)
    {
        this.msg = msg;
    }
    public String getMsg()
    {
        return msg;
    }
}
