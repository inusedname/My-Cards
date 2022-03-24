package com.example.mycards.controller.adapters;

import android.content.Context;
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

public class HomeCardViewRecylerAdapter extends RecyclerView.Adapter<HomeCardViewRecylerAdapter.DataViewHolder> {
    LayoutInflater mInflater;
    List<MembershipBase> mItems;
    public HomeCardViewRecylerAdapter(Context context, List<MembershipBase> mItems)
    {
        mInflater = LayoutInflater.from(context);
        this.mItems.clear();
        this.mItems = mItems;
    }


    @NonNull
    @Override
    public HomeCardViewRecylerAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_home, parent,false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCardViewRecylerAdapter.DataViewHolder holder, int position) {
        String shortName = mItems.get(position).getShortName();

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        ImageView bg;
        TextView tv;
        RelativeLayout rl;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            bg = (ImageView) itemView.findViewById(R.id.card_preview_home_im);
            tv = (TextView) itemView.findViewById(R.id.card_preview_home_tv);
            rl = (RelativeLayout) itemView.findViewById(R.id.card_preview_home_rl);
            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
