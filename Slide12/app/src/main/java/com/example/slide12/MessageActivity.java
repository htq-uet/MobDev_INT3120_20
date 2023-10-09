package com.example.slide12;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MessageActivity extends AppCompatActivity {

    private EditText phone, message;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_layout);

        phone = findViewById(R.id.phoneNumber);
        message = findViewById(R.id.message);
        send = findViewById(R.id.sendMessage);

        if (ActivityCompat.checkSelfPermission(MessageActivity.this, android.Manifest.permission.SEND_SMS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MessageActivity.this, new String[]{android.Manifest.permission.SEND_SMS}, 1);
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phone.getText().toString();
                String messageContent = message.getText().toString();

                //Dùng SmsManager để gửi tin nhắn (Gửi trực tiếp)
//                SmsManager smsManager = SmsManager.getDefault();
//                smsManager.sendTextMessage(phoneNumber, null, messageContent, null, null);

                //Dùng Intent để gửi tin nhắn (Vào ứng dụng tin nhắn)
                 Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
                 intent.putExtra("sms_body", messageContent);
                 startActivity(intent);

            }
        });
    }
}
