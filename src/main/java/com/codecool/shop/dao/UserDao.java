package com.codecool.shop.dao;

import com.codecool.shop.model.User;

public interface UserDao {

    void create(String name, String email, String password);
    User getUserByPasswordAndName(String password, String name);
}
