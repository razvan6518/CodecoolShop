package com.codecool.shop.service;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;
import com.codecool.shop.util.StripeApi;
import com.stripe.model.Customer;

public class UsersService {

    private UserDao userDao;

    public UsersService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void registerUser(User user){
        if (userDao.checkIfEmailExist(user.getEmail()).isPresent() && !userDao.checkIfEmailExist(user.getEmail()).get()){
            Customer customer = createCustomer();
            user.setCustomerId(customer.getId());
            userDao.create(user);
        }
    }

    public User loginUser(String email, String password){
        return userDao.loginUser(email, password);
    }

    private Customer createCustomer(){
        return StripeApi.createCustomer();
        }
    }
