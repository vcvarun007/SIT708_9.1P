package com.example.lostfoundmapapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailActivity extends AppCompatActivity {
    TextView itemName, itemPhone, itemDesc, itemLocation, itemDate, itemPostType;
    int itemID;
    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Intent receivedIntent = getIntent();

        itemName = findViewById(R.id.itemDetailName);
        itemPhone = findViewById(R.id.itemDetailPhone);
        itemDesc = findViewById(R.id.itemDetailDesc);
        itemLocation = findViewById(R.id.itemDetailLocation);
        itemDate = findViewById(R.id.itemDetailDate);
        itemPostType = findViewById(R.id.itemDetailType);
        deleteButton = findViewById(R.id.btnDeleteitem);
        itemID = receivedIntent.getIntExtra("id", 0);
        itemName.setText(receivedIntent.getStringExtra("name"));
        itemPhone.setText(receivedIntent.getStringExtra("phone"));
        itemDesc.setText(receivedIntent.getStringExtra("desc"));
        itemLocation.setText(receivedIntent.getStringExtra("location"));
        itemDate.setText(receivedIntent.getStringExtra("date"));
        itemPostType.setText(receivedIntent.getStringExtra("post_type"));
        LostFoundDatabase database = new LostFoundDatabase(this);

        deleteButton.setOnClickListener(view -> {
            database.deleteItem(itemID);
            Intent intent = new Intent(ItemDetailActivity.this, ViewItemsActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
