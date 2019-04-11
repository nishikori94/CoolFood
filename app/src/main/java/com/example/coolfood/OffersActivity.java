package com.example.coolfood;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.coolfood.adapter.OfferAdapter;
import com.example.coolfood.model.Offer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OffersActivity extends AppCompatActivity implements OfferAdapter.OnOfferListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<Offer> offers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        recyclerView = findViewById(R.id.offersRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        offers = new ArrayList<>();
        for (int i=0; i<5; i++){
            Offer offer = new Offer(i, "Offer "+(i+1), Integer.toString(20+i)+":30",Integer.toString(21+i), (i+1)*100);
            offers.add(offer);
        }

        adapter= new OfferAdapter(offers,this, this);
        recyclerView.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onOfferClick(int position) {
        offers.get(position);
        Intent intent = new Intent(this, OfferDetailsActivity.class);       //Ovde ide putExtra
        startActivity(intent);
    }
}
