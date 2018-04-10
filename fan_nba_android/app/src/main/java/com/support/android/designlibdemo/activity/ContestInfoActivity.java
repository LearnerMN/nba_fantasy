package com.support.android.designlibdemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.support.android.designlibdemo.R;
import com.support.android.designlibdemo.businessModel.ContestMatchs;
import com.support.android.designlibdemo.fragment.CheeseListFragment;
import com.support.android.designlibdemo.fragment.ContestEntriesListFragment;
import com.support.android.designlibdemo.fragment.ContestGamesListFragment;
import com.support.android.designlibdemo.fragment.ContestInfoListFragment;
import com.support.android.designlibdemo.other.FanRestClient;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Learn on 1/13/2016.
 */
public class ContestInfoActivity extends AppCompatActivity {

    int contest_id;
    ContestMatchs contestMatchs;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_info);

        contest_id = getIntent().getIntExtra("contest_id", 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        loadContestEntry();
    }

    void completedLoadRequest(){
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        MainActivity.Adapter adapter = new MainActivity.Adapter(getSupportFragmentManager());

        android.support.v4.app.Fragment fragmentContestInfo = new ContestInfoListFragment();
        Bundle bundleInfo = new Bundle();
        bundleInfo.putString("contest",contestMatchs.getContest());
        fragmentContestInfo.setArguments(bundleInfo);
        adapter.addFragment(fragmentContestInfo, "Info");

        android.support.v4.app.Fragment fragmentContestGames = new ContestGamesListFragment();
        Bundle bundleGames = new Bundle();
        bundleGames.putString("contestMatchs", contestMatchs.getContest());
        fragmentContestGames.setArguments(bundleGames);

        adapter.addFragment(fragmentContestGames, "Games");
        adapter.addFragment(new CheeseListFragment(), "Entries");
        viewPager.setAdapter(adapter);

    }
    private void loadContestEntry(){
        FanRestClient.get("contest/" + contest_id, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject contests = response.getJSONObject("contest");
                    contestMatchs = new ContestMatchs(contests);
                    completedLoadRequest();
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
}
