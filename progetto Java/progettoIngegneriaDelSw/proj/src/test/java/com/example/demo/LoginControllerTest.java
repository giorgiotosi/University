package com.example.demo;

import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    @Test
    void testLogin() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/questura", "root", "");
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT cod_fiscale, password FROM login");
    }

}