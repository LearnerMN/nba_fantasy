package com.support.android.designlibdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.support.android.designlibdemo.Model.Contest;
import com.support.android.designlibdemo.R;
import com.support.android.designlibdemo.adapter.ContestRecyclerViewAdapter;
import com.support.android.designlibdemo.adapter.SelectPlayerRecyclerViewAdapter;
import com.support.android.designlibdemo.adapter.SetLineupRecyclerViewAdapter;
import com.support.android.designlibdemo.businessModel.GTPlayer;
import com.support.android.designlibdemo.other.FanRestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Learn on 2/20/2016.
 */
public class SelectPlayerActivity extends AppCompatActivity {

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_player);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        String position = getIntent().getStringExtra("position");
        SelectPlayerRecyclerViewAdapter.getInstance().setFilter(position);
        SelectPlayerRecyclerViewAdapter.getInstance().sort();

        recyclerView.setAdapter(SelectPlayerRecyclerViewAdapter.getInstance());

        Log.e("????????????","position: " + position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SelectPlayerRecyclerViewAdapter.getInstance().flushFilter();
    }
}