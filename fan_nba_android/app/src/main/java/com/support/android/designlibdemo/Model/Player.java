package com.support.android.designlibdemo.Model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Learn on 2/19/2016.
 */
public class Player {
    public String code;
    public String firstName;
    public String lastName;
    public String sportCode;
    public int number;
    public int jerseyNumber;
    public String status;
    public String imageUrl;
    public String largeImageUrl;
    public int team_id;
    public int salary;
    public int originalSalary;
    public double projectedPoints;
    public String starting;
    public String primaryPosition;
    public String eligiblePositions;
    public double fantasyPointsPerGame;

    public Player(JSONObject jsonObject){
        try {
            this.code =  jsonObject.getString("code");
            this.firstName =  jsonObject.getString("firstName");
            this.lastName = jsonObject.getString("lastName");
            this.sportCode = jsonObject.getString("sportCode");
            this.number = jsonObject.getInt("number");
            this.jerseyNumber = jsonObject.getInt("jerseyNumber");
            this.status = jsonObject.getString("status");
            this.imageUrl = jsonObject.getString("imageUrl");
            this.largeImageUrl = jsonObject.getString("largeImageUrl");
//            this.team_id = jsonObject.getInt("team_id");
            this.salary = jsonObject.getInt("salary");
            this.originalSalary = jsonObject.getInt("originalSalary");
            this.projectedPoints = jsonObject.getDouble("projectedPoints");
            this.starting = jsonObject.getString("starting");
            this.primaryPosition = jsonObject.getString("primaryPosition");
            this.eligiblePositions = jsonObject.getString("eligiblePositions");
            this.fantasyPointsPerGame = jsonObject.getDouble("fantasyPointsPerGame");
        } catch (JSONException e) {
            Log.e("???????????: ", "json error -> Player" + e.toString());
            e.printStackTrace();
        }
    }
    public Player(){}
}
