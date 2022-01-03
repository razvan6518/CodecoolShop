package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.placeholder.DbConnection;
import com.codecool.shop.util.PasswordAuthentication;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDaoPostgreSQL implements UserDao {

    private static UserDaoPostgreSQL instance = null;

    public static UserDaoPostgreSQL getInstance() {
        if (instance == null) 
            instance = new UserDaoPostgreSQL();
        return instance;
    }

    private UserDaoPostgreSQL() {
    }

    @Override
    public void create(String userName, String userEmail, String userPassword) {
        Connection myConn = DbConnection.getInstance().getMyConn();
        PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
        String hashedPassword = passwordAuthentication.hash(userPassword.toCharArray());
        try {
            Statement statement = myConn.createStatement();
            String s = "INSERT INTO codecoolshop.public.users (name, email, password) " +
                        "VALUES ('" + userName + "', '" + userEmail + "', '" + hashedPassword + "')";
            statement.executeUpdate(s);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loginUser(String email, String enteredPassword){
        Connection myConn = DbConnection.getInstance().getMyConn();
        try {
            Statement statement = myConn.createStatement();
            String s = "SELECT * FROM codecoolshop.public.users WHERE email = '" + email + "'";
            ResultSet resultSet = statement.executeQuery(s);
            if (resultSet.next()){
                String hashPass = resultSet.getString("password");
                PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
                if (passwordAuthentication.authenticate(enteredPassword.toCharArray(), hashPass)){
                    System.out.println("ok");
                }
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
