package com.example.coolfood;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class HomeActivity extends AppCompatActivity implements FilterBottomSheetDialog.BottomSheetListener {


    private BottomNavigationView navigationView;
    private FrameLayout frameLayout;

    private RestaurantListFragment restaurantListFragment;
    private MapFragment mapFragment;
    private FavouritesFragment favouritesFragment;
    private OrdersFragment ordersFragment;
    private AccountFragment accountFragment;
    private OffersFragment offersFragment;

    private static final String TAG = "HomeActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        restaurantListFragment = new RestaurantListFragment();
        mapFragment = new MapFragment();
        favouritesFragment = new FavouritesFragment();
        ordersFragment = new OrdersFragment();
        accountFragment = new AccountFragment();
        offersFragment = new OffersFragment();

        frameLayout = findViewById(R.id.main_frame);
        navigationView = findViewById(R.id.bottom_navigation_id);

        if (savedInstanceState == null)
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
                        setFragment(offersFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("MyNotifications", "MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onButtonClicked(String minTime, String maxTime) {
        OffersFragment offersFragment = (OffersFragment) getSupportFragmentManager().findFragmentById(R.id.main_frame);
        offersFragment.updateText(minTime, maxTime);
    }
}
