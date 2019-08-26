package com.example.coolfood.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coolfood.R;

public class RestaurantViewHolder extends RecyclerView.ViewHolder {
    public TextView nameTV;
    public TextView descriptionTV;
    public ImageView imageView;
    public ImageButton infoImageButton;
    public ImageButton favBtn;

    public RestaurantViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTV = itemView.findViewById(R.id.restaurantName);
        descriptionTV = itemView.findViewById(R.id.restaurantDesc);
        imageView = itemView.findViewById(R.id.restaurantImage);
        infoImageButton = itemView.findViewById(R.id.infoButton);
        favBtn = itemView.findViewById(R.id.favBtn);
    }
}
