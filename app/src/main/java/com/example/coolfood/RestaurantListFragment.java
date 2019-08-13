package com.example.coolfood;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
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

import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.coolfood.adapter.RestaurantViewHolder;
import com.example.coolfood.database.Database;
import com.example.coolfood.model.Favourites;
import com.example.coolfood.model.Restaurant;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantListFragment extends Fragment {

    private RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Restaurant> options;
    FirebaseRecyclerAdapter<Restaurant, RestaurantViewHolder> adapter;
    FirebaseRecyclerAdapter<Restaurant, RestaurantViewHolder> searchAdapter;

    private FirebaseAuth firebaseAuth;

    Database localDB;


    public RestaurantListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        recyclerView = view.findViewById(R.id.homeRecyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Restaurant");

        localDB = new Database(getContext());

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        options = new FirebaseRecyclerOptions.Builder<Restaurant>().setQuery(databaseReference, Restaurant.class).build();

        adapter = new FirebaseRecyclerAdapter<Restaurant, RestaurantViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RestaurantViewHolder holder, final int position, @NonNull final Restaurant model) {
                holder.nameTV.setText(model.getName());
                holder.descriptionTV.setText(model.getDescription());
                Picasso.get().load(model.getImgUrl()).into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                int yellow = ResourcesCompat.getColor(getResources(), R.color.yellow, null);
                if(localDB.isFavourite(adapter.getRef(position).getKey()))
                    holder.favBtn.setColorFilter(yellow);

                holder.favBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int yellow = ResourcesCompat.getColor(getResources(), R.color.yellow, null);
                        int grey = ResourcesCompat.getColor(getResources(), R.color.grey, null);
                        Favourites favourites = new Favourites();
                        favourites.setAddress(model.getAddress());
                        favourites.setDescription(model.getDescription());
                        favourites.setImgUrl(model.getImgUrl());
                        favourites.setName(model.getName());
                        favourites.setRestaurantId(adapter.getRef(position).getKey());

                        firebaseAuth = FirebaseAuth.getInstance();
                        final FirebaseUser user = firebaseAuth.getCurrentUser();
                        favourites.setUser(user.getEmail());

                        if(!localDB.isFavourite(adapter.getRef(position).getKey())){
                            localDB.addToFavourites(favourites);
                            holder.favBtn.setColorFilter(yellow);
                            Toast.makeText(getContext(), "Added to favourites!", Toast.LENGTH_SHORT);
                        }else {
                            localDB.removeFromFavourites(adapter.getRef(position).getKey());
                            holder.favBtn.setColorFilter(grey);
                            Toast.makeText(getContext(), "Removed from favourites!", Toast.LENGTH_SHORT);
                        }
                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), OffersActivity.class);      //Ovde ide putExtra ko na UPP
                        Bundle extras = new Bundle();
                        extras.putString("storeId", adapter.getRef(position).getKey());
                        extras.putString("restaurantName", model.getName());
                        extras.putString("restaurantAddress", model.getAddress());
                        intent.putExtras(extras);
                        startActivity(intent);
                    }
                });

                holder.infoImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), StoreDetailsActivity.class);
                        intent.putExtra("storeId", adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.store_in_list_layout, viewGroup, false);
                return new RestaurantViewHolder(view);
            }
        };

        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Inflate the layout for this fragment
        adapter.startListening();
        recyclerView.setAdapter(adapter);

        setHasOptionsMenu(true);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null)
            adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null)
            adapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.startListening();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
//                searchAdapter.stopListening();
  //              adapter.startListening();
                recyclerView.setAdapter(adapter);
                return true;
            }
        });

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Restaurant");
                Query query = databaseReference.orderByChild("name").equalTo(s);//startAt(s.toUpperCase()).endAt(s.toLowerCase() + "\uf8ff");
                options = new FirebaseRecyclerOptions.Builder<Restaurant>().setQuery(query, Restaurant.class).build();
                searchAdapter = new FirebaseRecyclerAdapter<Restaurant, RestaurantViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull RestaurantViewHolder holder, final int position, @NonNull final Restaurant model) {
                        holder.nameTV.setText(model.getName());
                        holder.descriptionTV.setText(model.getDescription());
                        Picasso.get().load(model.getImgUrl()).into(holder.imageView, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), OffersActivity.class);      //Ovde ide putExtra ko na UPP
                                Bundle extras = new Bundle();
                                extras.putString("storeId", adapter.getRef(position).getKey());
                                extras.putString("restaurantName", model.getName());
                                extras.putString("restaurantAddress", model.getAddress());
                                intent.putExtras(extras);
                                startActivity(intent);
                            }
                        });

                        holder.infoImageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), StoreDetailsActivity.class);
                                intent.putExtra("storeId", adapter.getRef(position).getKey());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.store_in_list_layout, viewGroup, false);
                        return new RestaurantViewHolder(view);
                    }
                };



                searchAdapter.startListening();
                recyclerView.setAdapter(searchAdapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


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

//    @Override
//    public void onRestaurantClick(int position) {
//        restaurantList.get(position);
//        Intent intent = new Intent(getContext(), OffersActivity.class);      //Ovde ide putExtra ko na UPP
//        startActivity(intent);
//    }
}
