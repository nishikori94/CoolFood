package com.example.coolfood.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.coolfood.R;

public class OrderActiveViewHolder extends RecyclerView.ViewHolder {

    public TextView pickupTimeAO;
    public TextView storeNameAO;

    public OrderActiveViewHolder(@NonNull View itemView) {
        super(itemView);
        pickupTimeAO = itemView.findViewById(R.id.pickupTimeAO);
        storeNameAO = itemView.findViewById(R.id.storeNameAO);
    }
}
