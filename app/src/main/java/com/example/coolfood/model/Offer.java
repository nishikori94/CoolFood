package com.example.coolfood.model;

public class Offer {
    private int quantity;
    private String name;
    private String pickupFrom;
    private String until;
    private double price;

    public Offer(int quantity, String name, String pickupFrom, String until, double price) {
        this.quantity = quantity;
        this.name = name;
        this.pickupFrom = pickupFrom;
        this.until = until;
        this.price=price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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

    public String getUntil() {
        return until;
    }

    public void setUntil(String until) {
        this.until = until;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
