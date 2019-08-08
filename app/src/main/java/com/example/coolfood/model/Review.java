package com.example.coolfood.model;

public class Review {
    //treba spojiti korisnika sa review-om
    private String user;
    private String reviewText;
    private String rating;
    private String restaurantId;

    public Review() {
    }

    public Review(String user, String reviewText, String rating, String restaurantId) {
        this.user = user;
        this.reviewText = reviewText;
        this.rating = rating;
        this.restaurantId = restaurantId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
