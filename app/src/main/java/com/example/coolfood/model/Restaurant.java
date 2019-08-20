package com.example.coolfood.model;

public class Restaurant {

    private String name;
    private String description;
    private String imgUrl;
    private String address;
    private String lat;
    private String lng;

    public Restaurant() {
    }

    public Restaurant(String name, String description, String imgUrl, String address, String lat, String lng) {
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
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
