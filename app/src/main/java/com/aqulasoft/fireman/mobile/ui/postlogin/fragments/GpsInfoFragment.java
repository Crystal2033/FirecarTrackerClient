package com.aqulasoft.fireman.mobile.ui.postlogin.fragments;

import static com.aqulasoft.fireman.mobile.base.utils.FiremanSettings.LOCATION_GPS_TIME_UPDATE;
import static com.aqulasoft.fireman.mobile.base.utils.FiremanSettings.LOCATION_PACK_SIZE;
import static com.aqulasoft.fireman.mobile.base.utils.FiremanSettings.LOCATION_SEND_TIME_INTERVAL;
import static com.aqulasoft.fireman.mobile.base.utils.FiremanSettings.NETWORK_PROVIDER_TIME_UPDATE;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.aqulasoft.fireman.mobile.base.repository.tracker.TrackerService;
import com.aqulasoft.fireman.mobile.databinding.FragmentGpsInfoBinding;
import com.aqulasoft.fireman.mobile.ui.base.BaseFragment;
import com.aqulasoft.fireman.mobile.ui.postlogin.models.VehiclePositionDto;

import java.util.ArrayList;
import java.util.Date;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import moxy.presenter.InjectPresenter;


public class GpsInfoFragment extends BaseFragment<FragmentGpsInfoBinding> implements GpsInfoView {

    @InjectPresenter
    GpsInfoPresenter mPresenter;
    Location currentLocation = null;

    private LocationManager locationManager;
    private TrackerService trackerService;

    private ArrayList<VehiclePositionDto> lastLocations;
    private String vehicleId = "PaulVehicle"; //TODO: delete this field in future, need to get this data
    private String eventId = "74"; //TODO: delete this field in future, need to get this data

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            currentLocation = location;
            showLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            checkEnabled();
        }

        @Override
        public void onProviderEnabled(String provider) {
            checkEnabled();
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            showLocation(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (provider.equals(LocationManager.GPS_PROVIDER)) {
                mBinding.tvStatusGPS.setText("Status: " + String.valueOf(status));
            } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
                mBinding.tvStatusNet.setText("Status: " + String.valueOf(status));
            }
        }
    };

    public GpsInfoFragment() {
        // Required empty public constructor
        trackerService = new TrackerService();
        lastLocations = new ArrayList<>();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Disposable dispose = schedule()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::sendData);
    }

    public void sendData(Location location) {
        System.out.println(location.getLatitude());
        System.out.println(location.getLongitude());
        System.out.println("\n");
        //TODO: Getting vehicleId and Getting eventId

//        lastLocations.add(new VehiclePositionDto(location, eventId));
//        if (lastLocations.size() > LOCATION_PACK_SIZE) {
//            System.out.println("Sending data...");
//            trackerService.addPoints(lastLocations, vehicleId);
//        }
    }

    public Observable<Location> schedule() {
        return Observable.create(subscriber -> {
                    while (true) {
                        Thread.sleep(LOCATION_SEND_TIME_INTERVAL);
                        if (currentLocation != null) {
                            subscriber.onNext(currentLocation);
                        }
                    }
                }
        );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.btnLocationSettings.setOnClickListener(this::onClickLocationSettings);
        mBinding.btnChangeEventID.setOnClickListener(this::onClickChangeEventId);
        mBinding.textEventId.setText("Event ID: " + eventId);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                LOCATION_GPS_TIME_UPDATE, 10, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, NETWORK_PROVIDER_TIME_UPDATE, 10,
                locationListener);
        checkEnabled();
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    protected Class<FragmentGpsInfoBinding> getBindingClass() {
        return FragmentGpsInfoBinding.class;
    }

    private void showLocation(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            mBinding.tvLocationGPS.setText(formatLocation(location));
        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
            mBinding.tvLocationNet.setText(formatLocation(location));
        }
    }

    private String formatLocation(Location location) {
        if (location == null) {
            return "";
        }
        System.out.println(String.format(
                "Latitude = %1$.4f\nLongitude = %2$.4f\nTime = %3$tF %3$tT",
                location.getLatitude(), location.getLongitude(), new Date(
                        location.getTime())));

        return String.format(
                "Latitude = %1$.4f\nLongitude = %2$.4f\nTime = %3$tF %3$tT",
                location.getLatitude(), location.getLongitude(), new Date(
                        location.getTime()));
    }


    private void checkEnabled() {
        mBinding.tvEnabledGPS.setText("Enabled: "
                + locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER));
        mBinding.tvEnabledNet.setText("Enabled: "
                + locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER));

    }

    public void onClickLocationSettings(View view) {
        startActivity(new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

    public void onClickChangeEventId(View view) {
        Integer newEventId = Integer.parseInt(eventId);
        newEventId++;
        eventId = newEventId.toString();
        mBinding.textEventId.setText("Event ID: " + eventId);
    }


}