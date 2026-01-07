package main.java.main;

import java.sql.Connection;
import java.sql.SQLException;

import main.java.util.Conexao;
import main.java.view.MenuProduto;


public class Main {
    public static void main(String[] args) throws SQLException {
        
        MenuProduto menu = new MenuProduto();
        menu.menu();

    }
}
