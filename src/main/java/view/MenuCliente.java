package main.java.view;

import main.java.model.Cliente;
import main.java.service.ClienteService;

import java.util.List;
import java.util.Scanner;

public class MenuCliente {

    private ClienteService clienteService;
    private Scanner sc;

    public MenuCliente() {
        this.clienteService = new ClienteService();
        this.sc = new Scanner(System.in);
    }

    public void menu() {

        int opcao;

        do {
            System.out.println("\n=== MENU DE CLIENTES ===");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Listar clientes");
            System.out.println("3 - Buscar cliente");
            System.out.println("4 - Atualizar cliente");
            System.out.println("5 - Deletar cliente");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                    cadastrar();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    buscar();
                    break;
                case 4:
                    atualizar();
                    break;
                case 5:
                    deletar();
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
    // CADASTRAR CLIENTE
    // ===============================
    private void cadastrar() {

        System.out.println("\n=== CADASTRO DE CLIENTE ===");
        System.out.println("Cadastro simplificado - apenas CPF");

        System.out.print("CPF (11 dígitos): ");
        String cpf = sc.nextLine();

        try {
            Cliente c = new Cliente(cpf);
            clienteService.cadastrar(c);
            System.out.println("✓ Cliente cadastrado com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    // ===============================
    // LISTAR CLIENTES
    // ===============================
    private void listar() {

        List<Cliente> clientes = clienteService.listar();

        if (clientes.isEmpty()) {
            System.out.println("\n❌ Nenhum cliente cadastrado.");
            return;
        }

        System.out.println("\n=== LISTA DE CLIENTES ===");

        for (Cliente c : clientes) {
            System.out.println("----------------------------");
            System.out.println("ID: " + c.getId());
            System.out.println("Nome: " + c.getNome());
            System.out.println("CPF: " + c.getCpf());
            System.out.println("Telefone: " + c.getTelefone());
            System.out.println("Email: " + c.getEmail());
        }

        System.out.println("----------------------------");
    }

    // ===============================
    // BUSCAR CLIENTE
    // ===============================
    private void buscar() {

        System.out.println("\n=== BUSCAR CLIENTE ===");
        System.out.print("Digite o CPF do cliente: ");
        String cpf = sc.nextLine();

        try {
            Cliente c = clienteService.buscarPorCpf(cpf);

            if (c == null) {
                System.out.println("❌ Cliente não encontrado.");
                return;
            }

            System.out.println("\n=== DADOS DO CLIENTE ===");
            System.out.println("ID: " + c.getId());
            System.out.println("Nome: " + c.getNome());
            System.out.println("CPF: " + c.getCpf());
            System.out.println("Telefone: " + c.getTelefone());
            System.out.println("Email: " + c.getEmail());

        } catch (RuntimeException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    // ===============================
    // ATUALIZAR CLIENTE
    // ===============================
    private void atualizar() {

        System.out.println("\n=== ATUALIZAR CLIENTE ===");
        System.out.print("Digite o ID do cliente: ");
        int id = sc.nextInt();
        sc.nextLine();

        try {
            Cliente c = clienteService.buscarPorId(id);

            if (c == null) {
                System.out.println("❌ Cliente não encontrado.");
                return;
            }

            System.out.println("\nDados atuais:");
            System.out.println("Nome: " + c.getNome());
            System.out.println("CPF: " + c.getCpf());
            System.out.println("Telefone: " + c.getTelefone());
            System.out.println("Email: " + c.getEmail());

            System.out.println("\nDigite os novos dados (ou deixe em branco para manter):");

            System.out.print("Nome: ");
            String nome = sc.nextLine();
            if (!nome.isEmpty()) {
                c.setNome(nome);
            }

            System.out.print("CPF: ");
            String cpf = sc.nextLine();
            if (!cpf.isEmpty()) {
                c.setCpf(cpf);
            }

            System.out.print("Telefone: ");
            String telefone = sc.nextLine();
            if (!telefone.isEmpty()) {
                c.setTelefone(telefone);
            }

            System.out.print("Email: ");
            String email = sc.nextLine();
            if (!email.isEmpty()) {
                c.setEmail(email);
            }

            clienteService.atualizar(c);

        } catch (RuntimeException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    // ===============================
    // DELETAR CLIENTE
    // ===============================
    private void deletar() {

        System.out.println("\n=== DELETAR CLIENTE ===");
        System.out.print("Digite o ID do cliente: ");
        int id = sc.nextInt();
        sc.nextLine();

        try {
            System.out.print("Tem certeza? (S/N): ");
            String confirmacao = sc.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                clienteService.deletar(id);
            } else {
                System.out.println("Operação cancelada.");
            }

        } catch (RuntimeException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }
}
