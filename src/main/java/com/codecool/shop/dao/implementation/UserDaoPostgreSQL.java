package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;
import com.codecool.shop.placeholder.DbConnection;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoPostgreSQL implements UserDao {

    @Override
    public void create(String userName, String userEmail, String userPassword) {
        Connection myConn = DbConnection.getInstance().getMyConn();
        String hashedPassword = Hex.encodeHexString(getHashedPassword(userPassword));
        try {
            Statement statement = myConn.createStatement();
            String s = "insert into codecoolshop.public.users (name, email, password) values ('" + userName + "', '" + userEmail + "', '" + hashedPassword + "')";
            statement.executeUpdate(s);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserByPasswordAndName(String password, String name) {
        Connection myConn = DbConnection.getInstance().getMyConn();
        String hashedPassword = Hex.encodeHexString(getHashedPassword(password));
        try {
            Statement statement = myConn.createStatement();
            String query = "Select password from codecoolshop.public.users where name = '"+name+"'";
            ResultSet resultSet = statement.executeQuery(query);
            List<User> users = new ArrayList<>();
            if (resultSet.next()){
                String hashPass = resultSet.getString("password");

            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] getHashedPassword(String password) {
//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[16];
//        random.nextBytes(salt);
//        MessageDigest md = null;
//        try {
//            md = MessageDigest.getInstance("SHA-512");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        md.update(salt);
//        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
//        String string = new String(hashedPassword);

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = null;
        try {
            hash = factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return hash;
    }
}
