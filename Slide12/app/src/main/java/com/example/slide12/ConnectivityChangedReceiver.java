package com.example.slide12;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.List;

public class ConnectivityChangedReceiver extends BroadcastReceiver {

    private WifiManager mWifiManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                Toast.makeText(context, "Permission not granted", Toast.LENGTH_SHORT).show();



                return;
            }
            List<ScanResult> mScanResults = mWifiManager.getScanResults();
            Toast.makeText(context, "Wifi list refreshed", Toast.LENGTH_SHORT).show();
        }
    }
}
