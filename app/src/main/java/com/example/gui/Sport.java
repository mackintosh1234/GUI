package com.example.gui;

import android.os.Parcel;
import android.os.Parcelable;

public class Sport implements Parcelable {
    private String name;

    public Sport() {
        name = "";
    }

    public Sport(String a) {
        this.name = a;
    }

    protected Sport(Parcel in) {
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Sport> CREATOR = new Creator<Sport>() {
        @Override
        public Sport createFromParcel(Parcel in) {
            return new Sport(in);
        }

        @Override
        public Sport[] newArray(int size) {
            return new Sport[size];
        }
    };

    public String get_name() {
        return this.name;
    }

    public void set_name(String a) {
        this.name = a;
    }

    public void print() {
        System.out.println(this.name);
    }

    //initiate methods in main class for runtime polymorphism
    public void setCity(String n) {}
    public String getCity() {return "";}
    public void setArena(String a) {}
    public String getArena() {return "";}
    public void setTeam(String a) {}
    public String getTeam() {return "";}
    public void setPosition(String a) {}
    public String getPosition() {return "";}
    public void setStats(float[] a) {}
    public float[] getStats() {float[] a = new float[0]; return a;}

}
