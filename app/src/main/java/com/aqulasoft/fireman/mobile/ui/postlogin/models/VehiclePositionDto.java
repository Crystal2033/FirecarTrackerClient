package com.aqulasoft.fireman.mobile.ui.postlogin.models;


import android.location.Location;

public class VehiclePositionDto {
    private float longitude;
    private float latitude;
    private String eventId;

    public VehiclePositionDto(Location location, String eventId) {
        setLatitude((float) location.getLatitude());
        setLongitude((float) location.getLongitude());
        setEventId(eventId);
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
