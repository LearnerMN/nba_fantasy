package com.support.android.designlibdemo.Model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by LearnerMN on 1/13/16.
 */
public class Team {
    public String code = "nba.t.27";
    public String name = "Washington";
    public String teamName = "Wizards";
    public String abbr = "WAS";
    public String location = "Washington";
    public String imageUrl = "https://s.yimg.com/xe/ipt/was_nonWhite.png";
    public Team(JSONObject jsonObject){
        try {
            this.code = jsonObject.getString("code");
            this.name = jsonObject.getString("name");
            this.teamName = jsonObject.getString("teamName");
            this.abbr = jsonObject.getString("abbr");
            this.location = jsonObject.getString("location");
            this.imageUrl = jsonObject.getString("imageUrl");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public Team(JSONObject jsonObject,boolean mini){
        try {
            this.code = jsonObject.getString("code");
            this.location = jsonObject.getString("location");
            this.name = jsonObject.getString("name");
            this.teamName = jsonObject.getString("teamName");
            this.abbr = jsonObject.getString("abbr");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
