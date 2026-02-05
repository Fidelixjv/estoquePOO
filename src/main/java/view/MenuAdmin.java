package main.java.view;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuAdmin {

    private Scanner sc = new Scanner(System.in);

    public void menu() throws SQLException {

        int op;

        do {
            System.out.println("\n=== MENU ADMIN ===");
            System.out.println("1 - Produtos");
            System.out.println("2 - Fornecedores");
            System.out.println("3 - Vendas");
            System.out.println("4 - Usuários");
            System.out.println("0 - Sair");

            op = sc.nextInt();

            switch (op) {
                case 1:
                    new MenuProduto().menu();
                    break;
                case 2:
                    new MenuFornecedor().menu();
                    break;
                case 3:
                    new MenuVenda().menu();
                    break;
                case 4:
                    new MenuUsuario().menu();
                    break;
            }

        } while (op != 0);
    }
}
