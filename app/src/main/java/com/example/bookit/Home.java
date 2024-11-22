package com.example.bookit;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private RecyclerView recyclerViewTopMovies, footerMovies;
    private RecommendedAdapter recommendedAdapter;
    private ScrollView scrollView;
    private Button mov, concerts, magic, standup, live, sports;
    private ImageView pro_menu;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter adapter;
    private List<Integer> imageList;
    private List<Event> movieList;
    private int userID;

    // db
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        // db
        dbHelper = new DatabaseHelper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("LoginState", MODE_PRIVATE);
        int userID = sharedPreferences.getInt("userID", -1);  // -1 is the default value if userID doesn't exist



        recyclerViewTopMovies = findViewById(R.id.recycle_view_topmovies);
        viewPager2 = findViewById(R.id.viewpager_explore);
//        footerMovies = findViewById(R.id.footer_menu);
        scrollView = findViewById(R.id.scrollView);

        mov = findViewById(R.id.btn_movie);
        concerts = findViewById(R.id.btn_music);
        standup = findViewById(R.id.btn_comedy);
        live = findViewById(R.id.btn_live);
        magic = findViewById(R.id.btn_magic);
        sports = findViewById(R.id.btn_sports);

        pro_menu = findViewById(R.id.profile);

        imageList = new ArrayList<>();
        imageList.add(R.drawable.black); // Add your drawable resources
        imageList.add(R.drawable.titanic);
        imageList.add(R.drawable.looper);
        imageList.add(R.drawable.joker);



        SharedPreferences sharedPref = getSharedPreferences("LoginState", MODE_PRIVATE);






        pro_menu.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(this, view);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.menu_profile, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.option1) {
//                        Toast.makeText(getApplicationContext(), "Option 1 selected", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Home.this, Profile.class);

                        intent.putExtra("userID", userID);
                        startActivity(intent);
                        return true;
                    } else if (item.getItemId() == R.id.option2) {
//                        Toast.makeText(getApplicationContext(), "Option 2 selected", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Home.this, OrderActivity.class);

                        intent.putExtra("userID", userID);
                        startActivity(intent);
                        return true;
                    } else {
                        return false;
                    }
                }

            });
            popupMenu.show();
        });


        mov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the next activity
                Intent intent = new Intent(Home.this, EventList.class);
                intent.putExtra("category_id", 1);
                intent.putExtra("userID", userID);

//                intent.putExtra("show_id", 21);
                startActivity(intent);
            }
        });

        concerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the next activity
                Intent intent = new Intent(Home.this, EventList.class);
                intent.putExtra("category_id", 2);
                intent.putExtra("userID", userID);

//                intent.putExtra("show_id", 31);
                startActivity(intent);
            }
        });

        standup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the next activity
                Intent intent = new Intent(Home.this, EventList.class);
                intent.putExtra("category_id", 3);
                intent.putExtra("userID", userID);

//                intent.putExtra("show_id", 41);
                startActivity(intent);
            }
        });

        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the next activity
                Intent intent = new Intent(Home.this, EventList.class);
                intent.putExtra("category_id", 4);
                intent.putExtra("userID", userID);

//                intent.putExtra("show_id", 21);
                startActivity(intent);

            }
        });

        magic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the next activity
                Intent intent = new Intent(Home.this, EventList.class);
                intent.putExtra("category_id", 5);
                intent.putExtra("userID", userID);

                startActivity(intent);
            }
        });


        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the next activity
                Intent intent = new Intent(Home.this, EventList.class);
                intent.putExtra("category_id", 6);
                intent.putExtra("userID", userID);

//                intent.putExtra("show_id", 71);
                startActivity(intent);
            }
        });


        // Set up ViewPager2 adapter
        adapter = new ViewPagerAdapter(this, imageList);
        viewPager2.setAdapter(adapter);

        ProgressBar proBar = findViewById(R.id.progressBar);
        proBar.setVisibility(View.GONE);
        setupAutoScrollViewPager();

        recyclerViewTopMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ProgressBar progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);

//        footerMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        ProgressBar progressB2 = findViewById(R.id.progressBar3);
//        progressB2.setVisibility(View.GONE);
        // Initialize movie list before setting up adapter
//        initializeMovies();

        // Retrieve the movies from the database (random 5)
        movieList = new ArrayList<>();
        movieList.add(dbHelper.getEventByID(21));
        movieList.add(dbHelper.getEventByID(22));
        movieList.add(dbHelper.getEventByID(23));
        movieList.add(dbHelper.getEventByID(82));
        movieList.add(dbHelper.getEventByID(85));

        // Initialize the adapter with the movie list and set it to the RecyclerView
        recommendedAdapter = new RecommendedAdapter(movieList, this);
        recyclerViewTopMovies.setAdapter(recommendedAdapter);
//        footerMovies.setAdapter(recommendedAdapter);

//        scrollView.postDelayed(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN), 500);
    }


    // Set up auto-scrolling for ViewPager2
    private void setupAutoScrollViewPager() {
        final int delay = 3000;
        final android.os.Handler handler = new android.os.Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager2.getCurrentItem();
                int nextItem = (currentItem + 1) % imageList.size();
                viewPager2.setCurrentItem(nextItem);
                handler.postDelayed(this, delay);
            }
        };
        handler.postDelayed(runnable, delay);
    }

    // Example: Scroll to a specific position programmatically
    public void scrollToPosition(int yPosition) {
        scrollView.smoothScrollTo(0, yPosition);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Check if the user is logged in
        SharedPreferences sharedPref = getSharedPreferences("LoginState", MODE_PRIVATE);
        boolean isLoggedIn = sharedPref.getBoolean("isLoggedIn", false);
        if (!isLoggedIn) {
            // Show a logout success message
            Toast.makeText(getApplicationContext(), "Logged out.", Toast.LENGTH_SHORT).show();

            // Redirect to the login screen (optional)
            Intent intent = new Intent(getApplicationContext(), LogIn.class);
            startActivity(intent);
            finish();
        }
    }
}


