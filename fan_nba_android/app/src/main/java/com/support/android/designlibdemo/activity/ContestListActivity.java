package com.support.android.designlibdemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.support.android.designlibdemo.R;
import com.support.android.designlibdemo.adapter.ContestRecyclerViewAdapter;
import com.support.android.designlibdemo.Model.Contest;
import com.support.android.designlibdemo.other.FanRestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by LearnerMN on 1/11/16.
 */
public class ContestListActivity extends AppCompatActivity {


    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        try {
            getNBAContests();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getNBAContests() throws JSONException {
        FanRestClient.get("contest/list", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                recyclerView.setAdapter(new ContestRecyclerViewAdapter(getContestList(response)));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("??????????????", "statusCode: " + statusCode);
                Log.e("??????????????","throwable: " + throwable.toString());
            }
        });
    }

    private List<Contest> getContestList(JSONObject jsonObject) {
        ArrayList<Contest> list = new ArrayList<Contest>();
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray("contests");
            for (int i=0; i<jsonArray.length(); i++){
                list.add(new Contest(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}