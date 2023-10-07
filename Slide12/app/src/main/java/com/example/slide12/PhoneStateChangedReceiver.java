package com.example.slide12;

import static android.app.ProgressDialog.show;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class PhoneStateChangedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Toast.makeText(context, "Incoming Call From: " + phoneNumber, Toast.LENGTH_LONG).show();
        }
    }
}
