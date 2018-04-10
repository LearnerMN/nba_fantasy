package com.support.android.designlibdemo.businessModel;

import com.support.android.designlibdemo.Model.Contest;
import com.support.android.designlibdemo.Model.Match;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Learn on 1/18/2016.
 */
public class ContestMatchs {
    public Contest contest;
    public List<Match> matchs;
    String contestJSONString;
    String matchsJSONString;
    public ContestMatchs(JSONObject jsonObject){
        this.contest = new Contest(jsonObject);
        contestJSONString = jsonObject.toString();
        try {
            this.matchs = new ArrayList<>();
            JSONArray matchs = jsonObject.getJSONArray("matchs");
            for (int i=0; i<matchs.length(); i++){
                this.matchs.add(new Match(matchs.getJSONObject(i)));
            }
            matchsJSONString = jsonObject.getJSONArray("matchs").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getContest(){
        return contestJSONString;
    }
    public String getMatch(){
        return matchsJSONString;
    }
}