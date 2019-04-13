package com.example.coolfood.model;

public class Order {

    //ovde ce biti private Store store
    private String storeName;
    //verovatno treba neki DateTime
    private String pickupFrom;
    private String getPickupUntil;

    private String date;
    private boolean reviewed;

    public Order(String storeName, String pickupFrom, String getPickupUntil) {
        this.storeName = storeName;
        this.pickupFrom = pickupFrom;
        this.getPickupUntil = getPickupUntil;
    }

    public Order(String storeName, String date, boolean reviewed) {
        this.storeName = storeName;
        this.date = date;
        this.reviewed = reviewed;
    }

    public Order(String storeName, String pickupFrom, String getPickupUntil, String date, boolean reviewed) {
        this.storeName = storeName;
        this.pickupFrom = pickupFrom;
        this.getPickupUntil = getPickupUntil;
        this.date = date;
        this.reviewed = reviewed;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPickupFrom() {
        return pickupFrom;
    }

    public void setPickupFrom(String pickupFrom) {
        this.pickupFrom = pickupFrom;
    }

    public String getGetPickupUntil() {
        return getPickupUntil;
    }

    public void setGetPickupUntil(String getPickupUntil) {
        this.getPickupUntil = getPickupUntil;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }
}
