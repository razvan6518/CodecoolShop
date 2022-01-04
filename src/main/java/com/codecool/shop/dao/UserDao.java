package com.codecool.shop.dao;

import java.util.Optional;

public interface UserDao {

    void create(String name, String email, String password, String customerId);
    void loginUser(String email, String password);
    Optional<Boolean> checkIfEmailExist(String email);
}
