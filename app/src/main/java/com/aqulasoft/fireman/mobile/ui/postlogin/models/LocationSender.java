package com.aqulasoft.fireman.mobile.ui.postlogin.models;

import android.location.Location;

import java.util.ArrayList;

public class LocationSender {
    ArrayList<Location> locations = new ArrayList<>();

    public void savePosition(Location location){
        locations.add(location);
        System.out.println(location.getLatitude());
        System.out.println(location.getLongitude());
        System.out.println("\n");
        if (locations.size() > 5){
            sendRequest();
        }
    }

    private void sendRequest(){

        System.out.println("Send in IntelliJ IDEA");
        locations.clear();
        //TODO: send request on backend
    }

}
