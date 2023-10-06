package com.example.slide12;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor accelerometer;

    TextView xValue, yValue, zValue;

    int lastColor = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accelerometer_layout);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // Accelerometer
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        xValue = findViewById(R.id.xValue);
        yValue = findViewById(R.id.yValue);
        zValue = findViewById(R.id.zValue);

        xValue.setText("xA: " + x);
        yValue.setText("yA: " + y);
        zValue.setText("zA: " + z);

        int color = lastColor;

        if (x == 0 || y == 0 || z == 0) {
            color = Color.WHITE;
        } else if (x > 5 || y > 5 || z > 5) {
            color = Color.RED;
        } else if (x < -5 || y < -5 || z < -5) {
            color = Color.BLUE;
        }

        if (color != lastColor) {
            lastColor = color;
            TextView textView = findViewById(R.id.shake);
            textView.setBackgroundColor(color);
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }
}
