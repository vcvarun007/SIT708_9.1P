package com.example.lostfoundmapapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lostfoundmapapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        EdgeToEdge.enable(this);

        mainBinding.createAd.setOnClickListener(view -> {
            Intent createAdIntent = new Intent(MainActivity.this, NewAdvertisementActivity.class);
            startActivity(createAdIntent);
        });

        mainBinding.showItems.setOnClickListener(view -> {
            Intent showItemsIntent = new Intent(MainActivity.this, ViewItemsActivity.class);
            startActivity(showItemsIntent);
        });

        mainBinding.showOnMap.setOnClickListener(view -> {
            Intent showMapIntent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(showMapIntent);
        });
    }
}
