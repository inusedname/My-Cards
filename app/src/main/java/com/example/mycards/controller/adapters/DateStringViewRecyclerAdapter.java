package com.example.mycards.controller.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycards.R;
import com.example.mycards.model.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateStringViewRecyclerAdapter extends RecyclerView.Adapter<DateStringViewRecyclerAdapter.DataViewHolder> {

    List<Pair<String, String>> mItems = new ArrayList<>();

    public DateStringViewRecyclerAdapter(List<Pair<String, String>> mItems) {
        if (mItems == null || mItems.size() == 0)
            return;
        this.mItems.addAll(mItems);
    }
    public DateStringViewRecyclerAdapter(List<Pair<String, LocalDate>> mItems, boolean isStringLocalList) {
        if (mItems == null || mItems.size() == 0)
            return;
        for (int i = 0; i < mItems.size(); i++)
        {
            LocalDate date = mItems.get(i).getValue();
            String key = mItems.get(i).getKey();
            String value = date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear();
            this.mItems.add(new Pair<>(key, value));
        }
    }
    public void refreshList(List<Pair<String, String>> mItems) {
        if (mItems == null || mItems.size() == 0)
            return;
        this.mItems = mItems;
        notifyDataSetChanged();
    }
    public void refreshList(List<Pair<String, LocalDate>> mItems, boolean isStringLocalList){
        if (mItems == null || mItems.size() == 0)
            return;
        this.mItems.clear();
        for (int i = 0; i < mItems.size(); i++)
        {
            LocalDate date = mItems.get(i).getValue();
            String key = mItems.get(i).getKey();
            String value = date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear();
            this.mItems.add(new Pair<>(key, value));
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_label_text,parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        Pair<String, String> item = mItems.get(position);
        holder.label.setText(item.getKey());
        holder.value.setText(item.getValue());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class DataViewHolder extends RecyclerView.ViewHolder
    {
        TextView label, value;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.labelTV);
            value = itemView.findViewById(R.id.valueTV);
        }
    }
}
