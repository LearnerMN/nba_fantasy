package com.support.android.designlibdemo.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Learn on 2/2/2016.
 */
public class CodeHolder {
    public String yCode;
    public String start_time;
    public String state;
    public List<Match> matches;
    public CodeHolder(JSONObject jsonObject){
        try {
            this.yCode = jsonObject.getString("yCode");
            this.start_time = jsonObject.getString("start_time");
            this.state = jsonObject.getString("state");

            this.matches = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray("nba_contest_match");
            for (int i=0; i<jsonArray.length(); i++){
                this.matches.add(new Match(jsonArray.getJSONObject(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        String res = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = df.parse(this.start_time);
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, h:mm a");
            res += sdf.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res + "(" + matches.size() + " NBA Games)";
    }
}
