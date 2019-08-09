package com.example.coolfood.model;

public class Order {

    private String storeName;
    private String user;
    private String pickupFrom;
    private String getPickupUntil;
    private String price;
    private String offer;
    private String date;
    private boolean reviewed;
    private String quantity;
    private boolean active;

    public Order() {
    }

    public Order(String storeName, String user, String pickupFrom, String getPickupUntil, String price, String offer, String date, boolean reviewed, String quantity, boolean active) {
        this.storeName = storeName;
        this.user = user;
        this.pickupFrom = pickupFrom;
        this.getPickupUntil = getPickupUntil;
        this.price = price;
        this.offer = offer;
        this.date = date;
        this.reviewed = reviewed;
        this.quantity = quantity;
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
