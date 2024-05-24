package com.example.lostfoundmapapp;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    // creating array list for adding all our locations.
    private ArrayList<LatLng> locationArrayList;

    private ArrayList<String> itemType, itemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_on_map);
        LostFoundDatabase db = new LostFoundDatabase(this);
        List<Items> itemList = db.getItems();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.showOnMap);
        mapFragment.getMapAsync(this);

        // in below line we are initializing our array list.
        locationArrayList = new ArrayList<>();
        itemType = new ArrayList<>();
        itemName = new ArrayList<>();
        // on below line we are adding our
        // locations in our array list.
        if (itemList != null) {
            for (int i = 0; i < itemList.size(); i++) {
                locationArrayList.add(getLocationFromAddress(itemList.get(i).getLocation()));
                itemName.add(itemList.get(i).getName());
                if (itemList.get(i).getPostType() == 1) {
                    itemType.add("Lost");
                } else {
                    itemType.add("Found");
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // inside on map ready method
        // we will be displaying all our markers.
        // for adding markers we are running for loop and
        // inside that we are drawing marker on our map.
        for (int i = 0; i < locationArrayList.size(); i++) {

            // below line is use to add marker to each location of our array list.
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)).title(itemName.get(i) + "(" + itemType.get(i) + " Item)"));

            // below line is use to zoom our camera on map.
            mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));

            // below line is use to move our camera to the specific location.
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(i)));
        }
    }

    public LatLng getLocationFromAddress(String strAddress) {
        try {
            Geocoder coder = new Geocoder(this);
            List<Address> address;
            GeoPoint p1 = null;

            try {
                address = coder.getFromLocationName(strAddress, 5);
                if (address == null) {
                    return null;
                }
                Address location = address.get(0);
                return new LatLng(location.getLatitude(), location.getLongitude());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } catch (Exception e) {
            Log.d("reached glfa", e.getMessage());
            return null;
        }
    }
}
