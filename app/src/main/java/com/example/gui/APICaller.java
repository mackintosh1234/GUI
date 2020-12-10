package com.example.gui;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class APICaller extends AsyncTask<String, Void, Data_Collector[][]>  {
    //partial link variables for different API
    private String URLMain;
    private String URLTeam;
    private Context ctx;
    AlertDialog.Builder dialog;

    //blank constructor method
    public APICaller() {
        URLMain = "";
        URLTeam = "";
        ctx = null;
    }

    @Override
    protected void onPostExecute(Data_Collector[][] data_collectors) {
        super.onPostExecute(data_collectors);
        dialog = new AlertDialog.Builder(ctx);
        dialog.setTitle("Big W");
        dialog.setMessage("thank god");
        Intent intent = new Intent(ctx, MainMenu.class);
        intent.putExtra("NHL", data_collectors[0][0]);
        ctx.startActivity(intent);
    }

    @Override
    protected Data_Collector[][] doInBackground(String... strings) {
        Data_Collector[][] collector = fillTeam();
        return collector;
    }

    //constructor method
    public APICaller(String a, String b, Context ct) {
        URLMain = a;
        URLTeam = b;
        ctx = ct;
    }

    //fillTeam method fills a data_collector with team data and calls other fill methods
    public Data_Collector[][] fillTeam() {

        //2D array to be returned with all NHL data
        Data_Collector[][] nhl = new Data_Collector[1][40];
        //Holds player data
        Data_Collector players;
        //Holds team data
        Data_Collector teams = new Data_Collector();
        //Team object to be filled out
        Sport team = new Team();
        //connection
        HttpURLConnection con = establishConnection(URLMain + URLTeam);
        //string to read the input from the api
        String inputLine;
        //team stats variable
        float[] stats;
        //tracker variable to keep track of what team is current
        int i = 1;

        //checks to see if the connection is established
        if(con == null) {
            System.out.println("Failed");
            return null;
        }

        //attempts to read connection stream
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while((inputLine = in.readLine()) != null) {

                //checks for the wanted values in the api and creates a team with them
                if(inputLine.contains("name")) {
                    if(team.get_name() == "") {
                        team.set_name(inputLine.substring(inputLine.indexOf(":")+3, inputLine.length()-2));
                    }else if(team.getArena() == "") {
                        team.setArena(inputLine.substring(inputLine.indexOf(":")+3, inputLine.length()-2));
                    }
                }else if(inputLine.contains("city")) {
                    if(team.getCity() == "") {
                        team.setCity(inputLine.substring(inputLine.indexOf(":")+3, inputLine.length()-2));
                    }
                }else if(inputLine.contains("  }, {") || inputLine.contains(": [ {")) {
                    //a team has been made and now a new team needs to be created
                    team = new Team();
                    //fills current teams stats
                    stats = fillTeamStats(i);
                    //set stats
                    team.setStats(stats);
                    //fills the roster for the current team
                    players = fillPlayers(i);
                    //assigns the roster to its position in the nhl 2d array
                    nhl[0][i] = players;
                    //insert in data collector
                    teams.insert(team);
                    i++;
                }

            }

            in.close();
            con.disconnect();

        } catch (IOException e) {
            //for if there was a error reading
            e.printStackTrace();
            return null;
        }
        //insert the teams data collector into the nhl 2d array
        nhl[0][0] = teams;
        return nhl;
    }

    //fillPlayer method fills a data collector with player data
    public Data_Collector fillPlayers(int i) {

        Data_Collector players = new Data_Collector();
        Sport player = new Player();
        HttpURLConnection con = establishConnection(URLMain + URLTeam + i + "?expand=team.roster");
        boolean control = false;
        float[] stats;
        String inputLine, teamName = "";

        //check connection
        if(con == null) {
            System.out.println("Failed");
            return null;
        }

        //attempt to read api
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while((inputLine = in.readLine()) != null) {

                //get the specified values
                if(inputLine.contains("name") && teamName == "") {
                    teamName = inputLine.substring(inputLine.indexOf(":")+3, inputLine.length()-2);
                } else if(inputLine.contains("roster")) {
                    control = true;
                } else if(control){
                    if(player.getTeam() == "") {
                        player.setTeam(teamName);
                    }else if(inputLine.contains("fullName")) {
                        if(player.get_name() == "") {
                            player.set_name(inputLine.substring(inputLine.indexOf(":")+3, inputLine.length()-2));
                        }
                    }else if(inputLine.contains("link")) {
                        stats = fillPlayerStats(inputLine.substring(inputLine.indexOf(":")+3, inputLine.length()-1));
                        player.setStats(stats);
                    }else if(inputLine.contains("name")) {
                        if(player.getPosition() == "") {
                            player.setPosition(inputLine.substring(inputLine.indexOf(":")+3, inputLine.length()-2));
                        }
                    }else if(inputLine.contains("      }, {")){
                        player = new Player();
                        players.insert(player);
                    }
                }

            }

            in.close();
            con.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        //return player roster data collection
        return players;
    }

    //sub method to fill team
    public float[] fillTeamStats(int i) {
        //set array for stats
        float[] stats = new float[7];
        String inputLine, temp;
        HttpURLConnection con = establishConnection(URLMain + URLTeam + i + "/stats");

        //check connection
        if(con == null) {
            System.out.println("Failed");
            return null;
        }

        //attempt to read api
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while((inputLine = in.readLine()) != null) {

                //read and get the specific stats
                if(inputLine.contains("ptPctg")) {
                    temp = inputLine.substring(inputLine.indexOf(":")+3, inputLine.length()-2);
                    stats[0] = Float.parseFloat(temp);
                }else if(inputLine.contains("goalsPerGame")) {
                    temp = inputLine.substring(inputLine.indexOf(":")+3, inputLine.indexOf(","));
                    stats[1] = Float.parseFloat(temp);
                }else if(inputLine.contains("goalsAgainstPerGame")) {
                    temp = inputLine.substring(inputLine.indexOf(":")+3, inputLine.indexOf(","));
                    stats[2] = Float.parseFloat(temp);
                }else if(inputLine.contains("evGGARatio")) {
                    temp = inputLine.substring(inputLine.indexOf(":")+3, inputLine.indexOf(","));
                    stats[3] = Float.parseFloat(temp);
                }else if(inputLine.contains("faceOffWinPercentage")) {
                    temp = inputLine.substring(inputLine.indexOf(":")+3, inputLine.length()-2);
                    stats[4] = Float.parseFloat(temp);
                }else if(inputLine.contains("shootingPctg")) {
                    temp = inputLine.substring(inputLine.indexOf(":")+2, inputLine.indexOf(","));
                    stats[5] = Float.parseFloat(temp);
                }else if(inputLine.contains("savePctg")) {
                    temp = inputLine.substring(inputLine.indexOf(":")+2, inputLine.length());
                    stats[6] = Float.parseFloat(temp);
                    break;
                }

            }

            in.close();
            con.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        //return the stats
        return stats;
    }

    //sub method for fill player
    public float[] fillPlayerStats(String a) {

        //set stats array
        float[] stats = new float[2];
        String inputLine, temp;
        HttpURLConnection con = establishConnection(URLMain + a + "/stats?stats=statsSingleSeason&season=20192020");

        //check connection
        if(con == null) {
            System.out.println("Failed");
            return null;
        }

        //attempt to read api
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while((inputLine = in.readLine()) != null) {
                //get the specified stats for players
                if(inputLine.contains("shutouts")) {
                    //kill switch to avoid extra work
                    break;
                }else if(inputLine.contains("goals")) {
                    temp = inputLine.substring(inputLine.indexOf(":")+2, inputLine.length()-1);
                    stats[0] = Float.parseFloat(temp);
                }else if(inputLine.contains("assists")) {
                    temp = inputLine.substring(inputLine.indexOf(":")+2, inputLine.length()-1);
                    stats[1] = Float.parseFloat(temp);
                }

            }

            in.close();
            con.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        //return player stats array
        return stats;
    }

    //helper method for establishing connections
    public HttpURLConnection establishConnection(String link) {

        //attempt to open link, returns a connection
        try {
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            return con;
        }catch(IOException e) {
            e.printStackTrace();
        }
        //returns null if no connection is made
        return null;
    }


}
