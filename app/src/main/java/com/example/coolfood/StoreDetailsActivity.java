package com.example.coolfood;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coolfood.adapter.ReviewAdapter;
import com.example.coolfood.model.Restaurant;
import com.example.coolfood.model.Review;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StoreDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Review> reviewList;

    DatabaseReference databaseReference;

    private TextView storeNameTV, storeDescriptionTV;
    private ImageView storeImageView;
    private String storeId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        storeNameTV = findViewById(R.id.storeNameTV);
        storeDescriptionTV = findViewById(R.id.storeDescriptionTV);
        storeImageView = findViewById(R.id.storeImageView);

        Intent intent = getIntent();
        if (intent != null) {
            storeId = intent.getStringExtra("storeId");
        }
        if (!storeId.isEmpty() && storeId != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Offer");
            databaseReference.child(storeId).addValueEventListener(new ValueEventListener() {
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
        }
        recyclerView = findViewById(R.id.reviewsRecyclerView);
        reviewList = new ArrayList<>();
        reviewList.add(new Review("Milos Nisic", "FAN TAZIJA"));
        reviewList.add(new Review("Vuk Boskovic", "Odlicno! Bas je bilo superiska"));
        reviewList.add(new Review("Bojan Sovljanski", "Sve preporuke"));

        adapter = new ReviewAdapter(reviewList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(this, HomeActivity.class);
        startActivity(myIntent);
        return true;
    }
}
