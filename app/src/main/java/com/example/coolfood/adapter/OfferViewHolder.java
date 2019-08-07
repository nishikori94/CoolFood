package com.example.coolfood.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.coolfood.R;

public class OfferViewHolder extends RecyclerView.ViewHolder {

    public TextView textPrice;
    public TextView textQuantity;
    public TextView textName;
    public TextView textPickup;

    public OfferViewHolder(@NonNull View itemView) {
        super(itemView);
        textPrice = itemView.findViewById(R.id.foodPrice);
        textQuantity = itemView.findViewById(R.id.left);
        textName = itemView.findViewById(R.id.foodName);
        textPickup = itemView.findViewById(R.id.pickupTimeTV);
    }
}
