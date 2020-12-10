package com.example.gui;

public class Team extends Sport{
    private String city;
    private String arena;
    private float[] stats;

    //blank constructor method
    public Team() {
        super();
        city = "";
        arena = "";
        stats = new float[16];
    }

    //constructor method
    public Team(String name, String city, String arena,  float[] stats) {
        super(name);
        this.city = city;
        this.arena = arena;
        this.stats = stats;
    }

    //set city name for team
    @Override
    public void setCity(String a) {
        this.city = a;
    }

    //return name of team city
    @Override
    public String getCity() {
        return this.city;
    }

    //set arena for team
    @Override
    public void setArena(String a) {
        this.arena = a;
    }

    //return name of team arena
    @Override
    public String getArena() {
        return this.arena;
    }

    //set stats of team
    public void setStats(float[] a) {
        this.stats = a;
    }

    //return teams stats
    public float[] getStats() {
        return this.stats;
    }
}
