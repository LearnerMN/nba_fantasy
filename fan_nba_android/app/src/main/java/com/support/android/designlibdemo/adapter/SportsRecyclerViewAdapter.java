package com.support.android.designlibdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.support.android.designlibdemo.Model.Contest;
import com.support.android.designlibdemo.R;
import com.support.android.designlibdemo.activity.ContestListActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Learn on 1/9/2016.
 */
public class SportsRecyclerViewAdapter extends RecyclerView.Adapter<SportsRecyclerViewAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View cardView;
        public ViewHolder(View cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SportsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sports_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        View all_contest_btn = holder.cardView.findViewById(R.id.sport_list);
        all_contest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, ContestListActivity.class);
                context.startActivity(intent);
            }
        });

//        View create_contest_btn = holder.cardView.findViewById(R.id.create_contest_btn);
//        create_contest_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Context context = view.getContext();
//                Intent intent = new Intent(context, CreateContestActivity.class);
//                context.startActivity(intent);
//            }
//        });

    }

    private void setText(TextView a,TextView b,TextView c,Contest tmpContest){
        a.setText(tmpContest.title);
        b.setText("$" + tmpContest.entryFee + " Entry $" + tmpContest.totalPrize +" Prizes " + tmpContest.entryCount + "/" + tmpContest.entryLimit + " Entries");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = df.parse(tmpContest.startTime);
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, h:mm a");
            c.setText(sdf.format(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}