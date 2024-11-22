package com.example.bookit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShowList extends AppCompatActivity {

    private LinearLayout checkboxContainer;
    ArrayList<String> cityList;
    ArrayList<String> selectedLocations;
    public boolean isLocationFilterExpanded = false;
    Button apply;
    ArrayList<Show> showList;
    int show_id;
    DatabaseHelper dbHelper;
    ImageView back;

    TextView show_title;

    private RecyclerView showRecyclerView;
    private ShowAdapter showAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_list);

        show_id = getIntent().getIntExtra("show_id", -1);

        back = findViewById(R.id.showlistbackbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int categoryId = getIntent().getIntExtra("category_id", -1);


                Intent i = new Intent(ShowList.this, EventDetails.class);
                i.putExtra("showId", show_id);
                i.putExtra("category_id", categoryId);
                startActivity(i);
            }
        });


        selectedLocations = new ArrayList<>();
        cityList = new ArrayList<>();
        showList = new ArrayList<>();



        // location filter accordian
        LinearLayout filters = findViewById(R.id.filterlocationholder);

        show_title = findViewById(R.id.show_title);

        LinearLayout filteroptions = findViewById(R.id.filterlocationoptionsandapply);
        filters.setOnClickListener(v -> {
            isLocationFilterExpanded = !isLocationFilterExpanded ;

            filteroptions.setVisibility(isLocationFilterExpanded ? View.VISIBLE : View.GONE);
        });

        LinearLayout locationholder = findViewById(R.id.filterlocationoptions);


        // database starts here
        dbHelper = new DatabaseHelper(this);

        String title = dbHelper.getShowName(show_id);
        show_title.setText(title);

        cityList = dbHelper.getCities(show_id);

        showList = dbHelper.getShowTimings(show_id);



        // Dynamically create CheckBoxes based on location list
        for (String location : cityList) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(location);
            checkBox.setTextColor(R.color.filterstext);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    // Add the location to the selected locations list
                    selectedLocations.add(location);
                    //Toast.makeText(ShowList.this, location + " selected", Toast.LENGTH_SHORT).show();
                } else {
                    // Remove the location from the selected locations list
                    selectedLocations.remove(location);
                    //Toast.makeText(ShowList.this, location + " unselected", Toast.LENGTH_SHORT).show();
                }
            });

            // Add CheckBox to the container
            locationholder.addView(checkBox);
        }



        // applying the filter
        apply = findViewById(R.id.applylocationfilter);
        apply.setOnClickListener(v -> {
            isLocationFilterExpanded = !isLocationFilterExpanded ;
            filteroptions.setVisibility(isLocationFilterExpanded ? View.VISIBLE : View.GONE);
            ArrayList<Show> filterdData = dbHelper.getShowTimings(show_id, selectedLocations);
            showAdapter.updateData(filterdData);
            Toast.makeText(this, "Filter applied successfully.", Toast.LENGTH_SHORT).show();
            for (String location : selectedLocations) {
                Log.d("item", location);
            }

        });



        // show list
        showRecyclerView = findViewById(R.id.shows);
        showRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        showAdapter = new ShowAdapter(this, showList);
        showRecyclerView.setAdapter(showAdapter);


    }
}
