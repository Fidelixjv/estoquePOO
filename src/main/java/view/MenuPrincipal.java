package main.java.view;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuPrincipal {

    private Scanner sc = new Scanner(System.in);

    public void menu() throws SQLException {

        int opcao;

        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Cadastro de Fornecedor");
            System.out.println("2 - Cadastro de Cliente");
            System.out.println("3 - Vendas");
            System.out.println("4 - Controle de Produtos");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    MenuFornecedor menuFornecedor = new MenuFornecedor();
                    menuFornecedor.menu();
                    break;

                case 2:
                    System.out.println("Menu Cliente (em breve)");
                    break;

                case 3:
                    System.out.println("Menu Vendas (em breve)");
                    break;

                case 4:
                    abrirMenuProdutos();
                    break;

                case 0:
                    System.out.println("Encerrando sistema...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    private void abrirMenuProdutos() throws SQLException {
        MenuProduto menuProduto = new MenuProduto();
        menuProduto.menu(); 
    }
}
