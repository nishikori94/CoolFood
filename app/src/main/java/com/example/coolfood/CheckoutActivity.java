package com.example.coolfood;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coolfood.model.Offer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class CheckoutActivity extends AppCompatActivity {

    private TextView offerNameTV;
    private TextView restaurantAddressTV;
    private TextView pickupTimeTV;
    private ImageView offerIV;
    private String offerId = "";
    private String restaurantAddress = "";
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        offerNameTV = (TextView) findViewById(R.id.offerNameTV);
        restaurantAddressTV = (TextView)findViewById(R.id.restaurantAddressTV);
        pickupTimeTV = (TextView) findViewById(R.id.pickupTimeTV);
        offerIV = (ImageView) findViewById(R.id.offerIV);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (intent != null) {
            offerId = extras.getString("offerId");
            restaurantAddress = extras.getString("restaurantAddress");
        }
        if (!offerId.isEmpty() && offerId != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Offer");
            databaseReference.child(offerId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Offer offer = dataSnapshot.getValue(Offer.class);
                    Picasso.get().load(offer.getImgUrl()).into(offerIV);
                    offerNameTV.setText(offer.getName());
                    pickupTimeTV.setText(offer.getDate() + "  /  " + offer.getPickupFrom() + " - " + offer.getPickupUntil());
                    restaurantAddressTV.setText(restaurantAddress);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
