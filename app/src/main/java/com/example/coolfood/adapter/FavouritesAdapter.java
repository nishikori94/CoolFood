package com.example.coolfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coolfood.R;
import com.example.coolfood.StoreDetailsActivity;
import com.example.coolfood.database.Database;
import com.example.coolfood.model.Favourites;
import com.example.coolfood.model.Restaurant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesViewHolder> {


    private List<Favourites> favouritesList;
    private Context context;
    Database localDB;

    public FavouritesAdapter(List<Favourites> favouritesList, Context context, Database localDB) {
        this.favouritesList = favouritesList;
        this.context = context;
        this.localDB = localDB;
    }

    @NonNull
    @Override
    public FavouritesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.store_in_list_layout, viewGroup, false);
        return new FavouritesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavouritesViewHolder viewHolder, final int i) {
        final Favourites favourites = favouritesList.get(i);
        viewHolder.nameTV.setText(favourites.getName());
        viewHolder.descriptionTV.setText(favourites.getDescription());
        int yellow = ResourcesCompat.getColor(viewHolder.favBtn.getResources(), R.color.yellow, null);
        viewHolder.favBtn.setColorFilter(yellow);
        viewHolder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int yellow = ResourcesCompat.getColor(viewHolder.favBtn.getResources(), R.color.yellow, null);
                int grey = ResourcesCompat.getColor(viewHolder.favBtn.getResources(), R.color.grey, null);
                if(!localDB.isFavourite(favourites.getRestaurantId())){
                    localDB.addToFavourites(favourites);
                    viewHolder.favBtn.setColorFilter(yellow);
                    Toast.makeText(viewHolder.favBtn.getContext(), "Added to favourites!", Toast.LENGTH_SHORT);
                } else {
                    localDB.removeFromFavourites(favourites.getRestaurantId());
                    removeItem(i);
                    viewHolder.favBtn.setColorFilter(grey);
                    Toast.makeText(viewHolder.favBtn.getContext(), "Removed from favourites!", Toast.LENGTH_SHORT);
                }
            }
        });

        Picasso.get().load(favourites.getImgUrl()).into(viewHolder.imageView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(viewHolder.descriptionTV.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        viewHolder.infoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StoreDetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favouritesList.size();
    }

    public void removeItem(int position){
        favouritesList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Favourites item, int position){
        favouritesList.add(position, item);
        notifyItemInserted(position);
    }

    public Favourites getItem(int position){
        return favouritesList.get(position);
    }
}
