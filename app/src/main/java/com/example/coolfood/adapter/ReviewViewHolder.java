package com.example.coolfood.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.coolfood.R;

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    public TextView userTV;
    public RatingBar ratingBar;
    public TextView reviewTV;


    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        userTV = itemView.findViewById(R.id.userTV);
        reviewTV = itemView.findViewById(R.id.reviewTV);
        ratingBar = itemView.findViewById(R.id.ratingBar);
    }
}
