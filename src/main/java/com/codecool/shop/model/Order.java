package com.codecool.shop.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Order {

    private List<LineItem> items;
    private OrderData orderData;

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
            this.items.stream().filter(lineItem -> lineItem.getItem().name.equals(product.getName())).collect(Collectors.toList()).get(0).addQuantity(1);
        } else {
            items.add(new LineItem(product));
        }
    }

    public void setProductQuantity(Product product, int quantity){
        if (quantity == 0) {
            items.remove(items.indexOf(items.stream().filter(lineItem -> lineItem.getItem().getName().equals(product.getName())).collect(Collectors.toList()).get(0)));
        } else {
            items.stream().filter(lineItem -> lineItem.getItem().getName().equals(product.getName())).collect(Collectors.toList()).get(0).setQuantity(quantity);
        }
    }

    public List<LineItem> getProductsInCart() {
        return items;
    }

    public int getNrOfProducts(){
        int count = 0;
        for (LineItem item: this.items){
            count += item.getQuantity();
        }
        return  count;
    }

    public BigDecimal getTotalCartPrice(){
        BigDecimal totalPrice = new BigDecimal("0");
        for (LineItem item: this.items)
            totalPrice = totalPrice.add(item.getItem().getDefaultPrice().multiply(BigDecimal.valueOf(item.getQuantity() * 1.0)));
        return totalPrice;
    }

    public void setOrderData(OrderData orderData) {
        this.orderData = orderData;
    }
}

