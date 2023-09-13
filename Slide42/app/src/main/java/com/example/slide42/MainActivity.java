package com.example.slide42;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Constraint Layout
        setContentView(R.layout.activity_main_constraint_layout);

        //Linear Layout
        //setContentView(R.layout.activity_main_linear_layout);

        //Relative Layout
        //setContentView(R.layout.activity_main_relative_layout);

        //Table Layout
        //setContentView(R.layout.activity_main_table_layout);
    }
}