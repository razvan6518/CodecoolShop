package com.codecool.shop.controller.user;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.db.UserDaoJdbc;
import com.codecool.shop.model.User;
import com.codecool.shop.service.UsersService;
import com.codecool.shop.util.StripeApi;
import com.stripe.model.PaymentMethod;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddCard", urlPatterns = {"add_card"})
public class AddCard extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsersService usersService = new UsersService(UserDaoJdbc.getInstance());

        User user = (User) request.getSession().getAttribute("user");
        String paymentMethodId = StripeApi.createPaymentMethod(
                request.getParameter("card_number"),
                Integer.parseInt(request.getParameter("exp_month")),
                Integer.parseInt(request.getParameter("exp_year")),
                request.getParameter("cvc")
        );
        // TODO add exceptions for cards: expired_card, incorrect_cvc etc...
        String updatedPaymentMethodId = StripeApi.attachesPaymentMethodToCustomer(user.getCustomerId(), paymentMethodId).getId();
        // TODO use usersService
        UserDaoJdbc.getInstance().addPaymentMethod(user.getId(), updatedPaymentMethodId);
        response.sendRedirect("/");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        engine.process("user/add_card.html", context, response.getWriter());
    }
}