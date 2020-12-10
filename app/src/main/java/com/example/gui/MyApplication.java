package com.example.gui;

import android.app.Application;

public class MyApplication extends Application {
    private Data_Collector[][] collector;
    private float[] odds;
    private Sport teamA, teamB;

    public Data_Collector[][] getDataCollector(){
        return collector;
    }

    public void setDataCollector(Data_Collector[][] a){
        collector = a;
    }

    public float[] getOdds(){return odds;}

    public void setOdds(float[] a){odds = a;}

    public Sport getTeamA(){
        return teamA;
    }

    public void setTeamA(Sport a){
        teamA = a;
    }

    public Sport getTeamB(){
        return teamB;
    }

    public void setTeamB(Sport a){
        teamB = a;
    }
}
