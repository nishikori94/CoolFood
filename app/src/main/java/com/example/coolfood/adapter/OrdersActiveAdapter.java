package com.example.coolfood.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coolfood.R;
import com.example.coolfood.model.Order;

import java.util.List;

public class OrdersActiveAdapter extends RecyclerView.Adapter<OrdersActiveAdapter.ViewHolder> {

    private List<Order> orderList;
    private Context context;

    public OrdersActiveAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView storeName;
        public TextView pickupTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            storeName = itemView.findViewById(R.id.storeName);
            pickupTime = itemView.findViewById(R.id.pickupTime);
        }
    }

    @NonNull
    @Override
    public OrdersActiveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.active_order_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersActiveAdapter.ViewHolder viewHolder, int i) {
        Order order = orderList.get(i);
        viewHolder.storeName.setText(order.getStoreName());
        viewHolder.pickupTime.setText(order.getPickupFrom() + " - " + order.getGetPickupUntil());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
