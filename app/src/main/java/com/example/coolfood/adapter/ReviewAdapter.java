package com.example.coolfood.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coolfood.R;
import com.example.coolfood.model.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<Review> reviewList;
    private Context context;

    public ReviewAdapter(List<Review> reviewList, Context context) {
        this.reviewList = reviewList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView user;
        public TextView reviewText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user = (TextView)itemView.findViewById(R.id.userTV);
            reviewText = (TextView)itemView.findViewById(R.id.reviewTV);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Review review = reviewList.get(i);
        viewHolder.user.setText(review.getUser());
        viewHolder.reviewText.setText(review.getReviewText());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }



}
