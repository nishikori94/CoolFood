package com.example.coolfood.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.coolfood.R;
import com.example.coolfood.model.Order;

import java.util.List;

public class OrdersHistoryAdapter extends RecyclerView.Adapter<OrdersHistoryAdapter.ViewHolder> {

    private List<Order> orderList;
    private Context context;

    public OrdersHistoryAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView storeNamePO;
        private TextView orderDatePO;
        private ImageButton reviewOrderButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            storeNamePO = itemView.findViewById(R.id.storeNamePO);
            orderDatePO = itemView.findViewById(R.id.orderDatePO);
            reviewOrderButton = itemView.findViewById(R.id.reviewOrderButton);
        }


    }

    @NonNull
    @Override
    public OrdersHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.past_order_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersHistoryAdapter.ViewHolder viewHolder, int i) {
        Order order = orderList.get(i);
        viewHolder.orderDatePO.setText(order.getDate());
        viewHolder.storeNamePO.setText(order.getStoreName());
        if(order.isReviewed())
            viewHolder.reviewOrderButton.setVisibility(View.VISIBLE);
        else
            viewHolder.reviewOrderButton.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
