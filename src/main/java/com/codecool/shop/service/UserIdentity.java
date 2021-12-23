package com.codecool.shop.service;

public class UserIdentity {

    private static UserIdentity instance = null;
    String name;

    private UserIdentity() {
    }

    public static UserIdentity getInstance() {
        if (instance == null) {
            instance = new UserIdentity();
        }
        return instance;
    }
}
