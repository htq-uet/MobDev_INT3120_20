package com.example.slide12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;

    Button accelerometerButton, compassButton, wifiButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


        //Accelerometer
        accelerometerButton = findViewById(R.id.accelerometer);
        accelerometerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
                    Toast.makeText(MainActivity.this, "Accelerometer Available", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, AccelerometerActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Accelerometer Not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Magnetic Field
        compassButton = findViewById(R.id.magnetometer);
        compassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
                    Toast.makeText(MainActivity.this, "Magnetic Field Available", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, CompassActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Magnetic Field Not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Wifi
        wifiButton = findViewById(R.id.wifi);
        wifiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WifiActivity.class);
                startActivity(intent);
            }
        });

        //Telephone
        Button telephoneButton = findViewById(R.id.telephone);
        telephoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TelephoneActivity.class);
                startActivity(intent);
            }
        });

    }


}