package com.codecool.shop.dao;

import com.codecool.shop.model.User;
import com.stripe.model.PaymentMethod;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Integer create(String name, String email, String password, String customerId);
    User loginUser(String email, String password);
    Optional<Boolean> checkIfEmailExist(String email);
    List<PaymentMethod> getPaymentMethods(int id);
    void addPaymentMethod(int id, String paymentMethodId);
}
