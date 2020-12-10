package com.example.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {

    TextView teamA;
    private static final String TAG = "MainMenu";
    TextView teamB;
    Button getOdds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "hit");
        setContentView(R.layout.activity_main_menu);
        getOdds = findViewById(R.id.getOdds);
        teamA = findViewById(R.id.teamA);
        teamB = findViewById(R.id.teamB);

        getOdds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data_Collector teams = null;
                if(getIntent().hasExtra("NHL")){
                    teams = getIntent().getParcelableExtra("NHL");
                    Log.d(TAG, "onCreate: " + teams.display());
                    Sport teamOne, teamTwo;
                    String aTeam, bTeam;
                    aTeam = teamA.getText().toString();
                    bTeam = teamB.getText().toString();
                    teamOne = teams.search(aTeam);
                    teamTwo = teams.search(bTeam);
                    if(teamOne != null && teamTwo != null){
                        float[] weightMatrix = {0.803f, -0.076f, -0.112f, 0.027f, 0.048f, 0.103f, 0.566f};
                        Odds cal = new Odds(weightMatrix);
                        float[] teamOneStats = teamOne.getStats();
                        float[] teamTwostats = teamTwo.getStats();
                        float[] odds = cal.getOdds(teamOneStats, teamTwostats);
                        Intent intent = (new Intent(MainMenu.this, ViewOddsScreen.class));
                        intent.putExtra("odds", odds);
                        intent.putExtra("teamA", teamOne);
                        intent.putExtra("teamB", teamTwo);
                        startActivity(intent);
                    }
                }else{
                    teamB.setText("failed");
                }



            }
        });

    }
}