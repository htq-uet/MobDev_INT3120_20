package com.example.slide13;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        // Add a marker in Sydney and move the camera

        // Add current location got from LocationActivity
        if (getIntent().hasExtra("latitude") && getIntent().hasExtra("longitude")) {
            LatLng currentLocation = new LatLng(getIntent().getDoubleExtra("latitude", 0), getIntent().getDoubleExtra("longitude", 0));
            mMap.addMarker(new MarkerOptions()
                    .position(currentLocation)
                    .title("Current Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        }
        else {
            LatLng vancouver = new LatLng(49.246292, -123.116226);
            mMap.addMarker(new MarkerOptions()
                    .position(vancouver)
                    .title("Marker in Vancouver"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(vancouver));
        }


    }
}