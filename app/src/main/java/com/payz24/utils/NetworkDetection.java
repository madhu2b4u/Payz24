package com.payz24.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.PermissionChecker;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by mahesh on 1/14/2018.
 */

public class NetworkDetection {

    private static final String TAG = NetworkDetection.class.getSimpleName();
    private static Location location;
    private static LatLng latlong;

    public boolean isMobileNetworkAvailable(Context ctx) {
        ConnectivityManager connectionManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo myNetworkInfo = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        try {
            if (myNetworkInfo == null) {
                return false;
            } else {
                return myNetworkInfo.isConnected() ? true : false;
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return false;
    }

    public boolean isWifiAvailable(Context ctx) {
        ConnectivityManager myConnManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo myNetworkInfo = myConnManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return myNetworkInfo.isConnected() ? true : false;
    }

    public boolean isGpsEnabled(Context ctx) {
        LocationManager locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (PermissionChecker.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && PermissionChecker.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return false;
            }
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                latlong = new LatLng(latitude, longitude);
            }
            return true;
        } else {
            return false;
        }
    }
}
