package com.example.bookit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogIn extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton, signupButton;
    private TextView signupText;
    private SharedPreferences sharedPreferences;
    DatabaseHelper dbHelper;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        dbHelper = new DatabaseHelper(this);

        // Initialize views
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
//        signupText = findViewById(R.id.signupText);
        signupButton = findViewById(R.id.signup);

        // Set onClickListener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLogin();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the new activity
                Intent intent = new Intent(LogIn.this, SignUp.class);
                startActivity(intent);
            }
        });

        // Initialize SharedPreferences

     sharedPreferences = getSharedPreferences("LoginState", MODE_PRIVATE);

        // Check login state on start
        checkLoginState();
    }

    private void checkLoginState() {
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            // Redirect to main activity or dashboard
             Intent intent = new Intent(LogIn.this, Home.class);
             startActivity(intent);
             finish();
        }
    }

    private void validateLogin() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Check if username is empty
        if (TextUtils.isEmpty(username)) {
            usernameEditText.setError("Username is required");
            return;
        }

        // Check if password is empty
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required");
            return;
        }

        // Check password length (optional)
        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters");
            return;
        }
        userID = dbHelper.authenticateUser(username, password);
        if (userID != -1) {
            // Store login state
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", true);
            editor.putInt("userID", userID);
            editor.apply();

            // Proceed with the app
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LogIn.this, Home.class);
            intent.putExtra("userID", userID);

            startActivity(intent);

        }else
        {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check if the user is logged in
        SharedPreferences sharedPref = getSharedPreferences("LoginState", MODE_PRIVATE);
        boolean isLoggedIn = sharedPref.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            // Show a logout success message
            Toast.makeText(getApplicationContext(), "Logged out.", Toast.LENGTH_SHORT).show();

            // Redirect to the login screen (optional)
            Intent intent = new Intent(getApplicationContext(), LogIn.class);
            startActivity(intent);
            finish();
        }
    }


}
