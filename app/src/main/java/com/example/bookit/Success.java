package com.example.bookit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Success extends AppCompatActivity {

    Button home;
    Button orders;
    int userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success);
        userID = getIntent().getIntExtra("userID", 1);
        int ticketid = getIntent().getIntExtra("ticketID", 1);

        TextView ticketIDDisplay = findViewById(R.id.ticketid);
        ticketIDDisplay.setText("Ticket id : " + String.valueOf(ticketid));

        home = findViewById(R.id.home);
        orders = findViewById(R.id.orders);

        SharedPreferences sharedPreferences = getSharedPreferences("LoginState", MODE_PRIVATE);

        int userID = sharedPreferences.getInt("userID", -1);  // -1 is the default value if userID doesn't exist



        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Success.this, Home.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Success.this, OrderActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });



    }
}