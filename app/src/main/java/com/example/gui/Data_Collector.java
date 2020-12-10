package com.example.gui;

import android.os.Parcel;
import android.os.Parcelable;

public class Data_Collector implements Parcelable {
    //Node Subclass
    class Data_node{

        //Takes in a sports object as data
        //Links to a next node
        Sport data;
        Data_node next;
        int index;

        //Constructor method for new node
        Data_node(Sport a){
            data = a;
            next = null;
            index = 0;
        }

    }

    //start of list and size of list
    private Data_node front;
    private int count;

    //constructor method
    public Data_Collector() {
        front = null;
        count = 0;
    }

    protected Data_Collector(Parcel in) {
        count = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(count);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Data_Collector> CREATOR = new Creator<Data_Collector>() {
        @Override
        public Data_Collector createFromParcel(Parcel in) {
            return new Data_Collector(in);
        }

        @Override
        public Data_Collector[] newArray(int size) {
            return new Data_Collector[size];
        }
    };

    //return the front object in the list
    public Sport peek() {
        return this.front.data;
    }

    //insertion method
    public boolean insert(Sport n) {

        //boolean value to confirm insertion
        //create new node to insert
        boolean inserted = false;
        Data_node new_node = new Data_node(n);
        new_node.index = this.count + 1;

        //check if list is empty then insert accordingly
        if(this.front == null) {
            this.front = new_node;
            inserted = true;
        }else {
            new_node.next = this.front;
            this.front = new_node;
            inserted = true;
        }
        //update the count if inserted
        if(inserted) {
            this.count ++;
        }

        //return if inserted or not
        return inserted;
    }

    //returns the size of the list
    public int length() {
        return this.count;
    }

    //search the list for a data point matching the key
    public Sport search(String key) {

        key = key.toLowerCase();
        Data_node current = this.front;
        String name;
        //iterate through each node
        while(current != null) {
            name = current.data.get_name();
            name = name.toLowerCase();
            //check to see if key matches
            if(name.contains(key)) {
                return current.data;
            }
            //go to next node
            current = current.next;
        }
        //nothing found return null
        return null;
    }

    //displays the list as a string
    public String display() {
        String fullDisplay = "";
        Data_node current = this.front;
        //iterate through the list
        while(current != null) {
            //add each name to the string
            fullDisplay += current.data.get_name() +"\n";
            current = current.next;
        }
        //return the list string
        return fullDisplay;
    }

    public int getIndex(String key){
        key = key.toLowerCase();
        Data_node current = this.front;
        String name;
        //iterate through each node
        while(current != null) {
            name = current.data.get_name();
            name = name.toLowerCase();
            //check to see if key matches
            if(name.contains(key)) {
                return current.index;
            }
            //go to next node
            current = current.next;
        }
        return -1;
    }
}
