package com.example.mycards.controller.adapters;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
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
    List<Pair<String,String>> mItems = new ArrayList<>();
    public CustomStringRecyclerAdapter() {
    }

    public List<Pair<String,String>> getList(){
        return this.mItems;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateList(Pair<String,String> pair) {
        this.mItems.add(pair);
        notifyDataSetChanged();
    }
    public void updateList(List<Pair<String, String>> pair) {
        this.mItems = pair;
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


        holder.label.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    mItems.get(position).setKey(holder.label.getText().toString());
                }
            }
        });
        holder.field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mItems.get(position).setValue(holder.field.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
            label = itemView.findViewById(R.id.labelET);
            field = itemView.findViewById(R.id.valueET);
        }

    }

}
