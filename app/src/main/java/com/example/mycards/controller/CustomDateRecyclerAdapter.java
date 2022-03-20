package com.example.mycards.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycards.R;
import com.example.mycards.model.Pair;

import java.util.Date;
import java.util.List;

public class CustomDateRecyclerAdapter extends RecyclerView.Adapter<CustomDateRecyclerAdapter.DataViewHolder> {
    LayoutInflater mInflater;
    List<Pair<String, Date>> mItems;
    public CustomDateRecyclerAdapter(Context context, List<Pair<String,Date>> items)
    {
        mInflater = LayoutInflater.from(context);
        this.mItems = items;
    }
    public void updateList(List<Pair<String,Date>> pairs) {
        this.mItems = pairs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.no_label_date_field, parent,false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomDateRecyclerAdapter.DataViewHolder holder, int position) {
        String title = mItems.get(position).getKey();
        Date field = mItems.get(position).getValue();


    }
    @Override
    public int getItemCount() {
        return mItems.size();
    }
    class DataViewHolder extends RecyclerView.ViewHolder{
        EditText label;
        Date field;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            label = (EditText) itemView.findViewById(R.id.labelTextField);

        }

    }
}
