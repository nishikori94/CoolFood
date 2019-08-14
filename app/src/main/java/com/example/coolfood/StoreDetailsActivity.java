package com.example.coolfood;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coolfood.adapter.ReviewAdapter;
import com.example.coolfood.adapter.ReviewViewHolder;
import com.example.coolfood.model.Restaurant;
import com.example.coolfood.model.Review;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StoreDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    DatabaseReference databaseReferenceRestaurant;
    DatabaseReference databaseReferenceReview;
    FirebaseRecyclerOptions<Review> options;
    FirebaseRecyclerAdapter<Review, ReviewViewHolder> adapter;

    private TextView storeNameTV, storeDescriptionTV, reviewNumTV;
    private ImageView storeImageView;
    private String storeId = "";
    private double avgRating = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        storeNameTV = findViewById(R.id.storeNameTV);
        storeDescriptionTV = findViewById(R.id.storeDescriptionTV);
        storeImageView = findViewById(R.id.storeImageView);
        reviewNumTV = findViewById(R.id.reviewNumTV);

        Intent intent = getIntent();
        if (intent != null) {
            storeId = intent.getStringExtra("storeId");
        }
        if (!storeId.isEmpty() && storeId != null) {
            databaseReferenceRestaurant = FirebaseDatabase.getInstance().getReference("Restaurant");
            databaseReferenceRestaurant.child(storeId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Restaurant restaurant = dataSnapshot.getValue(Restaurant.class);
                    Picasso.get().load(restaurant.getImgUrl()).into(storeImageView);
                    storeNameTV.setText(restaurant.getName());
                    storeDescriptionTV.setText(restaurant.getDescription());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            databaseReferenceReview = FirebaseDatabase.getInstance().getReference("Review");
            Query query = databaseReferenceReview.orderByChild("restaurantId").equalTo(storeId);
            options = new FirebaseRecyclerOptions.Builder<Review>().setQuery(query, Review.class).build();
            adapter = new FirebaseRecyclerAdapter<Review, ReviewViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull ReviewViewHolder holder, int position, @NonNull Review model) {
                    holder.userTV.setText(model.getUser());
                    holder.reviewTV.setText(model.getReviewText());
                    holder.ratingBar.setRating(Float.parseFloat(model.getRating()));
                    avgRating += Double.parseDouble(model.getRating());
                    double avg = avgRating/adapter.getItemCount();
                    reviewNumTV.setText(String.format("%.2f", avg) + " (" + adapter.getItemCount() + " reviews)");
                }

                @NonNull
                @Override
                public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_layout, viewGroup, false);
                    return new ReviewViewHolder(view);
                }
            };
        }
        recyclerView = findViewById(R.id.reviewsRecyclerView);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(this, HomeActivity.class);
        startActivity(myIntent);
        return true;
    }
}
