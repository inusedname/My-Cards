package com.example.mycards.controller.util;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.core.content.ContextCompat;

import com.example.mycards.R;

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

    @SuppressWarnings("Convert2Lambda")
    public MyDatePicker(View view, int buttonID) {
        setTextID(view, buttonID);
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth() + 1;
                year = datePicker.getYear();
                String date = day + "/" + month + "/" + year;
                tv.setText(date);
                whatDoYouWantToDoAfterDateSet();
            }
        };
    }

    @SuppressWarnings({"Convert2Lambda", "UnusedDeclaration"})
    public MyDatePicker(View view, int buttonID, LocalDate date) {
        setTextID(view, buttonID, date);
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth() + 1;
                year = datePicker.getYear();
                String date = day + "/" + month + "/" + year;
                tv.setText(date);
                whatDoYouWantToDoAfterDateSet();
            }
        };
    }

    public void overrideOnDateSet(DatePickerDialog.OnDateSetListener setListener) {
        this.setListener = setListener;
    }
    public void showTheDialog(View view){
        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), android.R.style.Theme_DeviceDefault_Dialog, setListener, year, month - 1, day);
        int color = ContextCompat.getColor(view.getContext(), R.color.light_color);
        try
        {
            datePickerDialog.show();
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(color);
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(color);
        }
        catch(Exception e)
        {
            System.out.println("Can not display." + e);
        }
    }

    @SuppressLint("SetTextI18n")
    public void setTextID(View view, int id, LocalDate date) {
        tv = view.findViewById(id);
        if (date == null)
            date = LocalDate.now();
        year = date.getYear();
        month = date.getMonthValue();
        day = date.getDayOfMonth();
    }
    public void setTextID(View view, int id) {
        tv = view.findViewById(id);
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
    public LocalDate getLocalDate()
    {
        return LocalDate.of(year,month,day);
    }

    public abstract void whatDoYouWantToDoAfterDateSet();
}
