package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.UserDaoPostgreSQL;
import com.codecool.shop.service.UsersService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UsersController", urlPatterns = {"/user", "/user/login"})
public class UsersController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsersService usersService = new UsersService(UserDaoPostgreSQL.getInstance());
        if (request.getServletPath().equals("/user")){
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            usersService.registerUser(name, email, password);

            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
            WebContext context = new WebContext(request, response, request.getServletContext());

            context.setVariable("email", email);

            engine.process("user/login.html", context, response.getWriter());
        }
        if (request.getServletPath().equals("/user/login")){
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            usersService.loginUser(email, password);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        engine.process("user/register.html", context, response.getWriter());
    }
}