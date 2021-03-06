package com.support.android.designlibdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.support.android.designlibdemo.R;
import com.support.android.designlibdemo.other.SportFan;

import java.util.List;

/**
 * Created by Learn on 1/10/2016.
 */
public class LiveRecyclerViewAdapter extends RecyclerView.Adapter<LiveRecyclerViewAdapter.ViewHolder> {
    private List<SportFan> mDataset;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View cardView;
        public ViewHolder(View cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

    public LiveRecyclerViewAdapter(List<SportFan> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public LiveRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.cardView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}