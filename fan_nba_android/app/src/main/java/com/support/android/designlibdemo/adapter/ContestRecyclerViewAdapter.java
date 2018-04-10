package com.support.android.designlibdemo.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.support.android.designlibdemo.R;
import com.support.android.designlibdemo.activity.ContestEntryActivity;
import com.support.android.designlibdemo.Model.Contest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by LearnerMN on 1/11/16.
 */
public class ContestRecyclerViewAdapter extends RecyclerView.Adapter<ContestRecyclerViewAdapter.ViewHolder> {
    private List<Contest> mDataset;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View cardView;
        public ViewHolder(View cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

    public ContestRecyclerViewAdapter(List<Contest> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContestRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
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
        TextView c_title = (TextView)holder.cardView.findViewById(R.id.c_title);
        c_title.setText(tmpContest.title);

        TextView c_entries = (TextView)holder.cardView.findViewById(R.id.c_entries);
        c_entries.setText("$" + tmpContest.entryFee + " Entry $" + tmpContest.totalPrize +" Prizes " + tmpContest.entryCount + "/" + tmpContest.entryLimit + " Entries");

        TextView c_start_time = (TextView)holder.cardView.findViewById(R.id.c_start_time);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = df.parse(tmpContest.startTime);
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, h:mm a");
            c_start_time.setText(sdf.format(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contest contest = (Contest)v.getTag();
                Intent intent = new Intent(v.getContext(), ContestEntryActivity.class);
                intent.putExtra("contest_id",contest.id);
                intent.putExtra("contest_title",contest.title);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}