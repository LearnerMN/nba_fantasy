package com.support.android.designlibdemo.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Learn on 2/20/2016.
 */
public class Game {
    public String code;
    public int started;
    public String startTime;
    public String status;
    public String statusType;
    public Game(JSONObject jsonObject){
        try {
            this.code = jsonObject.getString("code");
            this.startTime = jsonObject.getString("startTime");
            this.status = jsonObject.getString("status");
            this.statusType = jsonObject.getString("statusType");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}