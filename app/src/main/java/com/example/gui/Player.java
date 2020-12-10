package com.example.gui;

public class Player extends Sport{

    private String team;
    private String position;
    private float[] stats;

    //blank constructor class
    public Player() {
        super();
        this.team = "";
        this.position = "";
        this.stats = new float[1];
    }

    //constructor class
    public Player(String a, String b, String c, float[] d) {
        super(a);
        this.team = b;
        this.position = c;
        this.stats = d;
    }

    //set player team
    @Override
    public void setTeam(String a) {
        this.team = a;
    }

    //return players team
    @Override
    public String getTeam() {
        return this.team;
    }

    //set player position
    @Override
    public void setPosition(String a) {
        this.position = a;
    }

    //return player position
    @Override
    public String getPosition() {
        return this.position;
    }

    //set player stats
    @Override
    public void setStats(float[] a) {
        this.stats = a;
    }

    //return player stats
    @Override
    public float[] getStats() {
        return this.stats;
    }

}
