package com.codecool.shop.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;

public class StripeApi {

    public static Customer createCustomer(){
        Stripe.apiKey = "sk_test_51KE7W8G8Vd74GxWdplerbvkihsXMkxu4Vab7z1rnsvsYW822n5mhn08r9dqpFi6FGJNdpmpeRqxAaEjGg7w0C3qY00tZ1NyFGX";
        Map<String, Object> params = new HashMap<>();
        Customer customer = null;
        try {
            customer = Customer.create(params);
        } catch (StripeException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public static PaymentIntent createPaymentIntent(int amount, String currency, String customerId){
        Stripe.apiKey = "sk_test_51KE7W8G8Vd74GxWdplerbvkihsXMkxu4Vab7z1rnsvsYW822n5mhn08r9dqpFi6FGJNdpmpeRqxAaEjGg7w0C3qY00tZ1NyFGX";

        List<Object> paymentMethodTypes =
                new ArrayList<>();
        paymentMethodTypes.add("card");
        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("currency", currency);
        params.put("customer", customerId);
        params.put(
                "payment_method_types",
                paymentMethodTypes
        );
        PaymentIntent paymentIntent = null;
        try {
            paymentIntent = PaymentIntent.create(params);
        } catch (StripeException e) {
            e.printStackTrace();
        }
        return paymentIntent;
    }

    public static String createPaymentMethod(String number, int expMonth, int expYear, String cvc){
        Stripe.apiKey = "sk_test_51KE7W8G8Vd74GxWdplerbvkihsXMkxu4Vab7z1rnsvsYW822n5mhn08r9dqpFi6FGJNdpmpeRqxAaEjGg7w0C3qY00tZ1NyFGX";

        Map<String, Object> card = new HashMap<>();
        card.put("number", number);
        card.put("exp_month", expMonth);
        card.put("exp_year", expYear);
        card.put("cvc", cvc);
        Map<String, Object> params = new HashMap<>();
        params.put("type", "card");
        params.put("card", card);
        PaymentMethod paymentMethod = null;
        try {
            paymentMethod = PaymentMethod.create(params);
        } catch (StripeException e) {
            e.printStackTrace();
        }
        return paymentMethod.getId();
    }

    public static PaymentIntent tryToConfirmPayment(PaymentIntent paymentIntent, String paymentMethodId){
        Stripe.apiKey = "sk_test_51KE7W8G8Vd74GxWdplerbvkihsXMkxu4Vab7z1rnsvsYW822n5mhn08r9dqpFi6FGJNdpmpeRqxAaEjGg7w0C3qY00tZ1NyFGX";
        Map<String, Object> params = new HashMap<>();
        params.put("payment_method", paymentMethodId);
        PaymentIntent updatedPaymentIntent = null;
        try {
            updatedPaymentIntent = paymentIntent.confirm(params);
        } catch (StripeException e) {
            e.printStackTrace();
        }
        return updatedPaymentIntent;
    }

    public static List<PaymentMethod> getPaymentMethodsByIds(List<String> paymentMethodsIds){
        Stripe.apiKey = "sk_test_51KE7W8G8Vd74GxWdplerbvkihsXMkxu4Vab7z1rnsvsYW822n5mhn08r9dqpFi6FGJNdpmpeRqxAaEjGg7w0C3qY00tZ1NyFGX";
        List<PaymentMethod> result = new ArrayList<>();
        for (String paymentMethodId: paymentMethodsIds){
            try {
                result.add(PaymentMethod.retrieve(paymentMethodId));
            } catch (StripeException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static PaymentMethod attachesPaymentMethodToCustomer(String customerId, String paymentMethodId){
        Stripe.apiKey = "sk_test_51KE7W8G8Vd74GxWdplerbvkihsXMkxu4Vab7z1rnsvsYW822n5mhn08r9dqpFi6FGJNdpmpeRqxAaEjGg7w0C3qY00tZ1NyFGX";

        PaymentMethod paymentMethod = null;
        try {
            paymentMethod = PaymentMethod.retrieve(paymentMethodId);
        } catch (StripeException e) {
            e.printStackTrace();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("customer", customerId);
        PaymentMethod updatedPaymentMethod = null;
        try {
            assert paymentMethod != null;
            updatedPaymentMethod = paymentMethod.attach(params);
        } catch (StripeException e) {
            e.printStackTrace();
        }
        return updatedPaymentMethod;
    }
}
