package com.example.mycards.controller.adapters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycards.R;
import com.example.mycards.controller.util.ItemClickListener;
import com.example.mycards.controller.util.MyDatePicker;
import com.example.mycards.model.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomDateRecyclerAdapter extends RecyclerView.Adapter<CustomDateRecyclerAdapter.DataViewHolder> {
    LayoutInflater mInflater;
    List<Pair<String, LocalDate>> mItems = new ArrayList<>();
    public CustomDateRecyclerAdapter(Context context, List<Pair<String,LocalDate>> items) {
        mInflater = LayoutInflater.from(context);
        this.mItems.clear();
        this.mItems.addAll(items);
    }

    public void updateList(Pair<String,LocalDate> pair) {
        this.mItems.add(pair);
        // System.out.println("Recently Added: " + (mItems.size() - 1));
        //notifyItemInserted(mItems.size() - 1);
        notifyDataSetChanged();
    }
    public List<Pair<String, LocalDate>> getList()
    {
        return mItems;
    }
    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_label_date_input, parent,false);
        return new DataViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CustomDateRecyclerAdapter.DataViewHolder holder, int position) {
        String label = mItems.get(position).getKey();
        LocalDate field = mItems.get(position).getValue();
        if (field != null)
        {
            holder.label.setText(label);
            String date = field.getDayOfMonth() + "/" + (field.getMonthValue() + 1) + "/" + field.getYear();
            holder.field.setText(date);
            holder.myDatePicker.setTextID(holder.itemView,holder.field.getId(), field);
        }
        else
        {
            holder.label.setText("");
            holder.field.setText("Choose date");
            holder.myDatePicker.setTextID(holder.itemView, holder.field.getId());
        }
        holder.label.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    mItems.get(position).setKey(holder.label.getText().toString());
                }
            }
        });
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                holder.myDatePicker.showTheDialog(view);
            }
        });
        holder.myDatePicker.setSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                holder.myDatePicker.setDay(datePicker.getDayOfMonth());
                holder.myDatePicker.setMonth(datePicker.getMonth());
                holder.myDatePicker.setYear(datePicker.getYear());
                String date = holder.myDatePicker.getDay() + "/" + (holder.myDatePicker.getMonth() + 1) + "/" + holder.myDatePicker.getYear();
                holder.field.setText(date);
                mItems.get(position).setValue(holder.myDatePicker.getLocalDate());
            }
        });
    }
    @Override
    public int getItemCount() {
        return mItems.size();
    }
    static class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ItemClickListener itemClickListener;
        MyDatePicker myDatePicker = new MyDatePicker();
        EditText label;
        Button field;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            label = (EditText) itemView.findViewById(R.id.labelTextField);
            field = (Button) itemView.findViewById(R.id.selectDateButton);
            field.setOnClickListener(this);
        }
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }
        public void onClick(View v)
        {
            itemClickListener.onClick(v, getAdapterPosition());
        }
    }

}
