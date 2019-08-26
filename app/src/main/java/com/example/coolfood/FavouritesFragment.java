package com.example.coolfood;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coolfood.adapter.FavouritesAdapter;
import com.example.coolfood.database.Database;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFragment extends Fragment {

    private RecyclerView recyclerView;

    FavouritesAdapter favouritesAdapter;

    private FirebaseAuth firebaseAuth;
    FirebaseUser user;

    Database localDB;

    public FavouritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        recyclerView = view.findViewById(R.id.homeRecyclerView);

        localDB = new Database(getContext());
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        favouritesAdapter = new FavouritesAdapter(new Database(getContext()).getAllFavourites(user.getEmail()), getContext(), localDB);

        recyclerView.setAdapter(favouritesAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Inflate the layout for this fragment

        setHasOptionsMenu(true);
        return view;

    }

}
