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
import com.support.android.designlibdemo.adapter.SportsRecyclerViewAdapter;
import com.support.android.designlibdemo.other.SportFan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Learn on 1/13/2016.
 */
public class ContestEntriesListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View frag_lobby_list = inflater.inflate(R.layout.fragment_lobby_list, container, false);
        RecyclerView rv = (RecyclerView)frag_lobby_list.findViewById(R.id.recyclerview);
        setupRecyclerView(rv);
        return frag_lobby_list;
    }
    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
//        recyclerView.setAdapter(new SportsRecyclerViewAdapter(getRandomSublist()));
    }

    private List<SportFan> getRandomSublist() {
        ArrayList<SportFan> list = new ArrayList<SportFan>();
        list.add(new SportFan());
        return list;
    }

}

