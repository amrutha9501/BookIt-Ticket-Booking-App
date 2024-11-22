package com.example.bookit;



import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private final Context context;
    private List<Event> dataList;
    private List<Event> dataListFull;

    public EventAdapter(Context context, List<Event> dataList) {
        this.context = context;
        this.dataList = dataList;
        dataListFull = new ArrayList<>(dataList);
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event currentEvent = dataList.get(position);
        holder.nameTextView.setText(currentEvent.getName());
        holder.ratingTextView.setText(String.valueOf(currentEvent.getRating()));
        holder.genreTextView.setText(currentEvent.getGenre());
        Picasso.get().load(currentEvent.getPosterUrl()).into(holder.moviePoster);




        holder.eventCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // for now display only the toast
//                Toast.makeText(v.getContext(), "Clicked on " + currentEvent.getName(), Toast.LENGTH_SHORT).show();




                // Start the new activity and pass the event details, including the ID
                Intent intent = new Intent(context, EventDetails.class);

                intent.putExtra("showId", currentEvent.getId()); // Pass the ID
                intent.putExtra("category_id", currentEvent.getCategoryId());

                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }




    // Method to filter events based on search query
    public void filter(String query) {
        if (query.isEmpty()) {
            dataList = new ArrayList<>(dataListFull); // Reset to full list if query is empty
        } else {
            List<Event> filteredList = new ArrayList<>();
            for (Event event : dataListFull) {
                if (event.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(event);
                }
            }
           dataList = filteredList; // Update the list with the filtered results
        }
        notifyDataSetChanged(); // Notify the adapter to refresh the views
    }

    public void filterAndUpdateData(ArrayList<Event> newData) {
//        this.dataList.clear(); // Clear existing data
//        this.dataList.addAll(newData); // Add new data
        dataList = newData;
        dataListFull = new ArrayList<>(dataList);
        notifyDataSetChanged(); // Notify the adapter of the changes
    }



    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, ratingTextView, genreTextView;
        CardView eventCard;
        ImageView moviePoster;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.moviePoster);
            nameTextView = itemView.findViewById(R.id.movieName);
            ratingTextView = itemView.findViewById(R.id.movieRating);
            genreTextView = itemView.findViewById(R.id.movieGenre);
            eventCard = itemView.findViewById(R.id.eventcard);
        }
    }


}
