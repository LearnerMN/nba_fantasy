package com.support.android.designlibdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.support.android.designlibdemo.R;
import com.support.android.designlibdemo.adapter.ContestGamesRecyclerViewAdapter;
import com.support.android.designlibdemo.businessModel.ContestMatchs;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Learn on 1/13/2016.
 */
public class ContestGamesListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView)inflater.inflate(R.layout.fragment_contest_games_list, container, false);
        setupRecyclerView(rv);
        return rv;
    }
    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        String contestMatchsString = getArguments().getString("contestMatchs");
        try {
            ContestMatchs contestMatchs = new ContestMatchs(new JSONObject(contestMatchsString));
            recyclerView.setAdapter(new ContestGamesRecyclerViewAdapter(contestMatchs.matchs));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
