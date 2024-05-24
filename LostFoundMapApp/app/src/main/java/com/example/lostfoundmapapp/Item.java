package com.example.lostfoundmapapp;

public class Item {
    private int itemID, itemType;
    private String itemName;
    private String itemPhone;
    private String itemDescription;
    private String itemDate;
    private String itemLocation;

    public Item() {}

    public Item(String itemName, String itemPhone, String itemDescription, String itemDate, String itemLocation, int itemType) {
        this.itemName = itemName;
        this.itemPhone = itemPhone;
        this.itemDescription = itemDescription;
        this.itemDate = itemDate;
        this.itemLocation = itemLocation;
        this.itemType = itemType;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPhone() {
        return itemPhone;
    }

    public void setItemPhone(String itemPhone) {
        this.itemPhone = itemPhone;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemDate() {
        return itemDate;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
    }

    public String getItemLocation() {
        return itemLocation;
    }

    public void setItemLocation(String itemLocation) {
        this.itemLocation = itemLocation;
    }
}
