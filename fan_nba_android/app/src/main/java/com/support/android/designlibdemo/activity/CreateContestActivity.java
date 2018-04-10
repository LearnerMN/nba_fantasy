package com.support.android.designlibdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.support.android.designlibdemo.Model.CodeHolder;
import com.support.android.designlibdemo.R;
import com.support.android.designlibdemo.adapter.MatchRecyclerViewAdapter;
import com.support.android.designlibdemo.other.FanRestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class CreateContestActivity extends AppCompatActivity {


    RecyclerView match_recycler_view;

    String[] total_entrants_array;
    String[] entry_fee_array;

    Spinner spinnerOsversions;
    Spinner total_entrants_spin;
    Spinner entry_fee_spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contest);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        spinnerOsversions = (Spinner) findViewById(R.id.osversions);

        total_entrants_spin = (Spinner) findViewById(R.id.total_entrants_spin);
        entry_fee_spin = (Spinner) findViewById(R.id.entry_fee_spin);

        total_entrants_array = getResources().getStringArray(R.array.total_entrants);
        entry_fee_array = getResources().getStringArray(R.array.entry_fee);

        setAdapter(total_entrants_spin,total_entrants_array);
        setAdapter(entry_fee_spin,entry_fee_array);



        match_recycler_view = (RecyclerView) findViewById(R.id.match_recycler_view);
        match_recycler_view.setLayoutManager(new LinearLayoutManager(match_recycler_view.getContext()));

        spinnerOsversions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerOsversions.setSelection(position);
                CodeHolder codeHolder = (CodeHolder) spinnerOsversions.getSelectedItem();
                match_recycler_view.setAdapter(new MatchRecyclerViewAdapter(codeHolder.matches));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        try {
            getMatches();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onCreateContest(View v){
        CodeHolder codeHolder = (CodeHolder) spinnerOsversions.getSelectedItem();
        String entrants = (String)total_entrants_spin.getSelectedItem();
        String entry_fee = (String)entry_fee_spin.getSelectedItem();

        if (codeHolder == null || entrants == null || entry_fee == null){
            return;
        }

        String yCode = codeHolder.yCode;
        String sportCode = "nba";
        String title = "NBA " + entry_fee + " " + entrants + "-Team Winner Takes All";
        String scope = "public";
        int entry_fee_i = entryFeeToInt(entry_fee);
        int entryLimit = Integer.parseInt(entrants);
        int totalPrize = (int)(entry_fee_i*entryLimit * 0.9);
        String start_time = codeHolder.start_time;

        RequestParams requestParams = new RequestParams();
        requestParams.add("yCode",yCode);
        requestParams.add("sportCode",sportCode);
        requestParams.add("title",title);
        requestParams.add("scope",scope);
        requestParams.add("entryFee",String.valueOf(entry_fee_i));
        requestParams.add("entryLimit",entrants);
        requestParams.add("totalPrize",String.valueOf(totalPrize));
        requestParams.add("startTime",start_time);

        try {
            createContestSubmit(requestParams);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void createContestSubmit(RequestParams requestParams) throws JSONException {
        FanRestClient.post("contest/create", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("??????????","onSuccess response: " + response.toString());
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("??????????????", "statusCode: " + statusCode);
                Log.e("??????????????", "throwable: " + throwable.toString());
            }
        });
    }

    private void setAdapter(Spinner spinner,String array[]){
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, array);
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter_state);
    }

    public void completedRequest(JSONObject jsonObject){
        Log.e("????????????","completedRequest");
        ArrayAdapter<CodeHolder> adapter_state = new ArrayAdapter<CodeHolder>(this,android.R.layout.simple_spinner_item, getContestList(jsonObject));
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOsversions.setAdapter(adapter_state);
    }

    public void getMatches() throws JSONException {
        FanRestClient.get("matches", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                completedRequest(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("??????????????", "statusCode: " + statusCode);
                Log.e("??????????????", "throwable: " + throwable.toString());
            }
        });
    }

    private List<CodeHolder> getContestList(JSONObject jsonObject) {
        ArrayList<CodeHolder> list = new ArrayList<CodeHolder>();
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray("matches");
            for (int i=0; i<jsonArray.length(); i++){
                list.add(new CodeHolder(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("???????????????","CodeHolder count: " + list.size());
        return list;
    }

    private int entryFeeToInt(String s){
        s = removeChar(s, '$');
        if (s.equalsIgnoreCase("free"))
            return 0;
        return (int)Double.parseDouble(s);
    }

    private String removeChar(String s, char c) {
        StringBuffer buf = new StringBuffer(s.length());
        buf.setLength(s.length());
        int current = 0;
        for (int i=0; i<s.length(); i++){
            char cur = s.charAt(i);
            if(cur != c) buf.setCharAt(current++, cur);
        }
        return buf.toString();
    }
}
