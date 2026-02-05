package main.java.view;

import main.java.model.Usuario;
import main.java.service.UsuarioService;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuLogin {

    private UsuarioService usuarioService = new UsuarioService();
    private Scanner sc = new Scanner(System.in);

    public void iniciar() throws SQLException {

        System.out.println("=== LOGIN DO SISTEMA ===");

        System.out.print("Login: ");
        String login = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        Usuario usuario = usuarioService.login(login, senha);

        if (usuario == null) {
            System.out.println("Login ou senha inválidos!");
            return;
        }

        System.out.println("\nBem-vindo, " + usuario.getNome());

        MenuPrincipal menuPrincipal = new MenuPrincipal(usuario);
        menuPrincipal.menu();
    }
}
