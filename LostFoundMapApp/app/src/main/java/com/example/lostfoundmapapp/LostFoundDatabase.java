package com.example.lostfoundmapapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class LostFoundDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "lost_and_found_db";

    public LostFoundDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOST_FOUND_ITEMS_TABLE = "CREATE TABLE LOST_FOUND_ITEMS (id INTEGER PRIMARY KEY AUTOINCREMENT" +
                ",name TEXT,description TEXT,location TEXT,phone TEXT,date TEXT,post_type INTEGER);";
        db.execSQL(CREATE_LOST_FOUND_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS LOST_FOUND_ITEMS;");
        onCreate(db);
    }

    public void insertItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("description", item.getDescription());
        values.put("location", item.getLocation());
        values.put("phone", item.getPhone());
        values.put("date", item.getDate());
        values.put("post_type", item.getPostType());
        db.insert("LOST_FOUND_ITEMS", null, values);
        db.close();
    }

    public List<Item> getItems() {
        List<Item> itemList = new ArrayList<>();
        String selectQuery = "SELECT * FROM LOST_FOUND_ITEMS";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getInt(0));
                item.setName(cursor.getString(1));
                item.setDescription(cursor.getString(2));
                item.setDate(cursor.getString(5));
                item.setLocation(cursor.getString(3));
                item.setPhone(cursor.getString(4));
                item.setPostType(cursor.getInt(6));
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return itemList;
    }

    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("LOST_FOUND_ITEMS",  "id = ?", new String[] { String.valueOf(id) });
        db.close();
    }
}
