package com.aqulasoft.fireman.mobile.ui.postlogin.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aqulasoft.fireman.mobile.R;
import com.aqulasoft.fireman.mobile.databinding.FragmentGpsInfoBinding;
import com.aqulasoft.fireman.mobile.ui.base.BaseFragment;
import com.aqulasoft.fireman.mobile.ui.postlogin.MainActivity;

import java.util.Date;

import javax.inject.Inject;

import moxy.presenter.InjectPresenter;


public class GpsInfoFragment extends BaseFragment<FragmentGpsInfoBinding> implements GpsInfoView {
    @InjectPresenter
    GpsInfoPresenter mPresenter;

    private LocationManager locationManager;

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
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
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.btnLocationSettings.setOnClickListener(this::onClickLocationSettings);
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
                1000 * 10, 10, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
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
        if (location == null)
            return "";
        return String.format(
                "Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT",
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
    };
}