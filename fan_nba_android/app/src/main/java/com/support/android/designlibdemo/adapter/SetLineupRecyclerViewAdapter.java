package com.support.android.designlibdemo.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.support.android.designlibdemo.Model.Contest;
import com.support.android.designlibdemo.Model.EmptyPlayer;
import com.support.android.designlibdemo.Model.Match;
import com.support.android.designlibdemo.Model.Player;
import com.support.android.designlibdemo.R;
import com.support.android.designlibdemo.activity.SelectPlayerActivity;
import com.support.android.designlibdemo.businessModel.GTPlayer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Learn on 2/19/2016.
 */
public class SetLineupRecyclerViewAdapter extends RecyclerView.Adapter<SetLineupRecyclerViewAdapter.ViewHolder> {

    private static SetLineupRecyclerViewAdapter instance = null;

    public static SetLineupRecyclerViewAdapter getInstance(List myDataset,Contest contest,Activity activity) {
        if(instance == null) {
            instance = new SetLineupRecyclerViewAdapter(myDataset);
        }
        instance.contest = contest;
        instance.activity = activity;
        return instance;
    }

    public static SetLineupRecyclerViewAdapter getInstance() { return instance; }

    Activity activity;
    private List mDataset;
    public Contest contest;
    private final int EMPTY_PLAYER = 0, PLAYER = 1;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View cardView;
        public ViewHolder(View cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

    public SetLineupRecyclerViewAdapter(List myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SetLineupRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View v = null;
        switch (viewType){
            case EMPTY_PLAYER:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lineup_item_add, parent, false);
                break;
            case PLAYER:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lineup_item, parent, false);
                break;
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lineup_item_add, parent, false);
                break;
        }

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == EMPTY_PLAYER){
            EmptyPlayer player = (EmptyPlayer)(mDataset).get(position);

            TextView p_pos_txt = (TextView)holder.cardView.findViewById(R.id.p_pos_txt);
            TextView p_select_pos_txt = (TextView)holder.cardView.findViewById(R.id.p_select_pos_txt);
            ImageView p_add_img = (ImageView)holder.cardView.findViewById(R.id.p_add_img);

            holder.cardView.setTag(player);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EmptyPlayer emptyPlayer = (EmptyPlayer)v.getTag();
                    Intent intent = new Intent(v.getContext(), SelectPlayerActivity.class);
                    intent.putExtra("position",emptyPlayer.position);
                    v.getContext().startActivity(intent);
                }
            });

            p_pos_txt.setText(player.position);
            p_select_pos_txt.setText(posDec(player.position));

        }else if (getItemViewType(position) == PLAYER){
            GTPlayer gtPlayer = (GTPlayer)(mDataset).get(position);

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

        }else;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataset.get(position) instanceof EmptyPlayer) {
            return EMPTY_PLAYER;
        } else if (mDataset.get(position) instanceof GTPlayer) {
            return PLAYER;
        }
        return -1;
    }

    public static String posDec(String pos){
        switch (pos){
            case "PG": return "Point Guard";
            case "SG": return "Shooting Guard";
            case  "G": return "Guard";
            case "SF": return "Small Forward";
            case "PF": return "Power Forward";
            case  "F": return "Forward";
            case  "C": return "Center";
            case "UTIL": return "Util";
            default:
                return "";
        }
    }

    public void addPlayer(GTPlayer gtPlayer,String qPosition){
        int index = -1;
        for (Object player : mDataset){
            if (player instanceof EmptyPlayer){
                EmptyPlayer emptyPlayer = (EmptyPlayer)player;
                if (emptyPlayer.position.equalsIgnoreCase(qPosition)){
                    gtPlayer.player.primaryPosition = emptyPlayer.position;
                    index = mDataset.indexOf(player);
                    break;
                }
            }
        }
        if (index == -1)
            return;

        mDataset.remove(index);
        mDataset.add(index, gtPlayer);
        contest.salaryCap -= gtPlayer.player.salary;
        updateSalaryCap();
        this.notifyDataSetChanged();
    }

    private void updateSalaryCap(){
        TextView salary_cap_txt = (TextView)activity.findViewById(R.id.salary_cap_txt);

        if (contest.salaryCap < 0){
            Button submitBtn = (Button)activity.findViewById(R.id.set_lineup_btn);
            submitBtn.setEnabled(false);
            salary_cap_txt.setTextColor(Color.RED);
            salary_cap_txt.setText("-$" + String.valueOf(contest.salaryCap*-1));
        }else{
            if (enabledSubmit()){
                Button submitBtn = (Button)activity.findViewById(R.id.set_lineup_btn);
                submitBtn.setEnabled(true);
                Log.e("??????????", "submitBtn: true");
            }
            salary_cap_txt.setTextColor(Color.GREEN);
            salary_cap_txt.setText("$"+String.valueOf(contest.salaryCap));
        }
    }

    private boolean enabledSubmit(){
        int count = 0;
        for (Object player : mDataset){
            if (player instanceof GTPlayer){
                count++;
            }
        }
        Log.e("??????????","count: " + count);
        return count == mDataset.size();
    }
}