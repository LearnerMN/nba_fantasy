package com.support.android.designlibdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.support.android.designlibdemo.Model.Match;
import com.support.android.designlibdemo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Learn on 2/2/2016.
 */
public class MatchRecyclerViewAdapter extends RecyclerView.Adapter<MatchRecyclerViewAdapter.ViewHolder> {
    private List<Match> mDataset;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View cardView;
        public ViewHolder(View cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

    public MatchRecyclerViewAdapter(List<Match> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MatchRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Match match = mDataset.get(position);

        if (match == null) return;
//        Log.e()
        TextView start_time_txt = (TextView)holder.cardView.findViewById(R.id.start_time_txt);
        TextView team_vs_txt = (TextView)holder.cardView.findViewById(R.id.team_vs_txt);

        ImageView home_team_img = (ImageView)holder.cardView.findViewById(R.id.home_team_img);
        ImageView away_team_img = (ImageView)holder.cardView.findViewById(R.id.away_team_img);


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = df.parse(match.startTime);
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, h:mm a");
            start_time_txt.setText(sdf.format(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        team_vs_txt.setText(match.homeTeam.abbr + " vs " + match.awayTeam.abbr);

        Glide.with(holder.cardView.getContext()).load(match.homeTeam.imageUrl).centerCrop().into(home_team_img);
        Glide.with(holder.cardView.getContext()).load(match.awayTeam.imageUrl).centerCrop().into(away_team_img);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}