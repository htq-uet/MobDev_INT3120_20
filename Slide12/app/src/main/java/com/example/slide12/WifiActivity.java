package com.example.slide12;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.util.ArrayList;
import java.util.List;

public class WifiActivity extends AppCompatActivity {

    public static Activity wifiActivity;
    private WifiManager mWifiManager;
    private TextView networkInfoTextView, wifiInfoTextView, status;

    public static ListView listView;

    private ConnectivityManager mConnectivityManager;
    ConnectivityChangedReceiver receiver = new ConnectivityChangedReceiver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_layout);

        mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //Network Info
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            networkInfoTextView = findViewById(R.id.wifiStatus);
            networkInfoTextView.setText("Network Info: " + networkInfo.toString());

        } else {
            Toast.makeText(this, "Not connected", Toast.LENGTH_SHORT).show();
        }

        //Wifi Info
        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiInfoTextView = findViewById(R.id.wifiInfo);
        wifiInfoTextView.setText("Wifi Info: " + mWifiManager.getConnectionInfo().toString());




        registerReceiver(receiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
        mWifiManager.startScan();
        //List of Wifi Networks
        Button scanButton = findViewById(R.id.scan_wifi);
        listView = findViewById(R.id.wifiList);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(WifiActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(WifiActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    mWifiManager.startScan();
                    List<ScanResult> scanResults = mWifiManager.getScanResults();
                    List<String> wifiList = new ArrayList<>();
                    for (ScanResult scanResult : scanResults) {
                        wifiList.add(scanResult.SSID);
                    }
                    listView.setAdapter(new ArrayAdapter<>(WifiActivity.this, android.R.layout.simple_list_item_1, wifiList));
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        wifiActivity = this;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
