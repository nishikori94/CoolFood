package com.example.coolfood.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.coolfood.R;

public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {

    public TextView pickupTimePO;
    public TextView storeNamePO;

    public OrderHistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        pickupTimePO = itemView.findViewById(R.id.pickupTimePO);
        storeNamePO = itemView.findViewById(R.id.storeNamePO);
    }
}
