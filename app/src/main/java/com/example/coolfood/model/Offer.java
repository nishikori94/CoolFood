package com.example.coolfood.model;

public class Offer {
    private String quantity;
    private String name;
    private String pickupFrom;
    private String pickupUntil;
    private String price;
    private String restaurantId;

    public Offer() {
    }

    public Offer(String quantity, String name, String pickupFrom, String pickupUntil, String price, String restaurantId) {
        this.quantity = quantity;
        this.name = name;
        this.pickupFrom = pickupFrom;
        this.pickupUntil = pickupUntil;
        this.price = price;
        this.restaurantId = restaurantId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPickupFrom() {
        return pickupFrom;
    }

    public void setPickupFrom(String pickupFrom) {
        this.pickupFrom = pickupFrom;
    }

    public String getPickupUntil() {
        return pickupUntil;
    }

    public void setPickupUntil(String pickupUntil) {
        this.pickupUntil = pickupUntil;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
