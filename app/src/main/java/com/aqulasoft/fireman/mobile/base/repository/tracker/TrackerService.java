package com.aqulasoft.fireman.mobile.base.repository.tracker;

import static com.aqulasoft.fireman.mobile.base.utils.FiremanSettings.TRACKER_HOSTNAME;

import androidx.annotation.NonNull;

import com.aqulasoft.fireman.mobile.ui.postlogin.models.VehiclePositionDto;
import com.aqulasoft.fireman.mobile.ui.postlogin.models.VehiclePositionRequest;
import com.aqulasoft.fireman.mobile.ui.postlogin.models.VehicleStatRequest;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrackerService {
    private final TrackerApi trackerApi;

    public TrackerService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TRACKER_HOSTNAME)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        trackerApi = retrofit.create(TrackerApi.class);
    }

    public void addPoints(ArrayList<VehiclePositionDto> lastLocations, String vehicleId) {
        trackerApi.addLocationsPack(new VehiclePositionRequest(lastLocations, vehicleId))
                .subscribeOn(Schedulers.computation()) // everything above and under is on computation thread
                .doOnNext(request -> { //computation thread
                            System.out.println("ThreadName : " + Thread.currentThread().getName());
                            System.out.println("Vehicle: " + request.getVehicleId());
                        }
                )
                .observeOn(AndroidSchedulers.mainThread()) //everything under is on the main thread
                .subscribe(new DisposableObserver<VehiclePositionRequest>() { //mainThread
                    @Override
                    public void onNext(@NonNull VehiclePositionRequest request) {
                        System.out.println("ThreadName : " + Thread.currentThread().getName());
                        System.out.println("Vehicle: " + request.getVehicleId());
                        PrintLocations(request);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("Something has gone wrong. Saving locations. " + e);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Everything is okay. Data has been sent. Deleting locations");
                        lastLocations.clear();
                    }
                });
    }

    public void addVehicle(VehicleStatRequest req) {
        trackerApi.addVehicle(req)
                .subscribeOn(Schedulers.computation())
                .doOnNext(request -> {
                    System.out.println(request.getVehicleId() + " This is vehicle Id next on " + Thread.currentThread().getName());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<VehicleStatRequest>() {

                    @Override
                    public void onNext(@NonNull VehicleStatRequest request) {
                        System.out.println(request + "Car has been added " + req.getVehicleId() + " " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println(e);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Done");
                    }
                });
    }


    private void PrintLocations(@NonNull VehiclePositionRequest request) {
        int i = 0;
        for (VehiclePositionDto pos : request.getPositions()) {
            System.out.println(++i + ". ( " + pos.getLatitude() + ", " + pos.getLongitude() + " )");
        }
    }
}
