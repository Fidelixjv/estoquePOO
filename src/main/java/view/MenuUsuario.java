package main.java.view;

import main.java.model.Usuario;
import main.java.service.UsuarioService;

import java.util.Scanner;

public class MenuUsuario {

    private UsuarioService usuarioService = new UsuarioService();
    private Scanner sc = new Scanner(System.in);

    public void menu() {

        int op;

        do {
            System.out.println("\n=== MENU USUÁRIOS ===");
            System.out.println("1 - Cadastrar usuário");
            System.out.println("0 - Voltar");

            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1:
                    cadastrar();
                    break;
            }

        } while (op != 0);
    }

    private void cadastrar() {

        Usuario u = new Usuario();

        System.out.print("Nome: ");
        u.setNome(sc.nextLine());

        System.out.print("Login: ");
        u.setLogin(sc.nextLine());

        System.out.print("Senha: ");
        u.setSenha(sc.nextLine());

        System.out.print("Tipo (ADMIN / OPERADOR): ");
        u.setTipo(sc.nextLine().toUpperCase());

        usuarioService.cadastrar(u);

        System.out.println("Usuário cadastrado com sucesso!");
    }
}
