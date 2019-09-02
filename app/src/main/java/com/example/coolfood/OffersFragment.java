package com.example.coolfood;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coolfood.adapter.OfferViewHolder;
import com.example.coolfood.adapter.RestaurantViewHolder;
import com.example.coolfood.database.Database;
import com.example.coolfood.model.Favourites;
import com.example.coolfood.model.Offer;
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
public class OffersFragment extends Fragment{

    private RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Offer> options;
    FirebaseRecyclerAdapter<Offer, OfferViewHolder> adapter;
    FirebaseRecyclerAdapter<Offer, OfferViewHolder> searchAdapter;

    private FirebaseAuth firebaseAuth;

    private FilterBottomSheetDialog.BottomSheetListener bottomSheetListener;

    public OffersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offers, container, false);

        recyclerView = view.findViewById(R.id.offersRecyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Offer");

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        options = new FirebaseRecyclerOptions.Builder<Offer>().setQuery(databaseReference, Offer.class).build();

        adapter = new FirebaseRecyclerAdapter<Offer, OfferViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final OfferViewHolder holder, final int position, @NonNull final Offer model) {
                holder.textName.setText(model.getName());
                final int mid = model.getPickupFrom().toString().length() / 2; //get the middle of the String
                String[] parts = {model.getPickupFrom().toString().substring(0, mid), model.getPickupFrom().toString().substring(mid)};
                String[] parts1 = {model.getPickupUntil().toString().substring(0, mid), model.getPickupUntil().toString().substring(mid)};
                holder.textPickup.setText(parts[0] + "." + parts[1] + " - " + parts1[0] +"."+parts1[1]);
                holder.textPrice.setText(model.getPrice() + " din.");
                holder.textQuantity.setText(model.getQuantity() + " left");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.parseInt(model.getQuantity()) != 0) {
                            Intent intent = new Intent(v.getContext(), OfferDetailsActivity.class);
                            Bundle extras = new Bundle();
                            //extras.putString("storeId", storeId);
                            extras.putString("offerId", adapter.getRef(position).getKey());
                            //extras.putString("restaurantAddress", restaurantAddress);
                            //extras.putString("restaurantName", getIntent().getStringExtra("restaurantName"));
                            intent.putExtras(extras);
                            startActivity(intent);
                        } else
                            Toast.makeText(getContext(), R.string.no_more, Toast.LENGTH_SHORT).show();
                    }

                });
            }

            @NonNull
            @Override
            public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.offer_in_list_layout, viewGroup, false);
                return new OfferViewHolder(view);
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
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Offer");
                Query query = databaseReference.orderByChild("name").equalTo(s);//startAt(s.toUpperCase()).endAt(s.toLowerCase() + "\uf8ff");
                options = new FirebaseRecyclerOptions.Builder<Offer>().setQuery(query, Offer.class).build();
                searchAdapter = new FirebaseRecyclerAdapter<Offer, OfferViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull OfferViewHolder holder, final int position, @NonNull final Offer model) {
                        holder.textName.setText(model.getName());
                        final int mid = model.getPickupFrom().toString().length() / 2; //get the middle of the String
                        String[] parts = {model.getPickupFrom().toString().substring(0, mid), model.getPickupFrom().toString().substring(mid)};
                        String[] parts1 = {model.getPickupUntil().toString().substring(0, mid), model.getPickupUntil().toString().substring(mid)};
                        holder.textPickup.setText(parts[0] + "." + parts[1] + " - " + parts1[0] +"."+parts1[1]);
                        holder.textPrice.setText(model.getPrice() + " din.");
                        holder.textQuantity.setText(model.getQuantity() + " left");

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (Integer.parseInt(model.getQuantity()) != 0) {
                                    Intent intent = new Intent(v.getContext(), OfferDetailsActivity.class);
                                    Bundle extras = new Bundle();
                                    //extras.putString("storeId", storeId);
                                    extras.putString("offerId", adapter.getRef(position).getKey());
                                    //extras.putString("restaurantAddress", restaurantAddress);
                                    //extras.putString("restaurantName", getIntent().getStringExtra("restaurantName"));
                                    intent.putExtras(extras);
                                    startActivity(intent);
                                } else
                                    Toast.makeText(getContext(), R.string.no_more, Toast.LENGTH_SHORT).show();
                            }

                        });
                    }

                    @NonNull
                    @Override
                    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.offer_in_list_layout, viewGroup, false);
                        return new OfferViewHolder(view);
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
            case R.id.settings_item:
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
            case R.id.profile_item:
                Intent intent1 = new Intent(getContext(), AccountActivity.class);
                startActivity(intent1);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateText(String minTime, String maxTime, String quantMin, String quantMax){
        // Here you have it
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Offer");
        Query query = databaseReference.orderByChild("pickupFrom").startAt(Double.parseDouble(minTime)).endAt(Double.parseDouble(maxTime));//startAt(s.toUpperCase()).endAt(s.toLowerCase() + "\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<Offer>().setQuery(query, Offer.class).build();
        searchAdapter = new FirebaseRecyclerAdapter<Offer, OfferViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OfferViewHolder holder, final int position, @NonNull final Offer model) {
                holder.textName.setText(model.getName());

                final int mid = model.getPickupFrom().toString().length() / 2; //get the middle of the String
                String[] parts = {model.getPickupFrom().toString().substring(0, mid), model.getPickupFrom().toString().substring(mid)};
                String[] parts1 = {model.getPickupUntil().toString().substring(0, mid), model.getPickupUntil().toString().substring(mid)};
                holder.textPickup.setText(parts[0] + "." + parts[1] + " - " + parts1[0] +"."+parts1[1]);

                holder.textPrice.setText(model.getPrice() + " din.");
                holder.textQuantity.setText(model.getQuantity() + " left");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.parseInt(model.getQuantity()) != 0) {
                            Intent intent = new Intent(v.getContext(), OfferDetailsActivity.class);
                            Bundle extras = new Bundle();
                            //extras.putString("storeId", storeId);
                            extras.putString("offerId", adapter.getRef(position).getKey());
                            //extras.putString("restaurantAddress", restaurantAddress);
                            //extras.putString("restaurantName", getIntent().getStringExtra("restaurantName"));
                            intent.putExtras(extras);
                            startActivity(intent);
                        } else
                            Toast.makeText(getContext(), R.string.no_more, Toast.LENGTH_SHORT).show();
                    }

                });
            }

            @NonNull
            @Override
            public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.offer_in_list_layout, viewGroup, false);
                return new OfferViewHolder(view);
            }
        };


        searchAdapter.startListening();
        recyclerView.setAdapter(searchAdapter);
    }

}
