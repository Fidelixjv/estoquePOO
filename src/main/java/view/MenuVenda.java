package main.java.view;

import java.util.Scanner;

public class MenuVenda {

    private Scanner sc = new Scanner(System.in);

    public void menu() {

        int op;

        do {
            System.out.println("\n=== MENU VENDAS ===");
            System.out.println("1 - Nova venda");
            System.out.println("2 - Listar vendas");
            System.out.println("0 - Sair");

            op = sc.nextInt();

            switch (op) {
                case 1:
                    System.out.println("Nova venda...");
                    break;
                case 2:
                    System.out.println("Listando vendas...");
                    break;
            }

        } while (op != 0);
    }
}
