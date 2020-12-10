package com.example.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String mainURL = "https://statsapi.web.nhl.com";
    private String teamURL = "/api/v1/teams/";
    TextView teamAname;
    TextView teamBname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        APICaller caller = new APICaller(mainURL, teamURL,MainActivity.this);
        caller.execute();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClickMainMenu(View view) {
        startActivity(new Intent(this,MainMenu.class));
    }

    public void onClickViewPlayer(View view) {
        startActivity(new Intent(this,ViewPlayer.class));
    }

    public void onClickViewTeam(View view) {
        startActivity(new Intent(this,ViewTeam.class));
    }

    public void onClickViewOddsScreen(View view) {
        startActivity(new Intent(this,ViewOddsScreen.class));
    }
}