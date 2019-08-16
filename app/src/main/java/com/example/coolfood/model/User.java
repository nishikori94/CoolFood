package com.example.coolfood.model;

public class User {
    private String name;
    private String surname;
    private String email;
    private String number;
    private String imgUrl;
    private String address;

    public User() {
    }

    public User(String name, String surname, String email, String number, String imgUrl, String address) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.number = number;
        this.imgUrl = imgUrl;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
}
