package com.example.coolfood;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coolfood.adapter.OrderHistoryViewHolder;
import com.example.coolfood.model.Order;
import com.example.coolfood.model.Review;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersHistoryFragment extends Fragment {

    private RecyclerView recyclerView;

    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Order> options;
    FirebaseRecyclerAdapter<Order, OrderHistoryViewHolder> adapter;
    private FirebaseAuth firebaseAuth;
    private boolean exists = false;

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

        FirebaseDatabase.getInstance().getReference().child("Review").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Review review = snapshot.getValue(Review.class);
                    if (review.getUser().equals(user.getEmail())) {
                        exists = true;
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new FirebaseRecyclerAdapter<Order, OrderHistoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position, @NonNull final Order model) {
                holder.storeNamePO.setText(model.getStoreName());

                final int mid = model.getPickupFrom().toString().length() / 2; //get the middle of the String
                String[] parts = {model.getPickupFrom().toString().substring(0, mid), model.getPickupFrom().toString().substring(mid)};
                String[] parts1 = {model.getGetPickupUntil().toString().substring(0, mid), model.getGetPickupUntil().toString().substring(mid)};
                holder.pickupTimePO.setText(parts[0] + "." + parts[1] + " - " + parts1[0] +"."+parts1[1]);

                if (model.isReviewed())
                    holder.reviewOrderButton.setVisibility(View.GONE);
                else
                    holder.reviewOrderButton.setVisibility(View.VISIBLE);
                holder.reviewOrderButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), ReviewActivity.class);
                        Bundle extras = new Bundle();
                        extras.putString("restaurantId", model.getRestaurantId());
                        extras.putString("orderId", model.getOrderId());
                        intent.putExtras(extras);
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
