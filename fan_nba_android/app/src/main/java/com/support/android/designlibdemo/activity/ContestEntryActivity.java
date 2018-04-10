package com.support.android.designlibdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.support.android.designlibdemo.R;
import com.support.android.designlibdemo.businessModel.ContestMatchs;
import com.support.android.designlibdemo.other.FanRestClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Learn on 1/13/2016.
 */
public class ContestEntryActivity extends AppCompatActivity {

    int contest_id;
    CollapsingToolbarLayout collapsingToolbar;

    TextView start_time_txt;
    TextView entry_fee_txt;
    TextView prizes_txt;
    TextView entries_txt;

    CountDownTimer startDownTimer;
    ContestMatchs contestMatchs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        contest_id = intent.getIntExtra("contest_id", 0);
        String contest_title = intent.getStringExtra("contest_title");


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");

        start_time_txt = (TextView)collapsingToolbar.findViewById(R.id.start_time_txt);

        entry_fee_txt = (TextView)collapsingToolbar.findViewById(R.id.entry_fee_txt);
        prizes_txt = (TextView)collapsingToolbar.findViewById(R.id.prizes_txt);
        entries_txt = (TextView)collapsingToolbar.findViewById(R.id.entries_txt);

        TextView text = new TextView(this);
        text.setText(contest_title);
        text.setTextAppearance(this, android.R.style.TextAppearance_Material_Widget_ActionBar_Title);
        text.setTextSize(14);
        toolbar.addView(text);

        loadBackdrop();

        loadContestEntry();

    }

    @Override
    protected void onDestroy() {
        if (startDownTimer != null)
            startDownTimer.cancel();
        super.onDestroy();
    }


    public void onEnterContest(View v){
        Intent intent = new Intent(this,SetLineupActivity.class);
        intent.putExtra("contest",contestMatchs.getContest());
        startActivity(intent);
    }
    public void completedContestEntryRequest(){
        if (startDownTimer != null)
            startDownTimer.cancel();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date d = df.parse(contestMatchs.contest.startTime);
            Log.e("????????????: ","contestMatchs: " + contestMatchs.contest.startTime);
            Long time = d.getTime();
            startDownTimer = new CountDownTimer((time - System.currentTimeMillis()), 1000) {
                public void onTick(long millisUntilFinished) {
                    long m = millisUntilFinished/1000;
                    int sec = (int)(m%60);
                    m /= 60;
                    int min = (int)(m%60);
                    m /= 60;
                    int hour = (int)m;
                    start_time_txt.setText((hour < 10 ? "0" : "") +  hour + ":" + (min < 10 ? "0" : "") +  min + ":" + (sec < 10 ? "0" : "") +  sec);
                }
                public void onFinish() {
                    start_time_txt.setText("00:00:00");
                }
            };
            startDownTimer.start();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        entry_fee_txt.setText("$" + contestMatchs.contest.entryFee);
        prizes_txt.setText("$" + contestMatchs.contest.totalPrize);
        entries_txt.setText(contestMatchs.contest.entryCount + "/" + contestMatchs.contest.entryLimit);
    }
    private void loadContestEntry(){
        FanRestClient.get("contest/" + contest_id, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject contests = response.getJSONObject("contest");
                    contestMatchs = new ContestMatchs(contests);
                    completedContestEntryRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("??????????????", "statusCode: " + statusCode);
                Log.e("??????????????", "throwable: " + throwable.toString());
            }
        });
    }
    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(R.drawable.nba_12_bg).centerCrop().into(imageView);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.contest_entry_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.contest_info:
                Intent intent = new Intent(this,ContestInfoActivity.class);
                intent.putExtra("contest_id",contest_id);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
