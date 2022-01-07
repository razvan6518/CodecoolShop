package com.codecool.shop.dao.db;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;
import com.codecool.shop.util.DbConnection;
import com.codecool.shop.util.PasswordAuthentication;
import com.codecool.shop.util.StripeApi;
import com.stripe.model.PaymentMethod;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoJdbc implements UserDao {

    // TODO: Refactor !!!

    private static UserDaoJdbc instance = null;

    public static UserDaoJdbc getInstance() {
        if (instance == null) 
            instance = new UserDaoJdbc();
        return instance;
    }

    private UserDaoJdbc() {
    }

    @Override
    public Integer create(User user) {
        Connection myConn = DbConnection.getInstance().getMyConn();
        PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
        String hashedPassword = passwordAuthentication.hash(user.getPassword().toCharArray());
        try {
            Statement statement = myConn.createStatement();
            String query = "INSERT INTO codecoolshop.public.users (first_name, last_name, email, phone_number, address, city, state, country, customer_id, password) " +
                        "VALUES ('" + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.getEmail() + "', '" + user.getPhoneNumber() + "', '" + user.getAddress() + "', '" + user.getCity() + "', '" + user.getState() + "', '" + user.getCountry() + "', '" + user.getCustomerId() + "', '" + hashedPassword + "')" +
                    "RETURNING id";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next())
                return resultSet.getInt("id");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User loginUser(String email, String enteredPassword){
        Connection myConn = DbConnection.getInstance().getMyConn();
        try {
            Statement statement = myConn.createStatement();
            String query = "SELECT * FROM codecoolshop.public.users WHERE email = '" + email + "'";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()){
                String hashPass = resultSet.getString("password");
                PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
                if (passwordAuthentication.authenticate(enteredPassword.toCharArray(), hashPass)){
                    User user = new User(resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("email"),
                            resultSet.getString("phone_number"),
                            resultSet.getString("address"),
                            resultSet.getString("city"),
                            resultSet.getString("state"),
                            resultSet.getString("country"),
                            resultSet.getString("password")
                    );
                    user.setCustomerId(resultSet.getString("customer_id"));
                    user.setId(resultSet.getInt("id"));
                    return user;
                }
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Boolean> checkIfEmailExist(String email) {
        Connection myConn = DbConnection.getInstance().getMyConn();
        try {
            Statement statement = myConn.createStatement();
            String query = "SELECT * FROM codecoolshop.public.users WHERE email = '" + email + "'";
            ResultSet resultSet = statement.executeQuery(query);
            return Optional.of((resultSet.next()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<PaymentMethod> getPaymentMethods(int id) {
        Connection myConn = DbConnection.getInstance().getMyConn();
        List<String> result = null;
        try {
            Statement statement = myConn.createStatement();
            String query = "SELECT payment_method_id\n" +
                    "FROM codecoolshop.public.payment_methods\n" +
                    "INNER JOIN codecoolshop.public.users\n" +
                    "ON payment_methods.user_id = users.id\n" +
                    "WHERE users.id = " + id;
            ResultSet resultSet = statement.executeQuery(query);
            result = new ArrayList<>();
            while (resultSet.next())
                result.add(resultSet.getString("payment_method_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return StripeApi.getPaymentMethodsByIds(result);
    }

    @Override
    public void addPaymentMethod(int id, String paymentMethodId) {
        Connection myConn = DbConnection.getInstance().getMyConn();
        try {
            Statement statement = myConn.createStatement();
            String query = "INSERT INTO codecoolshop.public.payment_methods (user_id, payment_method_id) " +
                    "VALUES (" + id + ", '" + paymentMethodId + "')";
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
