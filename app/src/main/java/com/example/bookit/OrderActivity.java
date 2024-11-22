package com.example.bookit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TicketAdapter ticketAdapter;
    private List<Ticket> ticket;
    private int userID;
    ImageView home, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
//        userID = getIntent().getIntExtra("userID", -1);

        SharedPreferences sharedPreferences = getSharedPreferences("LoginState", MODE_PRIVATE);
        userID = sharedPreferences.getInt("userID", -1);

        home = findViewById(R.id.back_icon);
        profile = findViewById(R.id.menu_icon);



        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Redirect to the login screen (optional)
                Intent intent = new Intent(OrderActivity.this, Home.class);
                intent.putExtra("UserID", userID);
                startActivity(intent);
                finish();
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Redirect to the login screen (optional)
                Intent intent = new Intent(OrderActivity.this, Profile.class);
                intent.putExtra("UserID", userID);
                startActivity(intent);
                finish();
            }
        });

//        userID = getIntent().getIntExtra("userID", 1);

        recyclerView = findViewById(R.id.history_content_group); // Ensure you have a RecyclerView with this ID in your layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<Ticket> tickets = dbHelper.getTicketsByUserId(userID);



        ticketAdapter = new TicketAdapter(tickets);
        recyclerView.setAdapter(ticketAdapter);
    }
}