package com.support.android.designlibdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.support.android.designlibdemo.Model.Contest;
import com.support.android.designlibdemo.R;

import java.util.List;

/**
 * Created by Learn on 1/13/2016.
 */
public class ContestEntriesRecyclerViewAdapter extends RecyclerView.Adapter<ContestEntriesRecyclerViewAdapter.ViewHolder> {
    private List<Contest> mDataset;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View cardView;
        public ViewHolder(View cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

    public ContestEntriesRecyclerViewAdapter(List<Contest> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContestEntriesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contest_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contest tmpContest = mDataset.get(position);
        holder.cardView.setTag(tmpContest);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}