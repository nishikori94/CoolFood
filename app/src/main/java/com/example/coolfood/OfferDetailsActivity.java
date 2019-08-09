package com.example.coolfood;

import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coolfood.model.Offer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class OfferDetailsActivity extends AppCompatActivity {

    private Button buyBTN;
    private ImageView offerImage;
    private TextView oldPrice, newPrice, offerDescriptionTV, pickupTime, quantityTV;

    DatabaseReference databaseReference;
    private String offerId = "";
    private String restaurantAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        offerImage = findViewById(R.id.offerImageView);
        oldPrice = (TextView) findViewById(R.id.oldPrice);
        oldPrice.setPaintFlags(oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        newPrice = (TextView) findViewById(R.id.newPrice);
        offerDescriptionTV = (TextView) findViewById(R.id.storeDescriptionTV);
        pickupTime = (TextView) findViewById(R.id.pickupTimeAO);
        quantityTV = (TextView) findViewById(R.id.reviewNumTV);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (intent != null) {
            offerId = extras.getString("offerId");
            restaurantAddress = extras.getString("restaurantAddress");
        }
        if (!offerId.isEmpty() && offerId != null) {

            buyBTN = findViewById(R.id.buyBTN);
            buyBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), CheckoutActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("restaurantAddress", restaurantAddress);
                    extras.putString("offerId", offerId);
                    extras.putString("restaurantName", getIntent().getStringExtra("restaurantName"));
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            });

            databaseReference = FirebaseDatabase.getInstance().getReference("Offer");
            databaseReference.child(offerId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Offer offer = dataSnapshot.getValue(Offer.class);
                    Picasso.get().load(offer.getImgUrl()).into(offerImage);
                    oldPrice.setText(offer.getOldPrice() + " din.");
                    newPrice.setText(offer.getPrice() + " din.");
                    offerDescriptionTV.setText(offer.getDescription());
                    pickupTime.setText(offer.getDate() + "  /  " + offer.getPickupFrom() + " - " + offer.getPickupUntil());
                    quantityTV.setText(offer.getQuantity() + " left");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
