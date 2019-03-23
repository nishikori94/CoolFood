package com.example.coolfood.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coolfood.R;
import com.example.coolfood.model.Restaurant;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

     private List<Restaurant> restaurantList;
     private Context context;

    public RestaurantAdapter(List<Restaurant> restaurantList, Context context) {
        this.restaurantList = restaurantList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.restoraunt_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Restaurant restaurant = restaurantList.get(i);
        viewHolder.nameTV.setText(restaurant.getName());
        viewHolder.descriptionTV.setText(restaurant.getDescription());
        viewHolder.imageView.setImageResource(restaurant.getImgUrl());
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTV;
        public TextView descriptionTV;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.restaurantName);
            descriptionTV = itemView.findViewById(R.id.restaurantDesc);
            imageView = itemView.findViewById(R.id.restaurantImage);
        }
    }
}
