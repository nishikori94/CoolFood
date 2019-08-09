package com.example.coolfood.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coolfood.R;

public class OfferViewHolder extends RecyclerView.ViewHolder {

    public TextView textPrice;
    public TextView textQuantity;
    public TextView textName;
    public TextView textPickup;
    public ImageView imageView;
    public TextView description;
    public TextView textOldPrice;
    public TextView myImageTextView;
    public TextView newPrice;
    public TextView pickupTime;

    public OfferViewHolder(@NonNull View itemView) {
        super(itemView);
        textPrice = itemView.findViewById(R.id.foodPrice);
        textQuantity = itemView.findViewById(R.id.left);
        textName = itemView.findViewById(R.id.foodName);
        textPickup = itemView.findViewById(R.id.pickupTimeTV);
        description = itemView.findViewById(R.id.storeDescriptionTV);
        imageView = itemView.findViewById(R.id.offerImageView);
        textOldPrice = itemView.findViewById(R.id.oldPrice);
        myImageTextView = itemView.findViewById(R.id.storeNameTV);
        newPrice = itemView.findViewById(R.id.newPrice);
        pickupTime = itemView.findViewById(R.id.pickupTimeAO);
    }
}
