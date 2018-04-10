package com.support.android.designlibdemo.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.support.android.designlibdemo.Model.Contest;
import com.support.android.designlibdemo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Learn on 1/13/2016.
 */
public class ContestInfoListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment_contest_info_list = inflater.inflate(R.layout.fragment_contest_info_list, container, false);
        String contestString = getArguments().getString("contest");
        try {
            Contest contest = new Contest(new JSONObject(contestString));
            TextView contest_entry_fee = (TextView)fragment_contest_info_list.findViewById(R.id.contest_entry_fee);
            TextView contest_max_entries = (TextView)fragment_contest_info_list.findViewById(R.id.contest_max_entries);
            @SuppressLint("WrongViewCast") TextView contest_start_time = (TextView)fragment_contest_info_list.findViewById(R.id.contest_start_time);
            TextView contest_id = (TextView)fragment_contest_info_list.findViewById(R.id.contest_id);


            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = null;
            try {
                d = df.parse(contest.startTime);
                SimpleDateFormat sdf = new SimpleDateFormat("EE, h:mm a");
                contest_start_time.setText(sdf.format(d));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            contest_entry_fee.setText("$" + String.valueOf(contest.entryFee));
            contest_max_entries.setText(String.valueOf(contest.entryLimit));
            contest_id.setText(String.valueOf(contest.yid));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return fragment_contest_info_list;
    }

}
