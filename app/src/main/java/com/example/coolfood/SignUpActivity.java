package com.example.coolfood;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private Button registerButton;
    private TextView signIn;
    private EditText emailText;
    private EditText passwordText;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            Intent homeIntent = new Intent(this, HomeActivity.class);
            startActivity(homeIntent);
        }

        registerButton = findViewById(R.id.registerBtn);
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        signIn = findViewById(R.id.signInTextView);
        progressBar = findViewById(R.id.progressBar);

        registerButton.setOnClickListener(this);
        signIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == signIn){
            //send to login activity
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }
        if(v == registerButton){
            //register
            registerUser();
        }
    }

    private void registerUser(){
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email address", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isComplete()){
                    //Toast.makeText(SignUpActivity.this, "Registrated successfully", Toast.LENGTH_SHORT).show();
                    Intent homeIntent = new Intent(SignUpActivity.this, HomeActivity.class);
                    startActivity(homeIntent);

                }else {
                    Toast.makeText(SignUpActivity.this, "Not registrated. Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
