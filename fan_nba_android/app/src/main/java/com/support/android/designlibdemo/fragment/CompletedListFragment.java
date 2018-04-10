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
import com.support.android.designlibdemo.adapter.CompletedRecyclerViewAdapter;
import com.support.android.designlibdemo.adapter.LiveRecyclerViewAdapter;
import com.support.android.designlibdemo.other.SportFan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Learn on 1/10/2016.
 */
public class CompletedListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView)inflater.inflate(R.layout.fragment_cheese_list, container, false);
        setupRecyclerView(rv);
        return rv;
    }
    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new CompletedRecyclerViewAdapter(getRandomSublist()));
    }

    private List<SportFan> getRandomSublist() {
        ArrayList<SportFan> list = new ArrayList<SportFan>();
        list.add(new SportFan());
        list.add(new SportFan());
        list.add(new SportFan());
        return list;
    }
}

