package com.example.coolfood;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class OrdersHistoryFragment extends Fragment {

    private RecyclerView recyclerView;

    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Order> options;
    FirebaseRecyclerAdapter<Order, OrderHistoryViewHolder> adapter;
    private FirebaseAuth firebaseAuth;

    public OrdersHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_orders_history, container, false);
        recyclerView = v.findViewById(R.id.pastOrdersRecyclerView);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("OrderHistory");
        Query query = databaseReference.orderByChild("user").equalTo(user.getEmail());
        options = new FirebaseRecyclerOptions.Builder<Order>().setQuery(query, Order.class).build();
        adapter = new FirebaseRecyclerAdapter<Order, OrderHistoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position, @NonNull Order model) {
                holder.storeNamePO.setText(model.getStoreName());
                holder.pickupTimePO.setText(model.getPickupFrom() + " - " + model.getGetPickupUntil());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), ReviewActivity.class);      //Ovde ide putExtra ko na UPP
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.past_order_layout, viewGroup, false);
                return new OrderHistoryViewHolder(view);
            }
        };

        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.startListening();
        recyclerView.setAdapter(adapter);
        return v;
    }


}
