package com.example.mycards.controller.util;

import android.app.DatePickerDialog;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;

public abstract class MyDatePicker {
    private Button tv;
    private int year;
    private int month;
    private int day;
    private DatePickerDialog.OnDateSetListener setListener;

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressWarnings("Convert2Lambda")
    public MyDatePicker(View view, int buttonID)
    {
        setTextID(view, buttonID);
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth();
                year = datePicker.getYear();
                String date = day + "/" + (month + 1) + "/" + year;
                tv.setText(date);
                whatDoYouWantToDoAfterDateSet();
            }
        };
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressWarnings({"Convert2Lambda", "UnusedDeclaration"})
    public MyDatePicker(View view, int buttonID, LocalDate date)
    {
        setTextID(view, buttonID, date);
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth();
                year = datePicker.getYear();
                String date = day + "/" + (month + 1) + "/" + year;
                tv.setText(date);
                whatDoYouWantToDoAfterDateSet();
            }
        };
    }

    public void overrideOnDateSet(DatePickerDialog.OnDateSetListener setListener)
    {
        this.setListener = setListener;
    }
    public void showTheDialog(View view){
        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), android.R.style.Theme_DeviceDefault_Dialog_MinWidth, setListener, year, month, day);
        try
        {
            datePickerDialog.show();
        }
        catch(Exception e)
        {
            System.out.println("Can not display." + e);
        }


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setTextID(View view, int id, LocalDate date)
    {
        tv = view.findViewById(id);
        System.out.println("Found TextView!");
        year = date.getYear();
        month = date.getMonthValue();
        day = date.getDayOfMonth();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setTextID(View view, int id)
    {
        tv = view.findViewById(id);
        System.out.println("Found TextView!");
        LocalDate date = LocalDate.now();
        year = date.getYear();
        month = date.getMonthValue();
        day = date.getDayOfMonth();
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate getLocalDate()
    {
        return LocalDate.of(year,month,day);
    }

    public abstract void whatDoYouWantToDoAfterDateSet();
}
