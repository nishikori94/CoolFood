package com.example.coolfood.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coolfood.R;
import com.example.coolfood.model.Offer;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {

    private List<Offer> offers;
    private Context context;

    public OfferAdapter(List<Offer> offers, Context context) {
        this.offers = offers;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from( viewGroup.getContext())
                .inflate(R.layout.offer_in_list_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Offer offer = offers.get(i);

        viewHolder.textPickup.setText(offer.getPickupFrom()+" - " + offer.getUntil());
        viewHolder.textPrice.setText(Double.toString(offer.getPrice()));
        viewHolder.textQuantity.setText(Double.toString(offer.getPrice()));
        viewHolder.textName.setText(offer.getName());

    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textPrice;
        public TextView textQuantity;
        public TextView textName;
        public TextView textPickup;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textPrice = itemView.findViewById(R.id.foodPrice);
            textQuantity = itemView.findViewById(R.id.left);
            textName = itemView.findViewById(R.id.foodName);
            textPickup = itemView.findViewById(R.id.pickupTimeTV);
        }
    }
}
