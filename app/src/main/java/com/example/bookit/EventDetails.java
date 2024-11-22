package com.example.bookit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class EventDetails extends AppCompatActivity {
    // db
    private DatabaseHelper dbHelper;
    private int showId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details);  // Layout file for this activity


        showId = getIntent().getIntExtra("showId", -1);


        // db
        dbHelper = new DatabaseHelper(this);





        // fields
        TextView eventName = findViewById(R.id.mname_details);
        ImageView poster = findViewById(R.id.posterNormalImg);
        ImageView back = findViewById(R.id.back);
        TextView rating = findViewById(R.id.rating);
        TextView duration = findViewById(R.id.time_text);
        TextView genre = findViewById(R.id.genre_text);
        TextView description = findViewById(R.id.about);
        TextView cast = findViewById(R.id.cast);
        Button booknow = findViewById(R.id.booknow);

        // event details
        Event event = dbHelper.getEventByID(showId);

        // filling all fields in the UI from database
        eventName.setText(event.getName());
        Log.d("", "poster  : " + event.getPosterUrl());
        Picasso.get().load(event.getPosterUrl()).into(poster);
        rating.setText(String.valueOf(event.getRating()));
        duration.setText(event.getDuration());
        genre.setText(event.getGenre());
        description.setText(event.getDescription());
        cast.setText(event.getCast());

        back.setOnClickListener(new View.OnClickListener() {
            int categoryId = getIntent().getIntExtra("category_id", -1);
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(EventDetails.this, EventList.class);
                i.putExtra("category_id", categoryId);
                startActivity(i);
            }
        });


        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int categoryId = getIntent().getIntExtra("category_id", -1);
                Intent i = new Intent(EventDetails.this, ShowList.class);
                i.putExtra("show_id", showId);
                i.putExtra("category_id", categoryId);
                startActivity(i);
            }
        });

    }
}
