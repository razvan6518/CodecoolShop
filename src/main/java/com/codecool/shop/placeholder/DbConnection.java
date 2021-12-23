package com.codecool.shop.placeholder;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class DbConnection {
    private static DbConnection instance = null;
    Connection myConn = null;

    private DbConnection() {
    }

    public Connection getMyConn() {
        return myConn;
    }

    private void initConnection(){
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("src/main/resources/connection.properties"));
            String url = props.getProperty("url");
            String database = props.getProperty("database");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            String dao = props.getProperty("dao");
            if (dao.equals("jdbc")){
                Statement stmt = null;
                try {
                    Class.forName("org.postgresql.Driver");
                    myConn = DriverManager
                            .getConnection(url, user, password);
                    System.out.println("Opened database successfully");

//                    stmt = myConn.createStatement();
//                    String sql = "CREATE TABLE COMPANY " +
//                            "(ID INT PRIMARY KEY     NOT NULL," +
//                            " NAME           TEXT    NOT NULL, " +
//                            " AGE            INT     NOT NULL, " +
//                            " ADDRESS        CHAR(50), " +
//                            " SALARY         REAL)";
//                    stmt.executeUpdate(sql);
//                    stmt.close();
//                    myConn.close();
                } catch ( Exception e ) {
                    System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                    System.exit(0);
                }
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public static DbConnection getInstance() {
        if (instance == null) {
            instance = new DbConnection();
            instance.initConnection();
        }
        return instance;
    }
}
