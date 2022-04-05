package com.example.mycards.controller.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mycards.R;
import com.example.mycards.controller.util.UIConfig;

public class SpinnerArrayAdapter extends ArrayAdapter<String> {

    public SpinnerArrayAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
    }
    boolean setFormat = false;
    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner, parent, false);
        TextView tv = convertView.findViewById(R.id.spinnerSelectedTV);
        if (setFormat)
        {
            tv.setTextSize(20);
            int color = UIConfig.getResAttr(convertView.getContext(), androidx.appcompat.R.attr.colorPrimary).data;
            tv.setTextColor(color);
            ((ImageView)convertView.findViewById(R.id.dropdownIcon)).setColorFilter(color);
            ((LinearLayout)convertView.findViewById(R.id.itemSpinner)).setGravity(Gravity.RIGHT);

        }
        String category = this.getItem(position);
        if (category != null)
            tv.setText(category);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner_dropdown, parent, false);
        TextView tv = convertView.findViewById(R.id.spinnerCateTV);
        if (setFormat)
        {
            tv.setTextSize(20);
        }
        String category = this.getItem(position);
        if (category != null)
            tv.setText(category);
        return convertView;
    }
    public void setFormat(boolean b){
        setFormat = b;
    }
}
