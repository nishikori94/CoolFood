package com.example.coolfood;


import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coolfood.model.Restaurant;
import com.example.coolfood.renderer.ClusterManagerRenderer;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "MapFragment";
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 10f;
    public static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    private MapView mMapView;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private GoogleMap mGoogleMap;
    DatabaseReference databaseReference;

    private ClusterManager<Restaurant> mClusterManager;
    private ClusterManagerRenderer mClusterManagerRenderer;
    private ArrayList<Restaurant> mClusterMarkers = new ArrayList<>();

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = view.findViewById(R.id.map);

        initGoogleMap(savedInstanceState);

        return view;
    }


    private void addMapMarkers() {
        if (mGoogleMap != null) {

            mClusterManager = new ClusterManager<Restaurant>(getActivity().getApplicationContext(), mGoogleMap);
            mClusterManagerRenderer = new ClusterManagerRenderer(
                    getActivity(),
                    mGoogleMap,
                    mClusterManager
            );
            mClusterManager.setRenderer(mClusterManagerRenderer);
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Restaurant");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        try {
                            String snippet = snapshot.child("description").getValue().toString();
                            Restaurant newClusterMarker = new Restaurant(snapshot.child("name").getValue().toString(), snapshot.child("description").getValue().toString(),
                                    snapshot.child("imgUrl").getValue().toString(),
                                    snapshot.child("address").getValue().toString(),
                                    snapshot.child("lat").getValue().toString(), snapshot.child("lng").getValue().toString(), snapshot.child("restaurantId").getValue().toString());
                            mClusterManager.addItem(newClusterMarker);
                        } catch (NullPointerException e) {
                            Log.e(TAG, "addMapMarkers: NullPointerException: " + e.getMessage());
                        }
                    }
                    mClusterManager.cluster();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            mGoogleMap.setOnInfoWindowClickListener(mClusterManager);
            mClusterManager.setOnClusterItemInfoWindowClickListener(new ClusterManager.OnClusterItemInfoWindowClickListener<Restaurant>() {
                @Override
                public void onClusterItemInfoWindowClick(Restaurant restaurant) {
                    Intent intent = new Intent(getContext(), OffersActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("storeId", restaurant.getRestaurantId());
                    extras.putString("restaurantName", restaurant.getName());
                    extras.putString("restaurantAddress", restaurant.getAddress());
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            });
        }
    }

    /**
     * Determines the view boundary then sets the camera
     * Sets the view
     */


    private void initGoogleMap(Bundle savedInstanceState) {
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        map.setMyLocationEnabled(true);
//        mGoogleMap = map;
//        setCameraView();

        mGoogleMap = map;
        mGoogleMap.setMyLocationEnabled(true);
        getDeviceLocation();
        addMapMarkers();
    }


    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        try {
            //if (locationPermissionGranted) {
            Task location = fusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        Log.d(TAG, "onComplete: found location!");
                        Location currentLocation = (Location) task.getResult();
                        moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);
                    } else {
                        Log.d(TAG, "onComplete: current location is null");
                        Toast.makeText(getContext(), R.string.unable_location, Toast.LENGTH_SHORT).show();
                    }

                }
            });
            //}
        } catch (SecurityException e) {
            Log.d(TAG, "getDeviceLocation: Security exception:" + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG, "moveCamera: moving camera to: " + latLng.latitude + ", " + latLng.longitude);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
