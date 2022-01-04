package com.codecool.shop.model;

public class OrderData {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final String billingAddress;
    private final String shippingAddress;

    public OrderData(String firstName, String lastName, String email, String phoneNumber, String billingAddress, String shippingAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
    }
}
