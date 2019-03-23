package com.example.coolfood;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.coolfood.adapter.RestaurantAdapter;
import com.example.coolfood.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    private BottomNavigationView navigationView;
    private FrameLayout frameLayout;

    private RestaurantListFragment restaurantListFragment;
    private MapFragment mapFragment;
    private FavouritesFragment favouritesFragment;
    private OrdersFragment ordersFragment;
    private AccountFragment accountFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        restaurantListFragment = new RestaurantListFragment();
        mapFragment = new MapFragment();
        favouritesFragment = new FavouritesFragment();
        ordersFragment = new OrdersFragment();
        accountFragment = new AccountFragment();

        frameLayout = findViewById(R.id.main_frame);
        navigationView = findViewById(R.id.bottom_navigation_id);

        setFragment(restaurantListFragment);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.stores_item:
                        setFragment(restaurantListFragment);
                        return true;
                    case R.id.map_item:
                        setFragment(mapFragment);
                        return true;
                    case R.id.favourites_item:
                        setFragment(favouritesFragment);
                        return true;
                    case R.id.orders_item:
                        setFragment(ordersFragment);
                        return true;
                    case R.id.account_item:
                        setFragment(accountFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }


}
