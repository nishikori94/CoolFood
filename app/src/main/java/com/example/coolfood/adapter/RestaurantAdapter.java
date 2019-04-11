package com.example.coolfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coolfood.R;
import com.example.coolfood.StoreDetailsActivity;
import com.example.coolfood.model.Restaurant;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

     private List<Restaurant> restaurantList;
     private Context context;
     private ImageButton infoImageButton;
     private OnRestaurantListener aOnRestaurantListener;

    public RestaurantAdapter(List<Restaurant> restaurantList, Context context, OnRestaurantListener onRestaurantListener) {
        this.restaurantList = restaurantList;
        this.context = context;
        this.aOnRestaurantListener=onRestaurantListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.store_in_list_layout, viewGroup, false);
        return new ViewHolder(v,aOnRestaurantListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Restaurant restaurant = restaurantList.get(i);
        viewHolder.nameTV.setText(restaurant.getName());
        viewHolder.descriptionTV.setText(restaurant.getDescription());
        viewHolder.imageView.setImageResource(restaurant.getImgUrl());

        viewHolder.infoImageButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, StoreDetailsActivity.class);
            context.startActivity(intent);
        }
    });
}

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nameTV;
        public TextView descriptionTV;
        public ImageView imageView;
        public ImageButton infoImageButton;
        OnRestaurantListener onRestaurantListener;

        public ViewHolder(@NonNull View itemView, OnRestaurantListener onRestaurantListener) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.restaurantName);
            descriptionTV = itemView.findViewById(R.id.restaurantDesc);
            imageView = itemView.findViewById(R.id.restaurantImage);
            infoImageButton = itemView.findViewById(R.id.infoButton);
            this.onRestaurantListener=onRestaurantListener;

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            onRestaurantListener.onRestaurantClick(getAdapterPosition());
        }
    }
    public interface OnRestaurantListener{
        void onRestaurantClick(int position);
    }
}
