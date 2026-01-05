package main.java.main;

import main.java.service.Conexao;
import java.sql.Connection;


public class Main {
    public static void main(String[] args) {
        Conexao.conectar();
        Connection conn = Conexao.getConnection();

        }
    }
