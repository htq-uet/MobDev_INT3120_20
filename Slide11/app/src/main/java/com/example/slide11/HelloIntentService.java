package com.example.slide11;

import android.app.IntentService;
import android.content.Intent;

public class HelloIntentService extends IntentService {

        public HelloIntentService() {
            super("HelloIntentService");
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            // Do the task here
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // Restore interrupt status.
                Thread.currentThread().interrupt();
            }
        }
}
