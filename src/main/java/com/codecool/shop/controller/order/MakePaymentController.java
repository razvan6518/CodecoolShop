package com.codecool.shop.controller.order;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.db.UserDaoJdbc;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.User;
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
import java.util.List;


@WebServlet(name = "MakePaymentController", urlPatterns = {"/make_payment"})
public class MakePaymentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}
