package com.example.bookit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);  // Link to your XML file

        // Set a delay for the splash screen (e.g., 3 seconds)
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(splash.this, MainActivity.class);
            startActivity(intent);
            finish();  // Finish splash activity so it doesn't appear on back stack
        }, 2000);  // 2-second delay
    }
}
