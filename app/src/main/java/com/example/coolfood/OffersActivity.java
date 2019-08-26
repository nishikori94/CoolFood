package com.example.coolfood;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coolfood.adapter.OfferViewHolder;
import com.example.coolfood.model.Offer;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class OffersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Offer> options;
    FirebaseRecyclerAdapter<Offer, OfferViewHolder> adapter;
    private String storeId = "";
    private String restaurantAddress = "";

    private TextView restaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        restaurantName = findViewById(R.id.restaurantNameTV);

        if (intent != null && extras != null) {
            storeId = extras.getString("storeId");
            restaurantAddress = extras.getString("restaurantAddress");
            restaurantName.setText(getIntent().getStringExtra("restaurantName"));
        }
        if (!storeId.isEmpty() && storeId != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Offer");
            Query query = databaseReference.orderByChild("restaurantId").equalTo(storeId);
            options = new FirebaseRecyclerOptions.Builder<Offer>().setQuery(query, Offer.class).build();
            adapter = new FirebaseRecyclerAdapter<Offer, OfferViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull OfferViewHolder holder, final int position, @NonNull final Offer model) {
                    holder.textName.setText(model.getName());
                    holder.textPickup.setText(model.getPickupFrom() + " - " + model.getPickupUntil());
                    holder.textPrice.setText(model.getPrice() + " din.");
                    holder.textQuantity.setText(model.getQuantity() + " left");


                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Integer.parseInt(model.getQuantity()) != 0) {
                                Intent intent = new Intent(v.getContext(), OfferDetailsActivity.class);
                                Bundle extras = new Bundle();
                                extras.putString("storeId", storeId);
                                extras.putString("offerId", adapter.getRef(position).getKey());
                                extras.putString("restaurantAddress", restaurantAddress);
                                extras.putString("restaurantName", getIntent().getStringExtra("restaurantName"));
                                intent.putExtras(extras);
                                startActivity(intent);
                            } else
                                Toast.makeText(getApplicationContext(), R.string.no_more, Toast.LENGTH_SHORT).show();
                        }

                    });

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

}
