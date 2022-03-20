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

import java.util.List;

public class CustomStringRecyclerAdapter extends RecyclerView.Adapter<CustomStringRecyclerAdapter.DataViewHolder> {
    LayoutInflater mInflater;
    List<Pair<String,String>> mItems;
    public CustomStringRecyclerAdapter(Context context, List<Pair<String,String>> items) {
        mInflater = LayoutInflater.from(context);
        this.mItems = items;
    }
    public void updateList(List<Pair<String,String>> pairs) {
        this.mItems = pairs;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.no_label_text_field, parent,false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomStringRecyclerAdapter.DataViewHolder holder, int position) {
        String label = mItems.get(position).getKey();
        String field = mItems.get(position).getValue();
        if (!label.isEmpty())
        {
            holder.label.setText(label);
            holder.field.setText(field);
        }
    }
    @Override
    public int getItemCount() {
        return mItems.size();
    }
    class DataViewHolder extends RecyclerView.ViewHolder{
        EditText label;
        EditText field;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            label = (EditText) itemView.findViewById(R.id.labelTextField);
            field = (EditText) itemView.findViewById(R.id.valueTextField);
        }

    }
}
