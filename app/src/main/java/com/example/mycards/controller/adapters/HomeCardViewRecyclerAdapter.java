package com.example.mycards.controller.adapters;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycards.R;
import com.example.mycards.model.MembershipBase;

import java.util.List;

public class HomeCardViewRecyclerAdapter extends RecyclerView.Adapter<HomeCardViewRecyclerAdapter.DataViewHolder> {
    List<MembershipBase> mItems;

    public HomeCardViewRecyclerAdapter(List<MembershipBase> mItems)
    {
        this.mItems = mItems;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<MembershipBase> mItems)
    {
        this.mItems.clear();
        this.mItems.addAll(mItems);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public HomeCardViewRecyclerAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_home, parent,false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCardViewRecyclerAdapter.DataViewHolder holder, int position) {
        MembershipBase mBase = mItems.get(position);
        ImageView iv = holder.bg;
        TextView title = holder.tv;
        iv.setImageBitmap(BitmapFactory.decodeFile(mBase.getFrontImgDir()));
        title.setText(mBase.getShortName());
    }

    @Override
    public int getItemCount() {
        if (mItems == null)
            return 0;
        return mItems.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        ImageView bg;
        TextView tv;
        RelativeLayout rl;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            bg = itemView.findViewById(R.id.takePictureIV);
            tv = itemView.findViewById(R.id.titleTV);
            rl = itemView.findViewById(R.id.cardRL);

        }
    }
}
