package com.support.android.designlibdemo.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.support.android.designlibdemo.Model.Contest;
import com.support.android.designlibdemo.R;
import com.support.android.designlibdemo.activity.ContestEntryActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Learn on 1/13/2016.
 */
public class ContestInfoRecyclerViewAdapter extends RecyclerView.Adapter<ContestInfoRecyclerViewAdapter.ViewHolder> {
    private List<Contest> mDataset;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View cardView;
        public ViewHolder(View cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

    public ContestInfoRecyclerViewAdapter(List<Contest> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContestInfoRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contest_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}