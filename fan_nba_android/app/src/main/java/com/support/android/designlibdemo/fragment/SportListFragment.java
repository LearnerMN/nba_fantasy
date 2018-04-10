package com.support.android.designlibdemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.support.android.designlibdemo.R;
import com.support.android.designlibdemo.activity.CreateContestActivity;
import com.support.android.designlibdemo.adapter.SportsRecyclerViewAdapter;

/**
 * Created by Learn on 1/9/2016.
 */
public class SportListFragment extends Fragment {

    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View frag_lobby_list = inflater.inflate(R.layout.fragment_lobby_list, container, false);
        recyclerView = (RecyclerView)frag_lobby_list.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new SportsRecyclerViewAdapter());
        FloatingActionButton btn = (FloatingActionButton)frag_lobby_list.findViewById(R.id.create_contest_nba);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, CreateContestActivity.class);
                context.startActivity(intent);
            }
        });
        return frag_lobby_list;
    }
}

