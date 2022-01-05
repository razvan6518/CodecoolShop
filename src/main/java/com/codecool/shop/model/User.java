package com.codecool.shop.model;


public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String customerId;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String country;
    private String password;

    public User(String firstName, String lastName, String email, String phoneNumber, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
