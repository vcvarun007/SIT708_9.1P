package com.example.lostfoundmapapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewItemsActivity extends AppCompatActivity {
    RecyclerView showItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);
        showItems = findViewById(R.id.LnfItems);
        LostFoundDatabase database = new LostFoundDatabase(this);
        List<Items> itemList = database.getItems();
        ItemAdapter itemAdapter = new ItemAdapter(itemList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        showItems.setAdapter(itemAdapter);
        showItems.setLayoutManager(layoutManager);
    }
}
