package com.example.mycards.controller.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycards.R;
import com.example.mycards.model.Pair;

import java.util.ArrayList;
import java.util.List;

public class CustomStringRecyclerAdapter extends RecyclerView.Adapter<CustomStringRecyclerAdapter.DataViewHolder> {
    LayoutInflater mInflater;
    List<Pair<String,String>> mItems = new ArrayList<>();
    public CustomStringRecyclerAdapter(Context context, List<Pair<String,String>> items) {
        mInflater = LayoutInflater.from(context);
        this.mItems.clear();
        this.mItems.addAll(items);
    }

    public List<Pair<String,String>> getList(){
        return mItems;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateList(Pair<String,String> pair) {
        this.mItems.add(pair);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_label_text_input, parent,false);
        return new DataViewHolder(itemView);
    }

    @SuppressWarnings("Convert2Lambda")
    @Override
    public void onBindViewHolder(@NonNull CustomStringRecyclerAdapter.DataViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String label = mItems.get(position).getKey();
        String field = mItems.get(position).getValue();
        holder.label.setText(label);
        holder.field.setText(field);


        holder.field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    mItems.get(position).setValue(holder.field.getText().toString());
                }
            }
        });
        holder.label.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    mItems.get(position).setKey(holder.label.getText().toString());
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return mItems.size();
    }


    static class DataViewHolder extends RecyclerView.ViewHolder{
        EditText label;
        EditText field;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.chooseDateET);
            field = itemView.findViewById(R.id.valueTextField);
        }

    }

}
