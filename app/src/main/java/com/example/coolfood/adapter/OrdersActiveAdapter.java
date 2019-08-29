package com.example.coolfood.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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

            storeName = itemView.findViewById(R.id.storeNameAO);
            pickupTime = itemView.findViewById(R.id.pickupTimeAO);
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
        final int mid = order.getPickupFrom().length() / 2; //get the middle of the String
        String[] parts = {order.getPickupFrom().substring(0, mid), order.getPickupFrom().substring(mid)};
        String[] parts1 = {order.getGetPickupUntil().substring(0, mid), order.getGetPickupUntil().substring(mid)};
        viewHolder.pickupTime.setText(parts[0] + "." + parts[1] + " - " + parts1[0] +"."+parts1[1]);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
