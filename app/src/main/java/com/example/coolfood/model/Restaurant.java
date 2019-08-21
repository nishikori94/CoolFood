package com.example.coolfood.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class Restaurant implements ClusterItem {

    private String restaurantId;
    private String name;
    private String description;
    private String imgUrl;
    private String address;
    private String lat;
    private String lng;

    public Restaurant() {
    }

    public Restaurant(String name, String description, String imgUrl, String address, String lat, String lng, String restaurantId) {
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.restaurantId = restaurantId;
    }

    public Restaurant(String name, String description, String lat, String lng, String imgUrl) {
        this.name = name;
        this.description = description;
        this.lat = lat;
        this.lng = lng;
        this.imgUrl = imgUrl;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
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

    @Override
    public LatLng getPosition() {
        return new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSnippet() {
        return description;
    }
}
