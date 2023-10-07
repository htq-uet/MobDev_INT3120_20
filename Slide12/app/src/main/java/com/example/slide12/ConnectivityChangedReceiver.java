package com.example.slide12;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.List;

public class ConnectivityChangedReceiver extends BroadcastReceiver {

    private WifiManager mWifiManager;


    private List<String> wifiList;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
            switch (wifiState) {
                case WifiManager.WIFI_STATE_ENABLED:
                    Toast.makeText(context, "Wifi Enabled", Toast.LENGTH_SHORT).show();
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    Toast.makeText(context, "Wifi Disabled", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        else if (intent.getAction() != null && intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(WifiActivity.wifiActivity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            }

            mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            List<ScanResult> scanResults = mWifiManager.getScanResults();
            for (ScanResult scanResult : scanResults) {
                wifiList.add(scanResult.SSID);
            }
            WifiActivity.listView.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, wifiList));
        }
    }
}
