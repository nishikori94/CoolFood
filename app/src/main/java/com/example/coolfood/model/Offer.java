package com.example.coolfood.model;

public class Offer {
    private String quantity;
    private String name;
    private String pickupFrom;
    private String pickupUntil;
    private String price;
    private String restaurantId;
    private String description;
    private String oldPrice;
    private String date;
    private String imgUrl;

    public Offer() {
    }

    public Offer(String quantity, String name, String pickupFrom, String pickupUntil, String price, String restaurantId, String description, String oldPrice, String date, String imgUrl) {
        this.quantity = quantity;
        this.name = name;
        this.pickupFrom = pickupFrom;
        this.pickupUntil = pickupUntil;
        this.price = price;
        this.restaurantId = restaurantId;
        this.description = description;
        this.oldPrice = oldPrice;
        this.date = date;
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
