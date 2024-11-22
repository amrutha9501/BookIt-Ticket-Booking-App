package com.example.bookit;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    private EditText fullName, usrName, email, phone, password, confirmPwd;
    private Button signButton, loginButton;
    private TextView signupText;
    private DatabaseHelper dbHelper;
    private SharedPreferences sharedPreferences;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up); // Ensure this is your actual layout file name

        // Initialize views
//        fullName = findViewById(R.id.FullName);
        usrName = findViewById(R.id.usrName);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        confirmPwd = findViewById(R.id.ConfirmPwd);
        signButton = findViewById(R.id.signButton);
//        signupText = findViewById(R.id.signupText);
        loginButton = findViewById(R.id.login);

        // Initialize DBHelper
        dbHelper = new DatabaseHelper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("LoginState", MODE_PRIVATE);

        // Set click listener for sign-up button
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    insertRecord();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putInt("userID", userID);
                    editor.apply();
                    Intent intent = new Intent(SignUp.this , Home.class);
                    intent.putExtra("userID", userID);

             startActivity(intent);
                     finish();

                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the new activity
                Intent intent = new Intent(SignUp.this, LogIn.class);
                startActivity(intent);
            }
        });
    }

    // Method for form validation
    private boolean validateForm() {
//        String name = fullName.getText().toString().trim();
        String username = usrName.getText().toString().trim();
        String emailAddress = email.getText().toString().trim();
        String phoneNumber = phone.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String confirmPass = confirmPwd.getText().toString().trim();
//
//        if (name.isEmpty()) {
//            fullName.setError("Full Name is required");
//            return false;
//        }
        if (username.isEmpty()) {
            usrName.setError("Username is required");
            return false;
        }
        if (emailAddress.isEmpty()) {
            email.setError("Email is required");
            return false;
        }
        if (phoneNumber.isEmpty()) {
            phone.setError("Phone number is required");
            return false;
        }
        if (pass.isEmpty()) {
            password.setError("Password is required");
            return false;
        }
        if (pass.length() < 6) {
            password.setError("Password must be at least 6 characters");
            return false;
        }
        if (confirmPass.isEmpty()) {
            confirmPwd.setError("Confirm Password is required");
            return false;
        }
        if (!pass.equals(confirmPass)) {
            confirmPwd.setError("Passwords do not match");
            return false;
        }

        return true;
    }

    // Method to insert record into database
    private void insertRecord() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("fullName", fullName.getText().toString().trim());
        values.put("username", usrName.getText().toString().trim());
        values.put("email", email.getText().toString().trim());
        values.put("phone", phone.getText().toString().trim());
        values.put("password", password.getText().toString().trim());

        long newRowId = db.insert("users", null, values);
        userID = (int) newRowId;// "users" is your table name

        if (newRowId == -1) {
            Toast.makeText(this, "Error Signing Up", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(this, "Record inserted successfully", Toast.LENGTH_SHORT).show();
            clearForm();

        }
        db.close();
    }

    // Method to clear the form fields after successful registration
    private void clearForm() {
//        fullName.setText("");
        usrName.setText("");
        email.setText("");
        phone.setText("");
        password.setText("");
        confirmPwd.setText("");
    }
}
