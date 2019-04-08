package com.example.coolfood.model;

public class Review {
    //treba spojiti korisnika sa review-om
    private String user;
    private String reviewText;
    //treba dodati polje za ocenu


    public Review(String user, String reviewText) {
        this.user = user;
        this.reviewText = reviewText;
    }

    public String getUser() {
        return user;
    }

    public String getReviewText() {
        return reviewText;
    }
}
