package com.codecool.shop.service;

import com.codecool.shop.dao.UserDao;

public class UsersService {

    private UserDao userDao;

    public UsersService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void registerUser(String name, String email, String password){
        userDao.create(name, email, password);
    }

    public void loginUser(String email, String password){
        userDao.loginUser(email, password);
    }
}