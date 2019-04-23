package com.example.coolfood;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.coolfood.R;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener {

    private Button submitReviewBTN;
    private TextView cancelReviewBTN;
    private Spinner reviewSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        submitReviewBTN = findViewById(R.id.submitReviewBTN);
        cancelReviewBTN = findViewById(R.id.cancelReviewBTN);
        submitReviewBTN.setOnClickListener(this);
        cancelReviewBTN.setOnClickListener(this);

        reviewSpinner = findViewById(R.id.review_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.review_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reviewSpinner.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        if(v == submitReviewBTN){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
        if(v == cancelReviewBTN){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }
}
