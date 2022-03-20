package com.example.mycards.controller;

import android.content.Context;
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

import java.util.List;

public class CustomStringRecyclerAdapter extends RecyclerView.Adapter<CustomStringRecyclerAdapter.DataViewHolder> {
    LayoutInflater mInflater;
    List<Pair<String,String>> mItems;
    public CustomStringRecyclerAdapter(Context context, List<Pair<String,String>> items) {
        mInflater = LayoutInflater.from(context);
        this.mItems = items;
    }

    public void updateList(Pair<String,String> pair) {
        this.mItems.add(pair);
        System.out.println("Recently Added: " + (mItems.size() - 1));
        notifyItemInserted(mItems.size() - 1);
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
        if (holder.getAdapterPosition() == mItems.size() - 1)
        {
            holder.field.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.length() == 1 && holder.getAdapterPosition() == mItems.size() - 1)
                    {
                        updateList(new Pair<String,String>("",""));
//                        holder.field.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
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
            label = (EditText) itemView.findViewById(R.id.labelTextField);
            field = (EditText) itemView.findViewById(R.id.valueTextField);
        }

    }

}
