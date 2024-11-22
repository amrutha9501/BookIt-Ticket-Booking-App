package com.example.bookit;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Book extends AppCompatActivity {

    private int currentCheckedSeatsID;
    private int currentSeatTypeID;
    private int multiplier;
    private int cost;
    private TextView total;
    private String seatType;
    private DatabaseHelper dbHelper;
    private int showTimingID;
    Button confirmButton;
    int availableSeats;
    int userID;
    ShowTiming showTiming;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_page);

        confirmButton = findViewById(R.id.buttonConfirm);

        dbHelper = new DatabaseHelper(this);



        showTimingID = getIntent().getIntExtra("show_timing_id", 1);
        userID = getIntent().getIntExtra("userID", 1);
        showTiming = dbHelper.getShowTimingByID(showTimingID);

        ImageView back = findViewById(R.id.imageView2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Book.this, Home.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        TextView title = findViewById(R.id.textView2);
        title.setText(showTiming.getShowTitle());

        TextView date = findViewById(R.id.textView5);
        date.setText(showTiming.getShowDate());

        TextView place = findViewById(R.id.textView4);
        place.setText(showTiming.getShowVenue() + ", " + showTiming.getShowCity());

        TextView time = findViewById(R.id.textView6);
        time.setText(showTiming.getShowTime());


        TextView goldAvailable, vipAvailable, platinumAvailable, specialAvailable, silverAvailable;

        goldAvailable = findViewById(R.id.txGold);
        vipAvailable = findViewById(R.id.txVIP);
        platinumAvailable = findViewById(R.id.txPlatinum);
        specialAvailable = findViewById(R.id.txSpecial);
        silverAvailable = findViewById(R.id.txSilver);

        goldAvailable.setText(String.valueOf(showTiming.getGoldSeats()));
        vipAvailable.setText(String.valueOf(showTiming.getVipSeats()));
        platinumAvailable.setText(String.valueOf(showTiming.getPlatinumSeats()));
        specialAvailable.setText(String.valueOf(showTiming.getSpecialSeats()));
        silverAvailable.setText(String.valueOf(showTiming.getSilverSeats()));


        multiplier = 2;
        cost = 200;
        seatType = "GOLD";
        availableSeats = showTiming.getGoldSeats();

        // text box for total
        total = findViewById(R.id.txTotal);

        total.setText(String.valueOf((multiplier * cost)));


        // number of seats
        CheckBox checkBox1 = findViewById(R.id.checkBox1);
        CheckBox checkBox2 = findViewById(R.id.checkBox2);
        CheckBox checkBox3 = findViewById(R.id.checkBox3);
        CheckBox checkBox4 = findViewById(R.id.checkBox4);
        CheckBox checkBox5 = findViewById(R.id.checkBox5);
        CheckBox checkBox6 = findViewById(R.id.checkBox6);
        CheckBox checkBox7 = findViewById(R.id.checkBox7);
        CheckBox checkBox8 = findViewById(R.id.checkBox8);
        CheckBox checkBox9 = findViewById(R.id.checkBox9);
        CheckBox checkBox10 = findViewById(R.id.checkBox10);

        // default checked
        checkBox2.setChecked(true);
        currentCheckedSeatsID = R.id.checkBox2;

        CompoundButton.OnCheckedChangeListener seatNumberListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    multiplier = Integer.valueOf(buttonView.getText().toString().strip());
                    Log.d("nums : ", String.valueOf(multiplier));
                    total.setText(String.valueOf((multiplier * cost)));

                }

                if (!isChecked && currentCheckedSeatsID == buttonView.getId()) {
                    buttonView.setOnCheckedChangeListener(null);

                    // Set the checked state programmatically without triggering the listener again
                    buttonView.setChecked(true);

                    // Re-enable the listener
                    buttonView.setOnCheckedChangeListener(this);
                } else if (currentCheckedSeatsID != -1) {
                    CheckBox checkBox = findViewById(currentCheckedSeatsID);

                    checkBox.setOnCheckedChangeListener(null);
                    // Set the checked state programmatically without triggering the listener again
                    checkBox.setChecked(false);
                    // Re-enable the listener
                    checkBox.setOnCheckedChangeListener(this);

                    currentCheckedSeatsID = buttonView.getId();
                } else {
                    currentCheckedSeatsID = buttonView.getId();

                }

            }
        };

        checkBox1.setOnCheckedChangeListener(seatNumberListener);
        checkBox2.setOnCheckedChangeListener(seatNumberListener);
        checkBox3.setOnCheckedChangeListener(seatNumberListener);
        checkBox4.setOnCheckedChangeListener(seatNumberListener);
        checkBox5.setOnCheckedChangeListener(seatNumberListener);
        checkBox6.setOnCheckedChangeListener(seatNumberListener);
        checkBox7.setOnCheckedChangeListener(seatNumberListener);
        checkBox8.setOnCheckedChangeListener(seatNumberListener);
        checkBox9.setOnCheckedChangeListener(seatNumberListener);
        checkBox10.setOnCheckedChangeListener(seatNumberListener);


        // seat type
        CheckBox vip = findViewById(R.id.rbVIP);
        CheckBox platinum = findViewById(R.id.rbPlatinum);
        CheckBox gold = findViewById(R.id.rbGold);
        CheckBox silver = findViewById(R.id.rbSilver);
        CheckBox speacial = findViewById(R.id.rbSpecial);

        // default checked
        gold.setChecked(true);
        currentSeatTypeID = R.id.rbGold;

        CompoundButton.OnCheckedChangeListener seatTypeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    setSeatType(buttonView.getId());
                    Log.d("cost : ", String.valueOf(cost));
                    total.setText(String.valueOf((multiplier * cost)));
                }

                if (!isChecked && currentSeatTypeID == buttonView.getId()) {
                    buttonView.setOnCheckedChangeListener(null);

                    // Set the checked state programmatically without triggering the listener again
                    buttonView.setChecked(true);

                    // Re-enable the listener
                    buttonView.setOnCheckedChangeListener(this);
                } else if (currentSeatTypeID != -1) {
                    CheckBox checkBox = findViewById(currentSeatTypeID);

                    checkBox.setOnCheckedChangeListener(null);
                    // Set the checked state programmatically without triggering the listener again
                    checkBox.setChecked(false);
                    // Re-enable the listener
                    checkBox.setOnCheckedChangeListener(this);


                    currentSeatTypeID = buttonView.getId();
                } else {
                    currentSeatTypeID = buttonView.getId();

                }

            }
        };

        vip.setOnCheckedChangeListener(seatTypeListener);
        platinum.setOnCheckedChangeListener(seatTypeListener);
        gold.setOnCheckedChangeListener(seatTypeListener);
        silver.setOnCheckedChangeListener(seatTypeListener);
        speacial.setOnCheckedChangeListener(seatTypeListener);


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (multiplier > availableSeats) {
                    Toast.makeText(getApplicationContext(), "Seats not available!", Toast.LENGTH_SHORT).show();

                } else {
                    int show_id = showTiming.getShowId();
                    int timing_id = showTiming.getTimingId();
                    String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    double amount = multiplier * cost;


                    int success = dbHelper.confirmTicket(userID, timing_id, currentDateTime, amount, seatType, multiplier);

                    if (success != -1) {
                        //create intent to next page
//                        Toast.makeText(getApplicationContext(), "Ticket booking success!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Book.this, Success.class);
                        intent.putExtra("userID", userID);
                        intent.putExtra("ticketID", success);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Ticket booking failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    private void setSeatType(int id) {
        if (id == R.id.rbVIP) {
            seatType = "VIP";
            cost = 350;
            availableSeats = showTiming.getVipSeats();
        } else if (id == R.id.rbPlatinum) {
            seatType = "PLATINUM";
            cost = 250;
            availableSeats = showTiming.getPlatinumSeats();
        } else if (id == R.id.rbGold) {
            seatType = "GOLD";
            cost = 200;
            availableSeats = showTiming.getGoldSeats();
        } else if (id == R.id.rbSilver) {
            seatType = "SILVER";
            cost = 150;
            availableSeats = showTiming.getSilverSeats();
        } else if (id == R.id.rbSpecial) {
            seatType = "SPEACIAL";
            cost = 100;
            availableSeats = showTiming.getSpecialSeats();
        }

    }
}