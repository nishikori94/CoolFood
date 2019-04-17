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
import com.example.coolfood.model.Restaurant;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFragment extends Fragment implements FavouritesAdapter.OnFavouritesListener {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Restaurant> restaurantList;


    public FavouritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        recyclerView = view.findViewById(R.id.homeRecyclerView);
        restaurantList = new ArrayList<>();

        restaurantList.add(new Restaurant("Restoran 1", "Opis 1 Opis 1Opis 1Opis 1Opis 1Opis 1", R.drawable.restaurant));

        adapter = new FavouritesAdapter(restaurantList, getContext(),this);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Inflate the layout for this fragment

        setHasOptionsMenu(true);
        return view;



    }

    @Override
    public void onFavouritesClick(int position) {
        restaurantList.get(position);
        Intent intent = new Intent(getContext(),OffersActivity.class);      //Ovde ide putExtra ko na UPP
        startActivity(intent);
    }
}
