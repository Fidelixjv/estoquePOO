package main.java.view;

import main.java.model.Fornecedor;
import main.java.service.FornecedorService;

import java.util.List;
import java.util.Scanner;

public class MenuFornecedor {

    private Scanner sc = new Scanner(System.in);
    private FornecedorService fornecedorService = new FornecedorService();

    public void menu() {

        int opcao;

        do {
            System.out.println("\n=== MENU FORNECEDOR ===");
            System.out.println("1 - Cadastrar fornecedor");
            System.out.println("2 - Listar fornecedores");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    cadastrar();
                    break;
                case 2:
                    listar();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    // ===============================
    // CADASTRAR FORNECEDOR
    // ===============================
    private void cadastrar() {

        Fornecedor f = new Fornecedor();

        System.out.print("Nome: ");
        f.setNome(sc.nextLine());

        System.out.print("CNPJ: ");
        f.setCnpj(sc.nextLine());

        System.out.print("Telefone: ");
        f.setTelefone(sc.nextLine());

        System.out.print("Email: ");
        f.setEmail(sc.nextLine());

        fornecedorService.cadastrar(f);

        System.out.println("Fornecedor cadastrado com sucesso!");
    }

    private void listar() {
        List<Fornecedor> fornecedores = fornecedorService.listar();

        if (fornecedores.isEmpty()) {
            System.out.println("Nenhum fornecedor cadastrado.");
            return;
        }

        System.out.println("\n--- LISTA DE FORNECEDORES ---");

        for (Fornecedor f : fornecedores) {
            System.out.println(
                "ID: " + f.getId() +
                " | Nome: " + f.getNome() +
                " | CNPJ: " + f.getCnpj() +
                " | Telefone: " + f.getTelefone() +
                " | Ativo: " + (f.isAtivo() ? "Sim" : "Não")
            );
        }
    }
}
