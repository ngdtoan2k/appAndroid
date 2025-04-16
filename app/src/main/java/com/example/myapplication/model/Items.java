package com.example.myapplication.model;

public class Items {
    private int id;
    private String name;
    private int cid;
    private int quantity;
    private double price;
    private String updatedAt;

    public Items(int id, String name, int cid, int quantity, double price, String updatedAt) {
        this.id = id;
        this.name = name;
        this.cid = cid;
        this.quantity = quantity;
        this.price = price;
        this.updatedAt = updatedAt;
    }

    public Items(String name, int cid, int quantity, double price, String updatedAt) {
        this.name = name;
        this.cid = cid;
        this.quantity = quantity;
        this.price = price;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
