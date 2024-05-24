package com.example.lostfoundmapapp;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class NewAdvertisementActivity extends AppCompatActivity {
    RadioButton radioButton;
    private static final String API_KEY = "YOUR_API_KEY";
    EditText name, phone, desc, date, locationET;
    Button saveItem, currentLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private PlacesClient placesClient;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ad);
        RadioGroup radioGroup = findViewById(R.id.postTypeRadioGroup);
        name = findViewById(R.id.nameInput);
        phone = findViewById(R.id.phoneInput);
        desc = findViewById(R.id.descInput);
        date = findViewById(R.id.dateInput);
        locationET = findViewById(R.id.locationInput);
        saveItem = findViewById(R.id.saveButton);
        currentLocation = findViewById(R.id.getCurrentLocationButton);
        // Initialize location services
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // Initialize Google Places API
        Places.initialize(getApplicationContext(), API_KEY);
        placesClient = Places.createClient(this);
        LostFoundDatabase db = new LostFoundDatabase(this);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setTypeFilter(TypeFilter.ESTABLISHMENT);
        autocompleteFragment.setCountries("AU");
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ADDRESS, Place.Field.NAME));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                locationET.setText(place.getAddress());
            }

            @Override
            public void onError(Status status) {
                // Handle the error
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(NewAdvertisementActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                } else {
                    ActivityCompat.requestPermissions(NewAdvertisementActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 0);
                }
            }
        });

        saveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);
                if (TextUtils.isEmpty(radioButton.getText().toString().trim())) {
                    Toast.makeText(NewAdvertisementActivity.this, "Please Select Post Type!..", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(name.getText().toString().trim())) {
                    Toast.makeText(NewAdvertisementActivity.this, "Please Enter Name!..", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(phone.getText().toString().trim())) {
                    Toast.makeText(NewAdvertisementActivity.this, "Please Enter Phone!..", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(desc.getText().toString().trim())) {
                    Toast.makeText(NewAdvertisementActivity.this, "Please Enter Description!..", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(date.getText().toString().trim())) {
                    Toast.makeText(NewAdvertisementActivity.this, "Please Enter Date!..", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(locationET.getText().toString().trim())) {
                    Toast.makeText(NewAdvertisementActivity.this, "Please Enter Location!..", Toast.LENGTH_SHORT).show();
                    return;
                }
                Items items = new Items(
                        name.getText().toString(),
                        phone.getText().toString(),
                        desc.getText().toString(),
                        date.getText().toString(),
                        locationET.getText().toString(),
                        radioButton.getText().toString().trim().equals("Lost") ? 1 : 0
                );
                db.insertItem(items);
                Toast.makeText(NewAdvertisementActivity.this, "Advertisement Added Successfully!...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Double latitude = location.getLatitude();
                            Double longitude = location.getLongitude();
                            try {
                                Geocoder geocoder;
                                List<Address> addresses;
                                geocoder = new Geocoder(NewAdvertisementActivity.this, Locale.getDefault());
                                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                String addressx = addresses.get(0).getAddressLine(0);
                                Toast.makeText(NewAdvertisementActivity.this, addressx, Toast.LENGTH_SHORT).show();
                                locationET.setText(addressx);
                            } catch (IOException e) {
                                Log.d("reached", e.getMessage());
                            }
                        }
                    }
                });
    }
}
