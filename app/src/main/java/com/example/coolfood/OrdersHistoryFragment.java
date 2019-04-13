package com.example.coolfood;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coolfood.adapter.OrdersHistoryAdapter;
import com.example.coolfood.model.Order;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersHistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Order> orderList;

    public OrdersHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_orders_history, container, false);
        recyclerView = v.findViewById(R.id.pastOrdersRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        orderList = new ArrayList<>();
        orderList.add(new Order("Restoran 1 - Novi Sad", "21.02.2018.", true));
        orderList.add(new Order("Restoran 1 - Novi Sad", "01.04.2018.", true));
        orderList.add(new Order("Restoran 1 - Novi Sad", "16.01.2019.", false));
        orderList.add(new Order("Restoran 1 - Novi Sad", "27.02.2019.", true));
        orderList.add(new Order("Restoran 1 - Novi Sad", "31.04.2019.", false));
        orderList.add(new Order("Restoran 1 - Novi Sad", "21.02.2018.", true));

        adapter = new OrdersHistoryAdapter(orderList, getContext());
        recyclerView.setAdapter(adapter);

        return v;
    }

}
