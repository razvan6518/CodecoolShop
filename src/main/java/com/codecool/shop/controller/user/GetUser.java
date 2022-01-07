package com.codecool.shop.controller.user;

import com.codecool.shop.model.User;
import com.codecool.shop.serialization.UserSerialization;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GetUser", urlPatterns = {"/get_user"})
public class GetUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().registerTypeAdapter(User.class, new UserSerialization()).serializeNulls().create();
        PrintWriter out = response.getWriter();

        User user = (User) request.getSession().getAttribute("user");

        out.println(gson.toJson(user));
    }
}
