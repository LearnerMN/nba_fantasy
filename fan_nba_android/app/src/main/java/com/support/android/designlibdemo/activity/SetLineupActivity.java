package com.support.android.designlibdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.support.android.designlibdemo.Model.Contest;
import com.support.android.designlibdemo.Model.EmptyPlayer;
import com.support.android.designlibdemo.Model.Player;
import com.support.android.designlibdemo.R;
import com.support.android.designlibdemo.adapter.SelectPlayerRecyclerViewAdapter;
import com.support.android.designlibdemo.adapter.SetLineupRecyclerViewAdapter;
import com.support.android.designlibdemo.businessModel.GTPlayer;
import com.support.android.designlibdemo.other.FanRestClient;
import com.support.android.designlibdemo.other.SingletonSelectPlayers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Learn on 2/19/2016.
 */
public class SetLineupActivity extends AppCompatActivity {

    Contest contest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_set_lineup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            contest = new Contest(new JSONObject(getIntent().getStringExtra("contest")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("?????????????","json contest_id: " + contest.id);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        SingletonSelectPlayers.getInstance().clearPlayers();
        recyclerView.setAdapter(SetLineupRecyclerViewAdapter.getInstance(SingletonSelectPlayers.getInstance(),contest,this));

        try {
            getContestPlayers(String.valueOf(contest.id));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getContestPlayers(String contest_id) throws JSONException {
        FanRestClient.get("contest/" + contest_id + "/players", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                SelectPlayerRecyclerViewAdapter.getInstance(getContestPlayersList(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("??????????????", "statusCode: " + statusCode);
                Log.e("??????????????", "throwable: " + throwable.toString());
            }
        });
    }

    private List getContestPlayersList(JSONObject jsonObject) {
        ArrayList list = new ArrayList();
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray("players");
            for (int i=0; i<jsonArray.length(); i++){
                list.add(new GTPlayer(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
