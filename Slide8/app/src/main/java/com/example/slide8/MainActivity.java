package com.example.slide8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button;

    private MyReceiver broadcastReceiver;

    private boolean isRegistered = false;

    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        101);
            }
        }

        broadcastReceiver = new MyReceiver();
        intentFilter = new IntentFilter("com.example.ACTION_MY_EVENT");
        registerReceiver(broadcastReceiver, intentFilter);
        isRegistered = true;

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.ACTION_MY_EVENT");
                intent.putExtra("message", "Hello, this is a broadcast event!");
                sendBroadcast(intent);
                LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);
            }
        });
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if (!isRegistered) {
//            broadcastReceiver = new MyReceiver();
//            intentFilter = new IntentFilter("com.example.ACTION_MY_EVENT");
//            registerReceiver(broadcastReceiver, intentFilter);
//            isRegistered = true;
//        }
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (isRegistered) {
//            unregisterReceiver(broadcastReceiver);
//            Toast.makeText(MainActivity.this, "Unregistered broadcast receiver", Toast.LENGTH_SHORT).show();
//            isRegistered = false;
//        }
//    }
}