package com.aqulasoft.fireman.mobile.base.repository.tracker;

import com.aqulasoft.fireman.mobile.ui.postlogin.models.VehiclePositionRequest;
import com.aqulasoft.fireman.mobile.ui.postlogin.models.VehicleStatRequest;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TrackerApi {
////////////////////////////////////////////////////////////////////////////////////////////////////
//                   Tracker api
////////////////////////////////////////////////////////////////////////////////////////////////////
    @POST("/api/tracker")
    Observable<VehiclePositionRequest> addLocationsPack(@Body VehiclePositionRequest request);

    @POST("/api/vehicle")
    Observable<VehicleStatRequest> addVehicle(@Body VehicleStatRequest vehicleStatRequest);
}

