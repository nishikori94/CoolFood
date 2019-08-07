package com.example.coolfood.model;

public class Restaurant {

    private String name;
    private String description;
    private String imgUrl;
    private String address;

    public Restaurant() {
    }

    public Restaurant(String name, String description, String imgUrl, String address) {
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
