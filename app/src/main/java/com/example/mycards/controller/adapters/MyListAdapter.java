package com.example.mycards.controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mycards.R;

import java.util.List;
import java.util.Locale;

public class MyListAdapter extends ArrayAdapter<String> {

    public MyListAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner, parent, false);
        TextView tv = convertView.findViewById(R.id.spinnerSelectedTV);
        String category = this.getItem(position);
        if (category != null)
        {
            tv.setText(category);
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner_dropdown, parent, false);
        TextView tv = convertView.findViewById(R.id.spinnerCateTV);
        String category = this.getItem(position);
        if (category != null)
        {
            tv.setText(category);
        }
        return convertView;
    }
}
