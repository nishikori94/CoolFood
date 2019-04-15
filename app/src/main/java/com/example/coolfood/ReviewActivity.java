package com.example.coolfood;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.coolfood.R;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener {

    private Button submitReviewBTN;
    private Button cancelReviewBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        submitReviewBTN = findViewById(R.id.submitReviewBTN);
        cancelReviewBTN = findViewById(R.id.cancelReviewBTN);
        submitReviewBTN.setOnClickListener(this);
        cancelReviewBTN.setOnClickListener(this);
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
