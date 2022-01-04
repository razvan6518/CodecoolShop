package com.codecool.shop.service;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.util.StripeApi;
import com.stripe.model.Customer;

public class UsersService {

    private UserDao userDao;

    public UsersService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void registerUser(String name, String email, String password){
        if (userDao.checkIfEmailExist(email).isPresent() && !userDao.checkIfEmailExist(email).get()){
            Customer customer = createCustomer();
            userDao.create(name, email, password, customer.getId());
        }
    }

    public void loginUser(String email, String password){
        userDao.loginUser(email, password);
    }

    private Customer createCustomer(){
        return StripeApi.createCustomer();
        }
    }
