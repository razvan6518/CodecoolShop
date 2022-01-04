package com.codecool.shop.dao;

import com.codecool.shop.model.User;

import java.util.Optional;

public interface UserDao {
    void create(String name, String email, String password, String customerId);
    User loginUser(String email, String password);
    Optional<Boolean> checkIfEmailExist(String email);
}
