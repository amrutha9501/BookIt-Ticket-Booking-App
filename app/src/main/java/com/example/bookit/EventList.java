package com.example.bookit;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class EventList extends AppCompatActivity {

    // shows data container
    ArrayList<Event> events;
    EventAdapter adapter;

    // accordians
    public boolean isFiltersExpanded = false;
    public boolean isGenreExpanded = true;
    public boolean isRatingExpanded = true;
    public boolean isLocationExpanded = false;
    public boolean isLanguageExpanded = false;
    int userID;

    // search and filter
    EditText searchEditText;
    Button applyfilters;

    // database
    private DatabaseHelper dbHelper;

    // filter options
    //      location filters
    CheckBox mangalore;
    CheckBox udupi;
    CheckBox manipal;
    //      language filters
    CheckBox hindi;
    CheckBox english;
    CheckBox kannada;
    CheckBox odia;
    //      genre filters
    CheckBox horror;
    ;
    CheckBox adventure;
    ;
    CheckBox scifi;
    CheckBox drama;
    CheckBox comedy;
    //      rating
    RadioGroup ratings;

    // category id from intent
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);
        userID = getIntent().getIntExtra("userID", -1);
        ImageView home = findViewById(R.id.HomeIcon);
        home.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(EventList.this, Home.class);
                i.putExtra("userID", userID);
                startActivity(i);
            }
        });


        mangalore = findViewById(R.id.mangalore);
        udupi = findViewById(R.id.udupi);
        manipal = findViewById(R.id.manipal);

        hindi = findViewById(R.id.hindi);
        english = findViewById(R.id.english);
        kannada = findViewById(R.id.kannada);
        odia = findViewById(R.id.odia);

        horror = findViewById(R.id.horror);
        adventure = findViewById(R.id.adventure);
        scifi = findViewById(R.id.scifi);
        drama = findViewById(R.id.drama);
        comedy = findViewById(R.id.comedy);

        ratings = findViewById(R.id.ratings);

        // accordian code

        LinearLayout filters = findViewById(R.id.filtersholder);
        ScrollView filterdropdowncontainer = findViewById(R.id.filterdropdowncontainer);

        LinearLayout genre = findViewById(R.id.genreholder);
        LinearLayout genreoptions = findViewById(R.id.genreoptions);

        LinearLayout rating = findViewById(R.id.ratingholder);
        LinearLayout ratingoptions = findViewById(R.id.ratingoptions);

        LinearLayout location = findViewById(R.id.locationholder);
        LinearLayout locationoptions = findViewById(R.id.locationoptions);

        LinearLayout language = findViewById(R.id.languageholder);
        LinearLayout languageoptions = findViewById(R.id.languageoptions);


        filters.setOnClickListener(v -> {
            isFiltersExpanded = !isFiltersExpanded;
            filterdropdowncontainer.setVisibility(isFiltersExpanded ? View.VISIBLE : View.GONE);
        });

        filterdropdowncontainer.setOnClickListener(v -> {
//            prevents clicking background items
//            Toast.makeText(this, "clicked inside dropdown", Toast.LENGTH_SHORT).show();
        });


        genre.setOnClickListener(v -> {
            isGenreExpanded = !isGenreExpanded;
            genreoptions.setVisibility(isGenreExpanded ? View.VISIBLE : View.GONE);
        });

        rating.setOnClickListener(v -> {
            isRatingExpanded = !isRatingExpanded;
            ratingoptions.setVisibility(isRatingExpanded ? View.VISIBLE : View.GONE);
        });

        location.setOnClickListener(v -> {
            isLocationExpanded = !isLocationExpanded;
            locationoptions.setVisibility(isLocationExpanded ? View.VISIBLE : View.GONE);
        });


        language.setOnClickListener(v -> {
            isLanguageExpanded = !isLanguageExpanded;
            languageoptions.setVisibility(isLanguageExpanded ? View.VISIBLE : View.GONE);
        });

        // accordian code done


        // event list code begins


        // filling 'events' with all the shows of given category id (passed via intent)
        dbHelper = new DatabaseHelper(this);
        id = getIntent().getIntExtra("category_id", -1);
        events = dbHelper.getAllShows(id);


        // creating the recycler view

        RecyclerView recyclerView = findViewById(R.id.events);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2); // 2 columns
        recyclerView.setLayoutManager(gridLayoutManager);


        // creating and attaching the adapter
        adapter = new EventAdapter(this, events);
        recyclerView.setAdapter(adapter);

        // event list ends here


        // search feature (for now there are no active suggestions list but the app updates the recycler view based on the entered text)
        // Set up the search bar
        searchEditText = findViewById(R.id.search);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call filter method in adapter
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed here
            }
        });


        // applying the filters .
        // on button click
        //      the parent accordian is closed
        //      all the filter options are checked.
        //      database is queried
        //      events is updated.
        //      adapter is notified of dataset changes.
        applyfilters = findViewById(R.id.applyfilters);
        applyfilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFiltersExpanded = !isFiltersExpanded;
                filterdropdowncontainer.setVisibility(isFiltersExpanded ? View.VISIBLE : View.GONE);
                adapter.filterAndUpdateData(getFilterdData());
                Toast.makeText(EventList.this, "Filters applied successfully.", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private ArrayList<Event> getFilterdData() {
        ArrayList<String> selectedLocations = getSelectedLocations(); // List of selected locations
        ArrayList<String> selectedGenres = getSelectedGenres();       // List of selected genres
        ArrayList<String> selectedLanguages = getSelectedLanguages(); // List of selected languages
        int radiobuttonid = ratings.getCheckedRadioButtonId();

        String selectedRating = radiobuttonid == -1 ? "" :
                (radiobuttonid == R.id.ratingabove0 ? "0+" : ((RadioButton) findViewById(radiobuttonid)).getText().toString());

        Log.d("locations", selectedLocations.toString());
        Log.d("genres", selectedGenres.toString());
        Log.d("languages", selectedLanguages.toString());
        Log.d("rating", selectedRating);

        return dbHelper.filterData(selectedLocations, selectedGenres, selectedLanguages, selectedRating, id);
    }

    private ArrayList<String> getSelectedLocations() {
        ArrayList<String> selectedLocations = new ArrayList<>();

        if (mangalore.isChecked()) {
            selectedLocations.add("Mangalore");  // Add location name based on the checkbox
        }
        if (udupi.isChecked()) {
            selectedLocations.add("Udupi");
        }
        if (manipal.isChecked()) {
            selectedLocations.add("Manipal");
        }

        return selectedLocations;
    }


    private ArrayList<String> getSelectedGenres() {
        ArrayList<String> selectedGenres = new ArrayList<>();

        if (horror.isChecked()) {
            selectedGenres.add("Horror");
        }
        if (adventure.isChecked()) {
            selectedGenres.add("Adventure");
        }
        if (scifi.isChecked()) {
            selectedGenres.add("Sci-fi");
        }
        if (drama.isChecked()) {
            selectedGenres.add("Drama");
        }
        if (comedy.isChecked()) {
            selectedGenres.add("Comedy");
        }

        return selectedGenres;
    }


    private ArrayList<String> getSelectedLanguages() {
        ArrayList<String> selectedLanguages = new ArrayList<>();

        if (kannada.isChecked()) {
            selectedLanguages.add("Kannada");
        }
        if (english.isChecked()) {
            selectedLanguages.add("English");
        }
        if (hindi.isChecked()) {
            selectedLanguages.add("Hindi");
        }
        if (odia.isChecked()) {
            selectedLanguages.add("Odia");
        }

        return selectedLanguages;
    }


}