package com.support.android.designlibdemo.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.support.android.designlibdemo.Model.EmptyPlayer;
import com.support.android.designlibdemo.R;
import com.support.android.designlibdemo.businessModel.GTPlayer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Learn on 2/20/2016.
 */
public class SelectPlayerRecyclerViewAdapter extends RecyclerView.Adapter<SelectPlayerRecyclerViewAdapter.ViewHolder> {

    private static SelectPlayerRecyclerViewAdapter instance = null;

    public static SelectPlayerRecyclerViewAdapter getInstance(List myDataset) {
        if(instance == null) {
            instance = new SelectPlayerRecyclerViewAdapter(myDataset);
        }else{
            instance.mDataset = myDataset;
            instance.allItem = new ArrayList<>();
            instance.allItem.addAll(myDataset);
            instance.notifyDataSetChanged();

        }
        return instance;
    }

    public static SelectPlayerRecyclerViewAdapter getInstance() { return instance; }

    public List mDataset;
    public List allItem;
    private String filterQuery;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View cardView;
        public ViewHolder(View cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

    public SelectPlayerRecyclerViewAdapter(List myDataset) {
        mDataset = myDataset;
        allItem = new ArrayList<>();
        allItem.addAll(mDataset);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SelectPlayerRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_player_add_item, parent, false);;
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final GTPlayer gtPlayer = (GTPlayer)(mDataset).get(position);

        TextView p_pos_txt = (TextView)holder.cardView.findViewById(R.id.p_pos_txt);
        ImageView p_pro_img = (ImageView)holder.cardView.findViewById(R.id.p_pro_img);
        TextView p_name_txt = (TextView)holder.cardView.findViewById(R.id.p_name_txt);
        TextView g_status_txt = (TextView)holder.cardView.findViewById(R.id.g_status_txt);
        TextView g_home_team_txt = (TextView)holder.cardView.findViewById(R.id.g_home_team_txt);
        TextView g_away_team_txt = (TextView)holder.cardView.findViewById(R.id.g_away_team_txt);
        TextView p_ffpg_txt = (TextView)holder.cardView.findViewById(R.id.p_ffpg_txt);
        TextView p_salary_txt = (TextView)holder.cardView.findViewById(R.id.p_salary_txt);

        p_pos_txt.setText(gtPlayer.player.primaryPosition + (gtPlayer.player.primaryPosition.length() == 1 ? " " : ""));

        Glide.with(holder.cardView.getContext()).load(gtPlayer.player.imageUrl).centerCrop().into(p_pro_img);
        p_name_txt.setText(gtPlayer.player.firstName + " " + gtPlayer.player.lastName);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = df.parse(gtPlayer.game.startTime);
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, h:mm a");
            g_status_txt.setText(sdf.format(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        g_home_team_txt.setText(gtPlayer.team.abbr);
        g_away_team_txt.setText(gtPlayer.game.status);
        p_ffpg_txt.setText(String.format("%.1f", gtPlayer.player.fantasyPointsPerGame) + " FFPG");

        p_salary_txt.setText("$" + String.valueOf(gtPlayer.player.salary));

        ImageView p_change_img = (ImageView)holder.cardView.findViewById(R.id.p_change_img);
        if (SetLineupRecyclerViewAdapter.getInstance().contest.salaryCap < gtPlayer.player.salary){
            Log.e("?????????????","salaryCap: " + SetLineupRecyclerViewAdapter.getInstance().contest.salaryCap + " salary: " + gtPlayer.player.salary);
            p_salary_txt.setTextColor(Color.RED);
        }else{
            p_salary_txt.setTextColor(Color.GREEN);
        }

        p_change_img.setTag(gtPlayer);
        p_change_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GTPlayer gtPlayer1 = (GTPlayer)v.getTag();
                SetLineupRecyclerViewAdapter.getInstance().addPlayer(gtPlayer1,filterQuery);

                allItem.remove(allItem.indexOf(gtPlayer1));

                Activity activity = (Activity)v.getContext();
                activity.finish();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void sort() {
        Collections.sort(mDataset,new ComparatorGTPlayer());
        this.notifyDataSetChanged();
    }

    public void flushFilter(){
        mDataset.clear();
        mDataset.addAll(allItem);
    }
    public void setFilter(String query){
        filterQuery = query.toLowerCase();
        query = filterQuery+"";
        if (query.equalsIgnoreCase("util"))
            query = "";
        final List filteredModelList = new ArrayList<>();
        for (Object model : mDataset) {
            final String text = ((GTPlayer)model).player.primaryPosition.toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        mDataset = filteredModelList;
        this.notifyDataSetChanged();
    }

}

class ComparatorGTPlayer implements Comparator<GTPlayer> {
    public int compare(GTPlayer a, GTPlayer b) {
        if (a.player.salary < b.player.salary){
            return 1;
        }else if (a.player.salary > b.player.salary){
            return -1;
        }else{
            return 0;
        }
    }
}