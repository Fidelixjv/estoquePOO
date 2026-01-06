package main.java.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL =
            "jdbc:mysql://localhost:3306/estoquePOO?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "vitor852";

    public static Connection getConnection() {

        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conectado ao banco de dados!");
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco", e);
        }
    }

    public static void conectar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'conectar'");
    }
}
