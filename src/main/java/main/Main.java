package main.java.main;

import java.sql.Connection;
import java.sql.SQLException;

import main.java.util.Conexao;
import main.java.view.MenuPrincipal;
import main.java.view.MenuProduto;


public class Main {
    public static void main(String[] args) throws SQLException {

        try {
            MenuPrincipal menu = new MenuPrincipal();
            menu.menu();
        } catch (Exception e) {
            System.out.println("Erro no sistema: " + e.getMessage());
        }

    }
}
