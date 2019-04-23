package com.example.coolfood;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.example.coolfood.adapter.RestaurantAdapter;
import com.example.coolfood.model.Restaurant;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantListFragment extends Fragment implements RestaurantAdapter.OnRestaurantListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Restaurant> restaurantList;

    public RestaurantListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        recyclerView = view.findViewById(R.id.homeRecyclerView);
        restaurantList = new ArrayList<>();

        restaurantList.add(new Restaurant("Restoran 1", "Opis 1 Opis 1Opis 1Opis 1Opis 1Opis 1", R.drawable.restaurant));
        restaurantList.add(new Restaurant("Restoran 2", "Opis 2 v Opis 2 Opis 2 Opis 2Opis 2Opis 2", R.drawable.restaurant));
        restaurantList.add(new Restaurant("Restoran 3", "Opis 3 Opis 3 Opis 3 Opis 3 Opis 3 Opis 3", R.drawable.restaurant));

        adapter = new RestaurantAdapter(restaurantList, getContext(), this);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Inflate the layout for this fragment

        setHasOptionsMenu(true);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                FilterBottomSheetDialog filterBottomSheetDialog = new FilterBottomSheetDialog();
                filterBottomSheetDialog.show(getFragmentManager(), "filterBottomSheet");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRestaurantClick(int position) {
        restaurantList.get(position);
        Intent intent = new Intent(getContext(), OffersActivity.class);      //Ovde ide putExtra ko na UPP
        startActivity(intent);
    }
}
