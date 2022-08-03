package com.aqulasoft.fireman.mobile.base.repository.tracker;

import static com.aqulasoft.fireman.mobile.base.utils.FiremanSettings.TRACKER_HOSTNAME;

import androidx.annotation.NonNull;

import com.aqulasoft.fireman.mobile.ui.postlogin.models.VehiclePositionRequest;
import com.aqulasoft.fireman.mobile.ui.postlogin.models.VehicleStatRequest;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrackerService {
    private TrackerApi trackerApi;

    public TrackerService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TRACKER_HOSTNAME)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        trackerApi = retrofit.create(TrackerApi.class);
    }

    public void addPoints(VehiclePositionRequest req) {
        trackerApi.addLocationsPack(req);
    }

    public void addVehicle(VehicleStatRequest req) {
        trackerApi.addVehicle(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<VehicleStatRequest>() {
                    @Override
                    public void onSuccess(@NonNull VehicleStatRequest request) {
                        System.out.println(request);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println(e + " FUCK");
                    }
                });
    }
}
