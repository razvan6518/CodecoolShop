package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Order {

    private List<LineItem> items;

    private static Order instance = null;

    public static Order getInstance(){
        if (instance == null)
            instance = new Order();
        return instance;
    }

    private Order() {
        items = new ArrayList<>();
    }

    public void addProduct(Product product){
        if (this.items.stream().anyMatch(lineItem -> lineItem.getItem().name.equals(product.getName()))){
            this.items.stream().filter(lineItem -> lineItem.getItem().name.equals(product.getName())).collect(Collectors.toList()).get(0).setQuantity(1);
        } else {
            items.add(new LineItem(product));
        }
    }

    public void setProductQuantity(Product product, int quantity){
        items.stream().filter(lineItem -> lineItem.getItem().getName().equals(product.getName())).collect(Collectors.toList()).get(0).setQuantity(quantity);
    }

    public List<LineItem> getProductsInCart() {
        return items;
    }

}

