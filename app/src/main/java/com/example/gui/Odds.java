package com.example.gui;

public class Odds {
    //matrix of weights for stats
    private float[] weightMatrix;

    //constructor method
    public Odds(float[] a) {

        //sets the weighted values for the odds
        this.weightMatrix = a;

    }

    //getOdds method takes in two team stat float arrays
    public float[] getOdds(float[] teamA, float[] teamB) {
        //variable to return results
        float[] odds = new float[2];
        //ratio variables for calculations
        float teamAratio = 0, teamBratio = 0, totalRatio;

        //matrix multiplication
        for(int i=0; i<weightMatrix.length;i++) {
            teamAratio += teamA[i] * weightMatrix[i];
            teamBratio += teamB[i] * weightMatrix[i];
        }
        //calculate odds
        totalRatio = teamAratio + teamBratio;
        odds[0] = (teamAratio/totalRatio) * 100;
        odds[1] = (teamBratio/totalRatio) * 100;

        //returns odds for viewing
        return odds;
    }
}
