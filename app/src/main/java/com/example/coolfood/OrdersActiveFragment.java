package com.example.coolfood;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coolfood.adapter.OrderActiveViewHolder;
import com.example.coolfood.adapter.OrderHistoryViewHolder;
import com.example.coolfood.model.Order;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersActiveFragment extends Fragment {

    private RecyclerView recyclerView;

    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Order> options;
    FirebaseRecyclerAdapter<Order, OrderActiveViewHolder> adapter;
    private FirebaseAuth firebaseAuth;

    public OrdersActiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_orders_active, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("OrderActive");
        Query query = databaseReference.orderByChild("user").equalTo(user.getEmail());
        options = new FirebaseRecyclerOptions.Builder<Order>().setQuery(query, Order.class).build();

        adapter = new FirebaseRecyclerAdapter<Order, OrderActiveViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderActiveViewHolder holder, int position, @NonNull Order model) {
                holder.storeNameAO.setText(model.getStoreName());
                holder.pickupTimeAO.setText(model.getPickupFrom() + " - " + model.getGetPickupUntil());
            }

            @NonNull
            @Override
            public OrderActiveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.active_order_layout, viewGroup, false);
                return new OrderActiveViewHolder(view);
            }
        };

        recyclerView = v.findViewById(R.id.activeOrdersRecyclerView);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.startListening();
        recyclerView.setAdapter(adapter);

        return v;
    }

}
