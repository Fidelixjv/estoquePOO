package main.java.main;

import java.sql.SQLException;

import main.java.view.MenuLogin;

public class Main {
    public static void main(String[] args) throws SQLException {
        new MenuLogin().iniciar();
    }
}

