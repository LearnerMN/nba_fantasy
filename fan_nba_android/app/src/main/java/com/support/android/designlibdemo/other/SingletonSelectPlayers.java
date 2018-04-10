package com.support.android.designlibdemo.other;

import com.support.android.designlibdemo.Model.EmptyPlayer;

import java.util.ArrayList;

/**
 * Created by Learn on 2/20/2016.
 */
public class SingletonSelectPlayers extends ArrayList {
    private static SingletonSelectPlayers instance = null;
    private SingletonSelectPlayers() {
        clearPlayers();
    }
    public static SingletonSelectPlayers getInstance() {
        if(instance == null) {
            instance = new SingletonSelectPlayers();
        }
        return instance;
    }

    public void clearPlayers(){
        this.clear();
        this.add(new EmptyPlayer("PG"));
        this.add(new EmptyPlayer("SG"));
        this.add(new EmptyPlayer("G"));
        this.add(new EmptyPlayer("SF"));
        this.add(new EmptyPlayer("PF"));
        this.add(new EmptyPlayer("F"));
        this.add(new EmptyPlayer("C"));
        this.add(new EmptyPlayer("UTIL"));
    }
}
