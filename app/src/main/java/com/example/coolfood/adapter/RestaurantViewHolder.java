package com.example.coolfood.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coolfood.R;

public class RestaurantViewHolder extends RecyclerView.ViewHolder {
    public TextView nameTV;
    public TextView descriptionTV;
    public ImageView imageView;

    public RestaurantViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTV = itemView.findViewById(R.id.restaurantName);
        descriptionTV = itemView.findViewById(R.id.restaurantDesc);
        imageView = itemView.findViewById(R.id.restaurantImage);
    }
}
