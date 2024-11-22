package com.example.bookit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder> {

    private List<Integer> images;
    private Context context;

    public ViewPagerAdapter(Context context, List<Integer> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public PagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_viewpager, parent, false);
        return new PagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PagerViewHolder holder, int position) {
        holder.imageView.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class PagerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public PagerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.viewpager_image);
        }
    }
}
