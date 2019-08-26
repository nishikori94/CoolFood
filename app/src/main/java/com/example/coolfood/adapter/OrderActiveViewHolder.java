package com.example.coolfood.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coolfood.R;

public class OrderActiveViewHolder extends RecyclerView.ViewHolder {

    public TextView pickupTimeAO;
    public TextView storeNameAO;
    public TextView cancelTV;
    public ImageView qrCodeIcon;

    public OrderActiveViewHolder(@NonNull View itemView) {
        super(itemView);
        pickupTimeAO = itemView.findViewById(R.id.pickupTimeAO);
        storeNameAO = itemView.findViewById(R.id.storeNameAO);
        cancelTV = itemView.findViewById(R.id.cancelTV);
        qrCodeIcon = itemView.findViewById(R.id.qrCodeIcon);
    }
}
