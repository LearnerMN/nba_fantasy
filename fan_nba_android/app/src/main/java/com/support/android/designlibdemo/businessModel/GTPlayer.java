package com.support.android.designlibdemo.businessModel;

import com.support.android.designlibdemo.Model.Game;
import com.support.android.designlibdemo.Model.Player;
import com.support.android.designlibdemo.Model.Team;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Learn on 2/20/2016.
 */
public class GTPlayer {
    public Player player;
    public Game game;
    public Team team;
    public GTPlayer(JSONObject jsonObject){
        this.player = new Player(jsonObject);
        try {
            this.team = new Team(jsonObject.getJSONObject("team"));
            this.game = new Game(jsonObject.getJSONObject("game"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
