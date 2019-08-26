package com.example.coolfood;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginButton;
    private TextView signUpTextView;
    private EditText emailText;
    private EditText passwordText;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginBtn);
        signUpTextView = findViewById(R.id.signUpTextView);
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            Intent homeIntent = new Intent(this, HomeActivity.class);
            startActivity(homeIntent);
        }

        loginButton.setOnClickListener(this);
        signUpTextView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == loginButton){
            login();
        }
        if(v == signUpTextView){
            //finish(); // proveriti tacno sta znaci finish()

            Intent signUpIntent = new Intent(this, SignUpActivity.class);
            startActivity(signUpIntent);
        }

    }

    private void login() {
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,R.string.enter_email_please, Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,R.string.enter_password_please, Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                }
                else{
                    Toast.makeText(LoginActivity.this,R.string.wrong, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
