package com.example.coolfood.model;

public class Restaurant {

    private String name;
    private String description;
    private int imgUrl;

    public Restaurant() {
    }

    public Restaurant(String name, String description, int imgUrl) {
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImgUrl() {
        return imgUrl;
    }
}
