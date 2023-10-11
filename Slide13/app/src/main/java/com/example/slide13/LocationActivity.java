package com.example.slide13;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity implements LocationListener {

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationManager locationManager;

    private String locationProvider;

    private Button getLocationButton;

    private Button updateLocationButton;

    private Button mainButton;

    private TextView locationDetails;

    private final static int REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        locationDetails = findViewById(R.id.location_details);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationProvider = LocationManager.GPS_PROVIDER;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        getLocationButton = findViewById(R.id.button_get_location);
        getLocationButton.setOnClickListener(v -> getLastLocation());

        updateLocationButton = findViewById(R.id.button_upd_loc);
        updateLocationButton.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(LocationActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
            }
            locationManager.requestLocationUpdates(locationProvider, 2000L, 1F, LocationActivity.this);
        });




    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<android.location.Location>() {
            @Override
            public void onSuccess(android.location.Location location) {
                if (location != null) {
                    Geocoder geocoder = new Geocoder(LocationActivity.this, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        locationDetails.setText("Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude() + "\n" + addresses.get(0).getAddressLine(0));
                        // Gửi cho MapActivity, khi mở MapActivity lên, nó sẽ lấy dữ liệu này và hiển thị
                        Intent intent = new Intent(LocationActivity.this, MapActivity.class);
                        intent.putExtra("latitude", location.getLatitude());
                        intent.putExtra("longitude", location.getLongitude());
                        intent.putExtra("address", addresses.get(0).getAddressLine(0));
                        startActivity(intent);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull android.location.Location location) {
        Geocoder geocoder = new Geocoder(LocationActivity.this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        locationDetails.setText("Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude() + "\n" + addresses.get(0).getAddressLine(0));
    }
}