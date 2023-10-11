package com.example.slide13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button playMusicButton;

    Button locationButton;

    Button mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationButton = findViewById(R.id.button_location);
        locationButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LocationActivity.class);
            startActivity(intent);
        });

        mapButton = findViewById(R.id.button_map);
        mapButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MapActivity.class));
        });


        playMusicButton = findViewById(R.id.button_play_music);
        playMusicButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MusicActivity.class);
            startActivity(intent);
        });


    }
}