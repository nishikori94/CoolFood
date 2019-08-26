package com.example.coolfood;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.coolfood.model.Review;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener {

    private Button submitReviewBTN;
    private TextView cancelReviewBTN;
    private Spinner reviewSpinner;
    private TextView reviewTextTV;

    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        submitReviewBTN = findViewById(R.id.submitReviewBTN);
        cancelReviewBTN = findViewById(R.id.cancelReviewBTN);
        submitReviewBTN.setOnClickListener(this);
        cancelReviewBTN.setOnClickListener(this);
        reviewTextTV = findViewById(R.id.reviewTextTV);

        reviewSpinner = findViewById(R.id.review_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.review_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reviewSpinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v == submitReviewBTN) {

            int spinner_pos = reviewSpinner.getSelectedItemPosition();
            String[] size_values = getResources().getStringArray(R.array.review_spinner_values);
            String rating = size_values[spinner_pos];

            Intent intent = getIntent();

            firebaseAuth = FirebaseAuth.getInstance();
            user = firebaseAuth.getCurrentUser();
            Review review = new Review(user.getEmail(), reviewTextTV.getText().toString(), rating, intent.getStringExtra("restaurantId"));

            databaseReference = FirebaseDatabase.getInstance().getReference("Review");
            databaseReference.child(String.valueOf(System.currentTimeMillis())).setValue(review);

            databaseReference = FirebaseDatabase.getInstance().getReference("OrderHistory");
            databaseReference.child(intent.getStringExtra("orderId")).child("reviewed").setValue(true);

            intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
        if (v == cancelReviewBTN) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }
}
