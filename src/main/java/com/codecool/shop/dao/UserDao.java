package com.codecool.shop.dao;

public interface UserDao {

    void create(String name, String email, String password);
    void loginUser(String email, String password);
}
