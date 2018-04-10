package com.support.android.designlibdemo.adapter;

import android.support.v7.widget.RecyclerView;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Learn on 1/13/2016.
 */
public class ContestGamesRecyclerViewAdapter extends RecyclerView.Adapter<ContestGamesRecyclerViewAdapter.ViewHolder> {
    private List<Match> mDataset;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View cardView;
        public ViewHolder(View cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

    public ContestGamesRecyclerViewAdapter(List<Match> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContestGamesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contest_games_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Match match = mDataset.get(position);

        TextView start_time_txt = (TextView)holder.cardView.findViewById(R.id.start_time_txt);

        ImageView home_team_img = (ImageView)holder.cardView.findViewById(R.id.home_team_img);
        TextView home_team_name = (TextView)holder.cardView.findViewById(R.id.home_team_name);

        ImageView away_team_img = (ImageView)holder.cardView.findViewById(R.id.away_team_img);
        TextView away_team_name = (TextView)holder.cardView.findViewById(R.id.away_team_name);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = df.parse(match.startTime);
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, h:mm a");
            start_time_txt.setText(sdf.format(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        home_team_name.setText(match.homeTeam.teamName);
        away_team_name.setText(match.awayTeam.teamName);

        Glide.with(holder.cardView.getContext()).load(match.homeTeam.imageUrl).centerCrop().into(home_team_img);
        Glide.with(holder.cardView.getContext()).load(match.awayTeam.imageUrl).centerCrop().into(away_team_img);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}