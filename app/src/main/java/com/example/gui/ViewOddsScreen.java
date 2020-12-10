package com.example.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ViewOddsScreen extends AppCompatActivity {
    TextView teamAname;
    TextView teamBname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_odds_screen);
        Sport teamA = null, teamB = null;
        if(getIntent().hasExtra("teamA")){
            teamA = getIntent().getParcelableExtra("teamA");
        }
        if(getIntent().hasExtra("teamB")){
            teamB = getIntent().getParcelableExtra("teamB");
        }

        teamAname = findViewById(R.id.aaaa);
        teamBname = findViewById(R.id.bbbb);
        teamAname.setText(teamA.get_name());
        teamBname.setText(teamB.get_name());
    }
}