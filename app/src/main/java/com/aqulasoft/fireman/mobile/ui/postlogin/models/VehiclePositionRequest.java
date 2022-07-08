package com.aqulasoft.fireman.mobile.ui.postlogin.models;

import java.util.ArrayList;

public class VehiclePositionRequest {
    private String vehicleId;
    private ArrayList<VehiclePositionDto> positions;

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public ArrayList<VehiclePositionDto> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<VehiclePositionDto> positions) {
        this.positions = positions;
    }
}
