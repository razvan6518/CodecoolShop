package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.util.StripeApi;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@WebServlet(name = "Payment", urlPatterns = {"/payment"})
public class Payment extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Order order = Order.getInstance();
        List<LineItem> productsList = order.getProductsInCart();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());

        BigDecimal totalPrice = new BigDecimal("0");
        for (LineItem item: productsList)
            totalPrice = totalPrice.add(item.getItem().getDefaultPrice().multiply(BigDecimal.valueOf(item.getQuantity() * 1.0)));
        context.setVariable("total_price", totalPrice);

        PaymentIntent paymentIntent = StripeApi.createPaymentIntent(totalPrice.setScale(0, RoundingMode.UP).intValueExact(), "USD");
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("paymentIntent", paymentIntent);

        engine.process("payment/payment.html", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Order order = Order.getInstance();
        List<LineItem> productsList = order.getProductsInCart();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());

        BigDecimal totalPrice = new BigDecimal("0");
        for (LineItem item: productsList)
            totalPrice = totalPrice.add(item.getItem().getDefaultPrice().multiply(BigDecimal.valueOf(item.getQuantity() * 1.0)));

        context.setVariable("total_price", totalPrice);

        PaymentMethod paymentMethod = StripeApi.createPaymentMethod(request.getParameter("card_number"), Integer.parseInt(request.getParameter("exp_month")), Integer.parseInt(request.getParameter("exp_year")), request.getParameter("cvc"));
        if (paymentMethod != null){
            HttpSession httpSession = request.getSession();
            PaymentIntent paymentIntent = (PaymentIntent) httpSession.getAttribute("paymentIntent");
            PaymentIntent updatedPaymentIntent = StripeApi.tryToConfirmPayment(paymentIntent, paymentMethod.getId());
            if (updatedPaymentIntent.getStatus().equals("succeeded")){
                engine.process("payment/succeeded.html", context, response.getWriter());
            }else{
                engine.process("payment/payment.html", context, response.getWriter());
            }
        }else{
            engine.process("payment/payment.html", context, response.getWriter());
        }
    }

}
