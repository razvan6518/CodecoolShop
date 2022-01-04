package com.codecool.shop.service;

import com.codecool.shop.dao.UserDao;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;


public class UsersService {

    private UserDao userDao;

    public UsersService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void registerUser(String name, String email, String password) throws IOException {
        if (userDao.checkIfEmailExist(email).isPresent() && !userDao.checkIfEmailExist(email).get()){
            String customerId = "";
            String resp = getJsonFromApi();
            userDao.create(name, email, password, customerId);
        }
    }

    public void loginUser(String email, String password){
        userDao.loginUser(email, password);
    }

    private String getJsonFromApi() throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL("https://sandboxapi.rapyd.net/v1/payment_methods/required_fields/ro_visa_card");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("GET");
//        con.setRequestProperty("Content-Type", "application/json");
//        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("rapyd_access_key", "8DE128D5601AD9D7DA63");
        con.setRequestProperty("rapyd_secret_key", "eab8b40a7d85432d9795f761d9a0320ebe9abbd45c0ecda89a0573ca5efce15244f7eb8f231ef183");
//        con.setRequestProperty("rapyd_request_timestamp", "1641216332");
//        con.setRequestProperty("rapyd_signature_salt", "c78c2b2632072d716dc3e225");
//        con.setRequestProperty("rapyd_signature", "YTlkNTM5NDJhYzMyYzNjNDNlYjE0MWI0NjkzY2NkZjg5Y2U0OGNiZWY1ZTQxYmVhMjc5OTc3ZTE3MWY3MzM1MA==");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null){
                result.append(line);
            }
            con.disconnect();
        } catch (IOException ignored) {}
        return result.toString();
    }

    private String createWallet() throws IOException {
//        URL url = new URL("https://sandboxapi.rapyd.net/v1/payment_methods/country?country=RO");
//        HttpURLConnection con = (HttpURLConnection)url.openConnection();
//        con.setRequestMethod("GET");
//        con.setRequestProperty("Content-Type", "application/json");
//        con.setRequestProperty("Accept", "application/json");
//        con.setRequestProperty("rapyd_access_key", "8DE128D5601AD9D7DA63");
//        con.setRequestProperty("rapyd_secret_key", "eab8b40a7d85432d9795f761d9a0320ebe9abbd45c0ecda89a0573ca5efce15244f7eb8f231ef183");
//        con.setRequestProperty("rapyd_request_timestamp", "1641216332");
//        con.setRequestProperty("rapyd_signature_salt", "c78c2b2632072d716dc3e225");
//        con.setRequestProperty("rapyd_signature", "YTlkNTM5NDJhYzMyYzNjNDNlYjE0MWI0NjkzY2NkZjg5Y2U0OGNiZWY1ZTQxYmVhMjc5OTc3ZTE3MWY3MzM1MA==");
//        con.setDoOutput(true);
//        String jsonInputString = "{\n" +
//                "    'first_name': 'John',\n" +
//                "    'last_name': 'Doe',\n" +
//                "    'email': '',\n" +
//                "    'ewallet_reference_id': 'John-Doe-02156666',\n" +
//                "    'metadata': {\n" +
//                "        'merchant_defined': true\n" +
//                "    },\n" +
//                "    'phone_number': '',\n" +
//                "    'type': 'person',\n" +
//                "    'contact': {\n" +
//                "        'phone_number': '+14155551311',\n" +
//                "        'email': 'johndoe@rapyd.net',\n" +
//                "        'first_name': 'John',\n" +
//                "        'last_name': 'Doe',\n" +
//                "        'mothers_name': 'Jane Smith',\n" +
//                "        'contact_type': 'personal',\n" +
//                "        'address': {\n" +
//                "            'name': 'John Doe',\n" +
//                "            'line_1': '123 Main Street',\n" +
//                "            'line_2': '',\n" +
//                "            'line_3': '',\n" +
//                "            'city': 'Anytown',\n" +
//                "            'state': 'NY',\n" +
//                "            'country': 'US',\n" +
//                "            'zip': '12345',\n" +
//                "            'phone_number': '+14155551111',\n" +
//                "            'metadata': {},\n" +
//                "            'canton': '',\n" +
//                "            'district': ''\n" +
//                "        },\n" +
//                "        'identification_type': 'PA',\n" +
//                "        'identification_number': '1234567890',\n" +
//                "        'date_of_birth': '11/22/2000',\n" +
//                "        'country': 'US',\n" +
//                "        'nationality': 'FR',\n" +
//                "        'metadata': {\n" +
//                "            'merchant_defined': true\n" +
//                "        }\n" +
//                "    }\n" +
//                "}";
//        try(OutputStream os = con.getOutputStream()) {
//            byte[] input = jsonInputString.getBytes("utf-8");
//            os.write(input, 0, input.length);
//        }
//        try(BufferedReader br = new BufferedReader(
//                new InputStreamReader(con.getInputStream(), "utf-8"))) {
//            StringBuilder response = new StringBuilder();
//            String responseLine = null;
//            while ((responseLine = br.readLine()) != null) {
//                response.append(responseLine.trim());
//            }
//            System.out.println(response.toString());
//            return response.toString();
        return null;
        }
    }
