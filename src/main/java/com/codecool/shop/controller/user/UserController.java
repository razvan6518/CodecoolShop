package com.codecool.shop.controller.user;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.db.UserDaoJdbc;
import com.codecool.shop.model.User;
import com.codecool.shop.service.UsersService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// TODO: Refactor !!!

@WebServlet(name = "UserController", urlPatterns = {"/user", "/user/login", "/user/logout"})
public class UserController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsersService usersService = new UsersService(UserDaoJdbc.getInstance());
        if (request.getServletPath().equals("/user")){
            String firstName = request.getParameter("first_name");
            String lastName = request.getParameter("last_name");
            String phoneNumber = request.getParameter("phone");
            String address = request.getParameter("address");
            String city = request.getParameter("city");
            String state = request.getParameter("state");
            String country = request.getParameter("country");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            User user = new User(firstName, lastName, email, phoneNumber, address, city, state, country, password);

            usersService.registerUser(user);

            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
            WebContext context = new WebContext(request, response, request.getServletContext());

            context.setVariable("email", email);

            engine.process("user/login.html", context, response.getWriter());
        }
        if (request.getServletPath().equals("/user/login")){
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            User loggedUser = usersService.loginUser(email, password);
            if (loggedUser != null){
                HttpSession session = request.getSession();
                session.setAttribute("user", loggedUser);
                response.sendRedirect("/");
            }else{
                TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
                WebContext context = new WebContext(request, response, request.getServletContext());
                engine.process("user/login.html", context, response.getWriter());
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getServletPath().equals("/user")) {
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
            WebContext context = new WebContext(request, response, request.getServletContext());
            engine.process("user/register.html", context, response.getWriter());
        }
        if (request.getServletPath().equals("/user/login")){
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
            WebContext context = new WebContext(request, response, request.getServletContext());

            engine.process("user/login.html", context, response.getWriter());
        }
        if (request.getServletPath().equals("/user/logout")){
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
            WebContext context = new WebContext(request, response, request.getServletContext());

            HttpSession session = request.getSession();
            session.removeAttribute("user");

            response.sendRedirect("/");
        }
    }
}