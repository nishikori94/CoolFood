package com.example.coolfood;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.coolfood.adapter.ReviewAdapter;
import com.example.coolfood.model.Restaurant;
import com.example.coolfood.model.Review;

import java.util.ArrayList;
import java.util.List;

public class StoreDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Review> reviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.reviewsRecyclerView);
        reviewList = new ArrayList<>();
        reviewList.add(new Review("Milos Nisic", "Ocaj zivi. Nasao dlaku u prasetini."));
        reviewList.add(new Review("Vuk Boskovic", "Odlicno! Obozavam skembice"));
        reviewList.add(new Review("Bojan Sovljanski", "Fuj! Kako mozete da jedete jadne zivotinje. GO VEGAN!"));

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
