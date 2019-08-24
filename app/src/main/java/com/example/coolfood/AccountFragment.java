package com.example.coolfood;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coolfood.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {


    public TextView emailTV;
    public TextView logoutTV;
    public TextView nameSurname;
    public TextView phoneNumberTV;
    public ImageView profileIV;
    public TextView settingsTV;

    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser user;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        emailTV = view.findViewById(R.id.emailTV);
        logoutTV = view.findViewById(R.id.logoutTV);
        nameSurname = view.findViewById(R.id.nameSurname);
        phoneNumberTV = view.findViewById(R.id.phoneNumberTV);
        profileIV = view.findViewById(R.id.profileIV);
        settingsTV = view.findViewById(R.id.settingsTV);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.orderByChild("email").equalTo(user.getEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsChild : dataSnapshot.getChildren()) {
                    User user = dsChild.getValue(User.class);

                    Picasso.get().load(user.getImgUrl()).into(profileIV);
                    emailTV.setText(user.getEmail());
                    nameSurname.setText(user.getName() + " " + user.getSurname());
                    phoneNumberTV.setText(user.getNumber());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        logoutTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(v.getContext(), LoginActivity.class));
            }
        });

        settingsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), SettingsActivity.class));
            }
        });

        return view;


    }

}
