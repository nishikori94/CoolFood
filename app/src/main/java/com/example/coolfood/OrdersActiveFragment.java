package com.example.coolfood;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coolfood.adapter.OrderAdapter;
import com.example.coolfood.model.Order;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersActiveFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Order> orderList;

    public OrdersActiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_orders_active, container, false);
        recyclerView = v.findViewById(R.id.activeOrdersRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        orderList = new ArrayList<>();
        orderList.add(new Order("Restoran 1 - Novi Sad", "17:00", "17:15"));
        orderList.add(new Order("Restoran 2 - Beograd", "11:00", "11:55"));
        orderList.add(new Order("Restoran 3 - Nis", "13:00", "13:15"));
        orderList.add(new Order("Restoran 4 - Novi Sad", "16:00", "17:15"));

        adapter = new OrderAdapter(orderList, getContext());
        recyclerView.setAdapter(adapter);

        return v;
    }

}
