package com.example.mycards.controller.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycards.R;
import com.example.mycards.model.MembershipBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.DataViewHolder> implements Filterable {

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String target = charSequence.toString().toLowerCase(Locale.ROOT);
                FilterResults filterResults = new FilterResults();
                if (target.isEmpty())
                    filterResults.values = allMembershipOld;
                else {
                    List<MembershipBase> resultList = new ArrayList<>();
                    for (MembershipBase mBase: allMembershipOld)
                        if (mBase.getFullName().toLowerCase(Locale.ROOT).contains(target)
                         || mBase.getShortName().toLowerCase(Locale.ROOT).contains(target)
                         || mBase.getIssuer().toLowerCase(Locale.ROOT).contains(target))
                            resultList.add(mBase);
                    filterResults.values = resultList;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                allMembership = (List<MembershipBase>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public void reset()
    {
        allMembership = allMembershipOld;
        notifyDataSetChanged();
    }
    public interface OnClickSearch {
        void onClick(MembershipBase mBase);
    }
    private final OnClickSearch onItemClick;
    private List<MembershipBase> allMembership;
    private List<MembershipBase> allMembershipOld;
    public SearchRecyclerAdapter(List<MembershipBase> allMembership, OnClickSearch onItemClick) {
        this.allMembership = allMembership;
        this.allMembershipOld = allMembership;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        MembershipBase mBase = allMembership.get(position);
        if (mBase == null)
            return;
        holder.fullNameTV.setText(mBase.getFullName());
        holder.issuerTV.setText(mBase.getIssuer());
        holder.itemSearch.setOnClickListener(view -> onItemClick.onClick(mBase));
    }

    @Override
    public int getItemCount() {
        if (allMembership == null)
            return 0;
        return allMembership.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        private final TextView fullNameTV;
        private final TextView issuerTV;
        private final LinearLayout itemSearch;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            fullNameTV = itemView.findViewById(R.id.fullNameTV);
            issuerTV = itemView.findViewById(R.id.issuerTV);
            itemSearch = itemView.findViewById(R.id.itemSearch);
        }
    }
}
