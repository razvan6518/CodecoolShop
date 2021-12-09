package com.codecool.shop.model;

public class LineItem {
    private Product item;
    private int quantity;

    public LineItem(Product product) {
        this.item = product;
        this.quantity = 1;
    }

    public Product getItem() {
        return item;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
