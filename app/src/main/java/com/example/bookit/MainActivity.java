package com.example.bookit;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("LoginState", MODE_PRIVATE);

        // check if user is logged in
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        // open appropriate activity based on login status
        if (isLoggedIn) {
            // If logged in, open Home page
            Intent intent = new Intent(MainActivity.this, Home.class);
            int userID = sharedPreferences.getInt("userID", -1);
            intent.putExtra("userID", userID);
            startActivity(intent);
        } else {
            // If not logged in, open Login page
            Intent intent = new Intent(MainActivity.this, LogIn.class);
            startActivity(intent);
        }

        // Close MainActivity
        finish();
    }
}
