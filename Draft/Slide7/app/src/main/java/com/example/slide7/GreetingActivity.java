package com.example.slide7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GreetingActivity extends AppCompatActivity {

    private EditText dataReceived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);

        dataReceived = (EditText) findViewById(R.id.text_received);
        Button btnToMain = (Button) findViewById(R.id.button_to_main);
        btnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = dataReceived.getText().toString().trim();
                goBackToMain(data);
            }
        });

        String strDataReceived = getIntent().getStringExtra("fullName");
        dataReceived.setText(strDataReceived);
    }

    private void goBackToMain(String data) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("feedback", data);
        setResult(RESULT_OK, intent);
        finish();
    }
}