package com.example.bookit;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ShowViewHolder> {

    private ArrayList<Show> showList; // List of shows
    private Context context; // Context to inflate layout

    // Constructor
    public ShowAdapter(Context context, ArrayList<Show> showList) {
        this.context = context;
        this.showList = showList;
    }

    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item_show layout
        View view = LayoutInflater.from(context).inflate(R.layout.show_item, parent, false);
        return new ShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder holder, int position) {
        // Get the current show
        Show show = showList.get(position);

        // Bind data to the views
        holder.textViewDate.setText(show.getDate());
        holder.textViewTime.setText(show.getTime());
        holder.textViewTheatre.setText(show.getTheatreName());
        holder.textViewCity.setText(show.getCity());



        holder.showHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // for now display only the toast
//                Toast.makeText(v.getContext(), "Clicked on " + show.getTheatreName(), Toast.LENGTH_SHORT).show();


                // Start the new activity and pass the event details, including the ID
                // Start the new activity and pass the event details, including the ID
                Intent intent = new Intent(context, Book.class);

                intent.putExtra("show_timing_id", show.getTiming_id()); // Pass the ID
                SharedPreferences sharedPref = context.getSharedPreferences("LoginState", MODE_PRIVATE);
                int userID = sharedPref.getInt("userID", -1);  // -1 is the default value if userID doesn't exist

                intent.putExtra("userID", userID);

                context.startActivity(intent);



            }
        });


    }


    public void updateData(ArrayList<Show> newData) {
        showList = newData;
        notifyDataSetChanged(); // Notify the adapter of the changes
    }



    @Override
    public int getItemCount() {
        return showList.size(); // Return the size of the show list
    }



    // ViewHolder class to hold the views for each item
    static class ShowViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate;
        TextView textViewTime;
        TextView textViewTheatre;
        TextView textViewCity;
        LinearLayout showHolder;

        ShowViewHolder(View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.showDate);
            textViewTime = itemView.findViewById(R.id.showTime);
            textViewTheatre = itemView.findViewById(R.id.showTheatre);
            textViewCity = itemView.findViewById(R.id.showCity);
            showHolder = itemView.findViewById(R.id.showHolder);
        }
    }
}
