package com.codecool.shop.util;

import java.util.HashMap;
import java.util.Map;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Customer;
import com.stripe.net.RequestOptions;

public class StripeApi {

    public static Customer createCustomer(){
        Stripe.apiKey = "sk_test_51KE7W8G8Vd74GxWdplerbvkihsXMkxu4Vab7z1rnsvsYW822n5mhn08r9dqpFi6FGJNdpmpeRqxAaEjGg7w0C3qY00tZ1NyFGX";
        Map<String, Object> params = new HashMap<>();
        Customer customer = null;
        try {
            customer = Customer.create(params);
        } catch (AuthenticationException | APIException | CardException | APIConnectionException | InvalidRequestException e) {
            e.printStackTrace();
        }
        return customer;
    }

}
