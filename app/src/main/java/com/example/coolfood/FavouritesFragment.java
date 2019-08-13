package com.example.coolfood;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coolfood.adapter.FavouritesAdapter;
import com.example.coolfood.adapter.RestaurantAdapter;
import com.example.coolfood.adapter.RestaurantViewHolder;
import com.example.coolfood.database.Database;
import com.example.coolfood.model.Favourites;
import com.example.coolfood.model.Restaurant;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFragment extends Fragment {


    private RecyclerView recyclerView;

    FavouritesAdapter favouritesAdapter;

    private FirebaseAuth firebaseAuth;
    FirebaseUser user;

    Database localDB;

    public FavouritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        recyclerView = view.findViewById(R.id.homeRecyclerView);

        localDB = new Database(getContext());
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        favouritesAdapter = new FavouritesAdapter(new Database(getContext()).getAllFavourites(user.getEmail()), getContext(), localDB);

        recyclerView.setAdapter(favouritesAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Inflate the layout for this fragment

        setHasOptionsMenu(true);
        return view;


    }

//    @Override
//    public void onFavouritesClick(int position) {
//        restaurantList.get(position);
//        Intent intent = new Intent(getContext(), OffersActivity.class);      //Ovde ide putExtra ko na UPP
//        startActivity(intent);
//    }
}
