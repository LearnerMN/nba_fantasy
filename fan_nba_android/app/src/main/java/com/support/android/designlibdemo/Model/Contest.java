package com.support.android.designlibdemo.Model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Learn on 1/12/2016.
 */
public class Contest {
    public int id = 1;
    public int yid =  991042;
    public String title = "NBA $17K Tournament [$2K to 1st]";
    String type = "league";
    String scope = "guaranteed";
    boolean guaranteed = true;
    String state = "upcoming";
    int seriesId = 851;
    String sportCode = "nba";
    public int salaryCap = 200;
    public int entryCount = 998;
    public int entryLimit = 3864;
    boolean multipleEntry = true;
    int multipleEntryLimit = 50;
    public int entryFee = 5;
    public int totalPrize = 17000;
    public String startTime = "0000-00-00 00:00:00";

    public Contest(JSONObject jsonObject){
        try {
            this.id =  jsonObject.getInt("id");
            this.yid =  Integer.parseInt(jsonObject.getString("code"));
            this.title = jsonObject.getString("title");
            this.type = jsonObject.getString("type");
            this.scope = jsonObject.getString("scope");
            this.guaranteed = jsonObject.getInt("guaranteed") == 1 ? true : false;
            this.state = jsonObject.getString("state");
            this.sportCode = jsonObject.getString("sportCode");
            this.salaryCap = jsonObject.getInt("salaryCap");
            this.entryCount = jsonObject.getInt("entryCount");
            this.entryLimit = jsonObject.getInt("entryLimit");
            this.multipleEntry = jsonObject.getInt("multipleEntry") == 1 ? true : false;
            this.multipleEntryLimit = jsonObject.getInt("multipleEntryLimit");
            this.entryFee = jsonObject.getInt("entryFee");
            this.totalPrize = jsonObject.getInt("totalPrize");
            this.startTime = jsonObject.getString("startTime");

        } catch (JSONException e) {
            Log.e("???????????: ","json error -> Contest");
            e.printStackTrace();
        }
    }
}
