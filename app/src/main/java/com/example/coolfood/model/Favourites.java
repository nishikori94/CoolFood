package com.example.coolfood.model;

public class Favourites {

    private String name;
    private String description;
    private String imgUrl;
    private String address;
    private String restaurantId;
    private String user;

    public Favourites() {
    }

    public Favourites(String name, String description, String imgUrl, String address, String restaurantId, String user) {
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.address = address;
        this.restaurantId = restaurantId;
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
