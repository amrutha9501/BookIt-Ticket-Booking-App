package com.example.bookit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    private TextView profileUsername, profileEmail, profilePhone;
    Button logout;
    ImageView history, home;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.profile);

        logout = findViewById(R.id.logout_button);

        history = findViewById(R.id.order_history_icon);

        home = findViewById(R.id.back_icon);


        SharedPreferences sharedPreferences = getSharedPreferences("LoginState", MODE_PRIVATE);
        int userID = sharedPreferences.getInt("userID", -1);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Redirect to the login screen (optional)
                Intent intent = new Intent(Profile.this, Home.class);
                intent.putExtra("UserID", userID);
                startActivity(intent);
                finish();
            }
        });


        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Redirect to the login screen (optional)
                Intent intent = new Intent(Profile.this, OrderActivity.class);
                intent.putExtra("UserID", userID);
                startActivity(intent);
                finish();
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Access the default shared preferences
                SharedPreferences sharedPref = getSharedPreferences("LoginState", MODE_PRIVATE);

                // Check if the user is logged in
                boolean isLoggedIn = sharedPref.getBoolean("isLoggedIn", false);

                if (isLoggedIn) {
                    // User is logged in, proceed with logout
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("isLoggedIn", false);  // Set the login status to false
                    editor.apply();

                    // Show a logout success message
                    Toast.makeText(getApplicationContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();

                    // Redirect to the login screen (optional)
                    Intent intent = new Intent(getApplicationContext(), LogIn.class);
                    startActivity(intent);
                    finish();
                } else {
                    // If already logged out, show a message
                    Toast.makeText(getApplicationContext(), "You are already logged out", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Initialize the TextViews
        profileUsername =

                findViewById(R.id.profile_username);

        profileEmail =

                findViewById(R.id.profile_email);

        profilePhone =

                findViewById(R.id.profile_phone);

        // Example: Suppose we pass the user ID through an intent when logging in
//        int userId = getIntent().getIntExtra("USER_ID", 1);

        // Fetch user details from the database using the userId
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        User user = dbHelper.getUserDetails(userID);

        // Check if the user exists and update the TextViews with user data
        if (user != null) {
            profileUsername.setText(user.getUsername());
            profileEmail.setText(user.getEmail());
            profilePhone.setText(user.getPhone());
        } else {
            // Handle the case where the user is not found
            profileUsername.setText("User not found");
            profileEmail.setText("-");
            profilePhone.setText("-");
        }
    }


}
