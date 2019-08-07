package com.example.coolfood;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coolfood.adapter.OfferAdapter;
import com.example.coolfood.adapter.OfferViewHolder;
import com.example.coolfood.model.Offer;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OffersActivity extends AppCompatActivity implements OfferAdapter.OnOfferListener {

    private RecyclerView recyclerView;
    //private RecyclerView.Adapter adapter;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Offer> options;
    FirebaseRecyclerAdapter<Offer, OfferViewHolder> adapter;
    private List<Offer> offers;
    private String storeId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        if (getIntent() != null)
            storeId = getIntent().getStringExtra("storeId");
        if (!storeId.isEmpty() && storeId != null) {

            databaseReference = FirebaseDatabase.getInstance().getReference("Offer");
            Query query = databaseReference.orderByChild("restaurantId").equalTo(storeId);
            options = new FirebaseRecyclerOptions.Builder<Offer>().setQuery(query, Offer.class).build();
            adapter = new FirebaseRecyclerAdapter<Offer, OfferViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull OfferViewHolder holder, int position, @NonNull Offer model) {
                    holder.textName.setText(model.getName());
                    holder.textPickup.setText(model.getPickupFrom() + " - " + model.getPickupUntil());
                    holder.textPrice.setText(model.getPrice());
                    holder.textQuantity.setText(model.getQuantity());
                }

                @NonNull
                @Override
                public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.offer_in_list_layout, viewGroup, false);

                    return new OfferViewHolder(view);
                }
            };
        }

        recyclerView = findViewById(R.id.offersRV);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

//        offers = new ArrayList<>();
//        for (int i=0; i<5; i++){
//            Offer offer = new Offer(i, "Offer "+(i+1), Integer.toString(20+i)+":30",Integer.toString(21+i), (i+1)*100);
//            offers.add(offer);
//        }

        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onOfferClick(int position) {
        offers.get(position);
        Intent intent = new Intent(this, OfferDetailsActivity.class);       //Ovde ide putExtra
        startActivity(intent);
    }
}
