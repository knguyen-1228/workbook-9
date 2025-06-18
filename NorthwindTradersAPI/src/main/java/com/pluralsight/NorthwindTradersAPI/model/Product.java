package com.pluralsight.NorthwindTradersAPI.model;

public class Product {
    private int productId;
    private String name;
    private int category;
    private double price;

    public Product(){}

    public Product(int productId, double price, int category, String name) {
        this.productId = productId;
        this.price = price;
        this.category = category;
        this.name = name;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return  "Products{" + "Product Id: " + productId + ", Name:" + name + ", Category: " + category + ", Price: " + price + "'}";
    }

}
