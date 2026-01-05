package main.java.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static Connection connection;

    public static void conectar() {
        try {
            String url = "jdbc:mysql://localhost:3306/estoquePOO";
            String user = "root";
            String password = "vitor852";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conectado ao banco de dados!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}