package main.java.view;

import java.sql.SQLException;

import main.java.model.Usuario;

public class MenuPrincipal {

    private Usuario usuario;

    public MenuPrincipal(Usuario usuario) {
        this.usuario = usuario;
    }

    public void menu() throws SQLException {

        if (usuario.getTipo().equals("ADMIN")) {
            new MenuAdmin().menu();
        } else if (usuario.getTipo().equals("OPERADOR")) {
            new MenuVenda(usuario).menu();
        } else {
            System.out.println("Perfil não autorizado.");
        }
    }
}
