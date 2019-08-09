package com.example.coolfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coolfood.R;
import com.example.coolfood.model.Order;
import com.example.coolfood.ReviewActivity;

import java.util.List;

public class OrdersHistoryAdapter extends RecyclerView.Adapter<OrdersHistoryAdapter.ViewHolder> {

    private List<Order> orderList;
    private Context context;
    private OnOrderToReviewListener onOrderToReviewListener;

    public OrdersHistoryAdapter(List<Order> orderList, Context context, OnOrderToReviewListener onOrderToReviewListener) {
        this.orderList = orderList;
        this.context = context;
        this.onOrderToReviewListener = onOrderToReviewListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView storeNamePO;
        private TextView orderDatePO;
        private TextView reviewOrderButton;
        OnOrderToReviewListener onOrderToReviewListener;

        public ViewHolder(@NonNull View itemView, OnOrderToReviewListener onOrderToReviewListener) {
            super(itemView);

            storeNamePO = itemView.findViewById(R.id.storeNamePO);
            orderDatePO = itemView.findViewById(R.id.pickupTimePO);
            reviewOrderButton = itemView.findViewById(R.id.reviewOrderButton);
            this.onOrderToReviewListener = onOrderToReviewListener;

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onOrderToReviewListener.onOrderToReviewClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public OrdersHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.past_order_layout, viewGroup, false);
        return new ViewHolder(v, onOrderToReviewListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersHistoryAdapter.ViewHolder viewHolder, int i) {
        Order order = orderList.get(i);
        viewHolder.orderDatePO.setText(order.getDate());
        viewHolder.storeNamePO.setText(order.getStoreName());

        if(order.isReviewed()) {
            viewHolder.reviewOrderButton.setVisibility(View.VISIBLE);
            viewHolder.reviewOrderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ReviewActivity.class);
                    context.startActivity(intent);
                }
            });
        }
        else
            viewHolder.reviewOrderButton.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public interface OnOrderToReviewListener{
        void onOrderToReviewClick(int position);
    }
}
