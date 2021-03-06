package com.example.coolfood.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    private OnOfferListener onOfferListener;

    public OfferAdapter(List<Offer> offers, Context context, OnOfferListener onOfferListener) {
        this.offers = offers;
        this.context = context;
        this.onOfferListener = onOfferListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.offer_in_list_layout, viewGroup, false);
        return new ViewHolder(v, onOfferListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Offer offer = offers.get(i);
        final int mid = offer.getPickupFrom().toString().length() / 2; //get the middle of the String
        String[] parts = {offer.getPickupFrom().toString().substring(0, mid), offer.getPickupFrom().toString().substring(mid)};
        String[] parts1 = {offer.getPickupUntil().toString().substring(0, mid), offer.getPickupUntil().toString().substring(mid)};
        viewHolder.textPickup.setText(parts[0] + "." + parts[1] + " - " + parts1[0] +"."+parts1[1]);
        viewHolder.textPrice.setText(offer.getPrice() + " dinara");
        viewHolder.textQuantity.setText("Preostalo " + offer.getQuantity());
        viewHolder.textName.setText(offer.getName());

    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textPrice;
        public TextView textQuantity;
        public TextView textName;
        public TextView textPickup;
        OnOfferListener onOfferListener;


        public ViewHolder(@NonNull View itemView, OnOfferListener onOfferListener) {
            super(itemView);
            textPrice = itemView.findViewById(R.id.foodPrice);
            textQuantity = itemView.findViewById(R.id.left);
            textName = itemView.findViewById(R.id.foodName);
            textPickup = itemView.findViewById(R.id.pickupTimeTV);
            this.onOfferListener = onOfferListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onOfferListener.onOfferClick(getAdapterPosition());
        }
    }

    public interface OnOfferListener {
        void onOfferClick(int position);
    }
}
