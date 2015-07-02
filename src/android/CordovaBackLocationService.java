package com.red_folder.phonegap.plugin.backgroundservice.sample;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;

import android.os.Bundle;

import android.util.Log;

import com.red_folder.phonegap.plugin.backgroundservice.BackgroundService;

public class CordovaBackLocationService extends BackgroundService {

    private final static String TAG = CordovaBackLocationService.class.getSimpleName();
    private String mHelloTo = "World";
    private LocationManager locationManager;

    private JSONObject jsonLocation;
    public final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            JSONObject result = new JSONObject();

            try {
                result.put("Latitude", location.getLatitude());
                result.put("Longitude", location.getLongitude());
            } catch (Exception e) {
            }

            CordovaBackLocationService.this.jsonLocation = result;
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        initManagementLocation();
        return START_STICKY;
    }

    @Override
    protected JSONObject doWork() {

        return this.jsonLocation;
    }

    public void initManagementLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                3000, // 3 sec
                1, this.mLocationListener);
    }

    @Override
    protected JSONObject getConfig() {
        JSONObject result = new JSONObject();

        try {
            result.put("HelloTo", this.mHelloTo);
        } catch (JSONException e) {
        }

        return result;
    }

    @Override
    protected void setConfig(JSONObject config) {
        try {
            if (config.has("HelloTo")) {
                this.mHelloTo = config.getString("HelloTo");
            }
        } catch (JSONException e) {
        }

    }

    @Override
    protected JSONObject initialiseLatestResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void onTimerEnabled() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onTimerDisabled() {
        // TODO Auto-generated method stub

    }

}
