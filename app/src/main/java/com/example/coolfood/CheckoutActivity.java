package com.example.coolfood;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coolfood.model.Offer;
import com.example.coolfood.model.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Calendar;

public class CheckoutActivity extends AppCompatActivity {

    private TextView offerNameTV;
    private TextView restaurantAddressTV;
    private TextView pickupTimeTV;
    private ImageView offerIV;
    private TextView quantityCounterTV;
    private ImageButton decQuantityIB;
    private ImageButton incQuantityIB;
    private Button confirmBtn;
    private TextView totalPriceTV;

    private String offerId = "";
    private String restaurantAddress = "";
    private int price = 0;
    DatabaseReference databaseReference;
    DatabaseReference orderDatabaseRef;
    private FirebaseAuth firebaseAuth;
    int maxQuantity = 0;
    private Offer offer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        offerNameTV = (TextView) findViewById(R.id.offerNameTV);
        restaurantAddressTV = (TextView) findViewById(R.id.restaurantAddressTV);
        pickupTimeTV = (TextView) findViewById(R.id.pickupTimeTV);
        offerIV = (ImageView) findViewById(R.id.offerIV);
        quantityCounterTV = findViewById(R.id.quantityCounterTV);
        decQuantityIB = findViewById(R.id.decQuantityIB);
        decQuantityIB.setEnabled(false);
        decQuantityIB.setColorFilter(getColor(R.color.grey));
        incQuantityIB = findViewById(R.id.incQuantityIB);
        confirmBtn = findViewById(R.id.confirmBtn);
        confirmBtn.setEnabled(false);
        decQuantityIB.setColorFilter(getColor(R.color.grey));
        totalPriceTV = findViewById(R.id.totalPriceTV);

        quantityCounterTV.setText("0");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        orderDatabaseRef = FirebaseDatabase.getInstance().getReference("OrderActive");

        if (intent != null) {
            offerId = extras.getString("offerId");
            restaurantAddress = extras.getString("restaurantAddress");
        }
        if (!offerId.isEmpty() && offerId != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Offer");
            databaseReference.child(offerId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    offer = dataSnapshot.getValue(Offer.class);
                    Picasso.get().load(offer.getImgUrl()).into(offerIV);
                    offerNameTV.setText(offer.getName());
                    pickupTimeTV.setText(offer.getDate() + "  /  " + offer.getPickupFrom() + " - " + offer.getPickupUntil());
                    restaurantAddressTV.setText(restaurantAddress);
                    maxQuantity = Integer.parseInt(offer.getQuantity());
                    price = Integer.parseInt(offer.getPrice());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        incQuantityIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(quantityCounterTV.getText().toString());
                if (quantity < maxQuantity) {
                    quantity += 1;
                    confirmBtn.setEnabled(true);
                    decQuantityIB.setColorFilter(getColor(R.color.colorPrimaryDark));
                }
                if (quantity > 0) {
                    decQuantityIB.setEnabled(true);
                    decQuantityIB.setColorFilter(getColor(R.color.colorPrimaryDark));
                }
                if (quantity == maxQuantity) {
                    incQuantityIB.setEnabled(false);
                    incQuantityIB.setColorFilter(getColor(R.color.grey));
                }

                quantityCounterTV.setText(Integer.toString(quantity));
                totalPriceTV.setText("Total: " + Integer.toString(quantity * price) + " din.");
            }
        });

        decQuantityIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(quantityCounterTV.getText().toString());
                if (quantity > 1)
                    quantity -= 1;
                if (quantity == 1) {
                    decQuantityIB.setEnabled(false);
                    decQuantityIB.setColorFilter(getColor(R.color.grey));
                }
                if (quantity < maxQuantity) {
                    incQuantityIB.setEnabled(true);
                    incQuantityIB.setColorFilter(getColor(R.color.colorPrimaryDark));
                }
                quantityCounterTV.setText(Integer.toString(quantity));
                totalPriceTV.setText("Total: " + Integer.toString(quantity * price) + " din.");
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = new Order(getIntent().getStringExtra("restaurantName"), user.getEmail(), offer.getPickupFrom(), offer.getPickupUntil(), Integer.toString(price * Integer.parseInt(quantityCounterTV.getText().toString())), offer.getName(), Calendar.getInstance().getTime().toString(), false, quantityCounterTV.getText().toString(), true);
                orderDatabaseRef.child(String.valueOf(System.currentTimeMillis())).setValue(order);
                Toast.makeText(getApplicationContext(), "Thank you, order placed!", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(myIntent);
            }
        });


    }
}
