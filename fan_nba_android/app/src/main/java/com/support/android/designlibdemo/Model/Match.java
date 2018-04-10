package com.support.android.designlibdemo.Model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Learn on 1/18/2016.
 */
public class Match {
    JSONObject jsonObject;
    public String code = "nba.g.1571447";
    public String yCode = "1004802";
    public String sportCode = "nba";
    public String homeTeamCode = "nba.t.27";
    public String awayTeamCode = "nba.t.15";
    public String statusType = "PREGAME";
    public String status = "7:00 pm ET";
    public boolean lineupAvailable = false;
    public String startTime = "";
    public String location = "Washington";
    public String weather = "sunny";
    public Team homeTeam;
    public Team awayTeam;
    public Match(JSONObject jsonObject){
        try {
            this.jsonObject = jsonObject;
            this.code = jsonObject.getString("code");
            this.yCode = jsonObject.getString("yCode");
            this.sportCode = jsonObject.getString("sportCode");
            this.homeTeamCode = jsonObject.getString("homeTeamCode");
            this.awayTeamCode = jsonObject.getString("awayTeamCode");
            this.statusType = jsonObject.getString("statusType");
            this.status = jsonObject.getString("status");
            this.lineupAvailable = jsonObject.getInt("lineupAvailable") == 1 ? true : false;
            this.startTime = jsonObject.getString("startTime");
            this.location = jsonObject.getString("location");
            this.weather = jsonObject.getString("weather");


            this.homeTeam = new Team(jsonObject.getJSONObject("home_team"));
            this.awayTeam = new Team(jsonObject.getJSONObject("away_team"));

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("??????????????","errrrrrrrrrrrrrrorrrrrrrrrrrrrrr");
        }
    }

    @Override
    public String toString() {
        return jsonObject.toString();
    }
}
