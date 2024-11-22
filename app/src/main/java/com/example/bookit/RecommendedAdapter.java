//package com.example.cinepolis;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.MovieViewHolder> {
//
//    private List<Movie> movieList;
//    private Context context;
//
//    // Constructor to pass movie list and context
//    public RecommendedAdapter(List<Movie> movieList, Context context) {
//        this.movieList = movieList;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        // Inflate the layout for each item in the RecyclerView
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder, parent, false);
//        return new MovieViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
//        // Get the current movie item
//        Movie movie = movieList.get(position);
//
//        // Set the title and image for the current movie
//        holder.nameTxt.setText(movie.getTitle());
//        holder.pic.setImageResource(movie.getImageResource());  // Assuming local image resources
//    }
//
//    @Override
//    public int getItemCount() {
//        // Return the total number of items in the movie list
//        return movieList.size();
//    }
//
//    // ViewHolder class to hold the views for each item
//    public static class MovieViewHolder extends RecyclerView.ViewHolder {
//        TextView nameTxt;
//        ImageView pic;
//
//        public MovieViewHolder(@NonNull View itemView) {
//            super(itemView);
//            // Bind views from the layout
//            nameTxt = itemView.findViewById(R.id.nameTxt);
//            pic = itemView.findViewById(R.id.pic);
//        }
//    }
//}

package com.example.bookit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.MovieViewHolder> {

    private List<Event> movieList;
    private Context context;

    // Constructor to pass movie list and context
    public RecommendedAdapter(List<Event> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder, parent, false);
        return new MovieViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        // Get the current movie item
        Event movie = movieList.get(position);

        // Set the title and poster for the current movie
        holder.nameTxt.setText(movie.getName());

        Picasso.get().load(movie.getPosterUrl()).into(holder.pic);
        holder.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // for now display only the toast
//                Toast.makeText(v.getContext(), "Clicked on " + currentEvent.getName(), Toast.LENGTH_SHORT).show();




                // Start the new activity and pass the event details, including the ID
                Intent intent = new Intent(context, EventDetails.class);

                intent.putExtra("showId", movie.getId()); // Pass the ID
                intent.putExtra("category_id", movie.getCategoryId());

                context.startActivity(intent);


            }
        });

    }


    @Override
    public int getItemCount() {
        // Return the total number of items in the movie list
        return movieList.size();
    }

    // ViewHolder class to hold the views for each item
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView nameTxt;
        ImageView pic;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            // Bind views from the layout
            nameTxt = itemView.findViewById(R.id.nameTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
