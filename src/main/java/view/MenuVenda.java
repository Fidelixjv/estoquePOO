package main.java.view;

import main.java.model.Cliente;
import main.java.model.ItemVenda;
import main.java.model.Produto;
import main.java.model.Usuario;
import main.java.model.Venda;
import main.java.service.ClienteService;
import main.java.service.ProdutoService;
import main.java.service.VendaService;

import java.util.List;
import java.util.Scanner;

public class MenuVenda {

    private VendaService vendaService;
    private ClienteService clienteService;
    private ProdutoService produtoService;
    private Usuario usuarioLogado;
    private Scanner sc;

    public MenuVenda(Usuario usuarioLogado) {
        this.vendaService = new VendaService();
        this.clienteService = new ClienteService();
        this.produtoService = new ProdutoService();
        this.usuarioLogado = usuarioLogado;
        this.sc = new Scanner(System.in);
    }

    // Construtor para menu admin (sem usuário específico)
    public MenuVenda() {
        this.vendaService = new VendaService();
        this.clienteService = new ClienteService();
        this.produtoService = new ProdutoService();
        this.usuarioLogado = new Usuario();
        this.usuarioLogado.setId(0); // ID 0 significa admin genérico
        this.sc = new Scanner(System.in);
    }

    public void menu() {
        // loop principal exibido ao usuário para navegar pelas funcionalidades
        // relacionadas a vendas.
        int opcao;

        do {
            System.out.println("\n=== MENU DE VENDAS ===");
            System.out.println("1 - Nova venda");
            System.out.println("2 - Listar vendas");
            System.out.println("3 - Ver detalhes da venda");
            System.out.println("4 - Relatório de vendas");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer do Scanner

            switch (opcao) {
                case 1:
                    novaVenda();          // processo completo de criação de venda
                    break;
                case 2:
                    listarVendas();       // mostra todas as vendas cadastradas
                    break;
                case 3:
                    verDetalhes();        // solicita ID e exibe detalhes de uma venda
                    break;
                case 4:
                    relatorio();          // imprime estatísticas de vendas
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
    // NOVA VENDA
    // ===============================
    private void novaVenda() {

        // passo a passo para criar uma nova venda
        System.out.println("\n=== NOVA VENDA ===");

        // busca cliente existente ou cadastra novo/ anônimo
        Cliente cliente = buscarOuCriarCliente();
        if (cliente == null) {
            System.out.println("❌ Operação cancelada.");
            return;
        }

        // inicializa objeto Venda e associa cliente e usuário
        Venda venda = new Venda();
        venda.setClienteId(cliente.getId() == 0 ? null : cliente.getId());
        venda.setUsuarioId(usuarioLogado.getId());

        // interage com o usuário para montar a lista de itens
        adicionarProdutos(venda);

        if (venda.getItens().isEmpty()) {
            System.out.println("❌ Nenhum produto foi adicionado. Venda cancelada.");
            return;
        }

        // apresenta resumo e pede confirmação final
        try {
            venda.calcularTotal();

            System.out.println("\n=== RESUMO DA VENDA ===");
            System.out.println("Cliente: " + cliente.getNome());
            System.out.println("Vendedor: " + usuarioLogado.getNome());
            System.out.println("Itens: " + venda.getItens().size());
            System.out.printf("Total: R$ %.2f%n", venda.getValorTotal());

            System.out.print("\nConfirmar venda? (S/N): ");
            String confirmacao = sc.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                int vendaId = vendaService.procesarVenda(venda);
                System.out.println("\n✓ Venda realizada com sucesso! ID: " + vendaId);
            } else {
                System.out.println("❌ Venda cancelada.");
            }

        } catch (RuntimeException e) {
            // exibe mensagem detalhada de erro caso alguma validação falhe
            System.out.println("❌ Erro ao processar venda: " + e.getMessage());
        }
    }

    // ===============================
    // BUSCAR OU CRIAR CLIENTE
    // ===============================
    // Exibe um pequeno submenu que permite ao usuário localizar um cliente pelo
    // CPF, cadastrar um novo cliente (somente com CPF) ou prosseguir como anônimo.
    private Cliente buscarOuCriarCliente() {

        System.out.println("\n=== CLIENTE ===");
        System.out.println("1 - Buscar cliente por CPF");
        System.out.println("2 - Registrar novo cliente (apenas CPF)");
        System.out.println("3 - Continuar sem cliente");
        System.out.print("Escolha: ");
        int op = sc.nextInt();
        sc.nextLine();

        if (op == 1) {
            System.out.print("Digite o CPF do cliente: ");
            String cpf = sc.nextLine();

            try {
                Cliente cliente = clienteService.buscarPorCpf(cpf);
                if (cliente != null) {
                    System.out.println("✓ Cliente encontrado!");
                    return cliente;
                } else {
                    System.out.println("❌ Cliente não encontrado.");
                    return null;
                }
            } catch (RuntimeException e) {
                System.out.println("❌ Erro: " + e.getMessage());
                return null;
            }
        } else if (op == 2) {
            System.out.print("CPF (11 dígitos): ");
            String cpf = sc.nextLine();

            try {
                Cliente novoCliente = new Cliente(cpf);
                clienteService.cadastrar(novoCliente);
                System.out.println("✓ Cliente cadastrado com sucesso!");
                return clienteService.buscarPorCpf(cpf);
            } catch (RuntimeException e) {
                System.out.println("❌ Erro ao cadastrar: " + e.getMessage());
                return null;
            }
        } else if (op == 3) {
            // Cliente anônimo
            Cliente clienteAnonimo = new Cliente();
            clienteAnonimo.setId(0);
            clienteAnonimo.setNome("CLIENTE ANÔNIMO");
            return clienteAnonimo;
        }

        return null;
    }

    // ===============================
    // ADICIONAR PRODUTOS À VENDA
    // ===============================
    // Percorre um loop onde o usuário pode inserir novos produtos, ver os
    // itens já adicionados ou finalizar a seleção.
    private void adicionarProdutos(Venda venda) {

        int opcao;

        do {
            System.out.println("\n=== ADICIONAR PRODUTOS ===");
            System.out.println("1 - Adicionar produto");
            System.out.println("2 - Ver itens adicionados");
            System.out.println("0 - Finalizar");
            System.out.print("Escolha: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    adicionarProduto(venda);
                    break;
                case 2:
                    listarItensVenda(venda);
                    break;
                case 0:
                    System.out.println("Finalizando adição de produtos...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    // ===============================
    // ADICIONAR UM PRODUTO
    // ===============================
    // Solicita o código do produto, verifica se está ativo e há estoque
    // suficiente. Se válido, cria um ItemVenda e o acrescenta à venda em curso.
    private void adicionarProduto(Venda venda) {

        System.out.print("Digite o código do produto: ");
        String codigo = sc.nextLine();

        Produto produto = produtoService.listar().stream()
                .filter(p -> p.getCodigo().equals(codigo) && p.isAtivo())
                .findFirst()
                .orElse(null);

        if (produto == null) {
            System.out.println("❌ Produto não encontrado ou inativo.");
            return;
        }

        System.out.println("\nProduto: " + produto.getNome());
        System.out.println("Preço: R$ " + produto.getPrecoVenda());
        System.out.println("Estoque: " + produto.getQuantidade());

        System.out.print("Quantidade: ");
        int quantidade = sc.nextInt();
        sc.nextLine();

        if (quantidade > produto.getQuantidade()) {
            System.out.println("❌ Quantidade insuficiente em estoque.");
            return;
        }

        ItemVenda item = new ItemVenda();
        item.setProdutoId(produto.getId());
        item.setQuantidade(quantidade);
        item.setPrecoUnitario(produto.getPrecoVenda());
        item.calcularSubtotal();

        venda.adicionarItem(item);
        System.out.println("✓ Produto adicionado com sucesso!");
    }

    // ===============================
    // LISTAR ITENS DA VENDA
    // ===============================
    // Imprime no console todos os itens adicionados até o momento, junto com
    // subtotais e total parcial.
    private void listarItensVenda(Venda venda) {

        if (venda.getItens().isEmpty()) {
            System.out.println("\n❌ Nenhum item adicionado ainda.");
            return;
        }

        System.out.println("\n=== ITENS DA VENDA ===");
        double total = 0;

        for (int i = 0; i < venda.getItens().size(); i++) {
            ItemVenda item = venda.getItens().get(i);
            Produto p = produtoService.listar().stream()
                    .filter(prod -> prod.getId() == item.getProdutoId())
                    .findFirst()
                    .orElse(null);

            if (p != null) {
                System.out.println((i + 1) + ". " + p.getNome());
                System.out.println("   Qtd: " + item.getQuantidade() + " x R$ " + item.getPrecoUnitario());
                System.out.printf("   Subtotal: R$ %.2f%n", item.getSubtotal());
                total += item.getSubtotal();
            }
        }

        System.out.printf("\nTotal: R$ %.2f%n", total);
    }

    // ===============================
    // LISTAR VENDAS
    // ===============================
    // Chama o serviço para obter todas as vendas e as formata para exibição.
    private void listarVendas() {

        List<Venda> vendas = vendaService.listar();

        if (vendas.isEmpty()) {
            System.out.println("\n❌ Nenhuma venda registrada.");
            return;
        }

        System.out.println("\n=== LISTA DE VENDAS ===");

        for (Venda v : vendas) {
            System.out.println("----------------------------");
            System.out.println("ID: " + v.getId());

            // mostrar nome do cliente (ou "CLIENTE ANÔNIMO" se não houver)
            String nomeCliente;
            if (v.getClienteId() != null && v.getClienteId() > 0) {
                try {
                    Cliente c = clienteService.buscarPorId(v.getClienteId());
                    nomeCliente = (c != null ? c.getNome() : "CLIENTE ANÔNIMO");
                } catch (RuntimeException e) {
                    nomeCliente = "CLIENTE ANÔNIMO";
                }
            } else {
                nomeCliente = "CLIENTE ANÔNIMO";
            }
            System.out.println("Cliente: " + nomeCliente);

            System.out.println("Data: " + v.getDataVenda());
            System.out.printf("Total: R$ %.2f%n", v.getValorTotal());
        }

        System.out.println("----------------------------");
    }

    // ===============================
    // VER DETALHES DA VENDA
    // ===============================
    // Solicita um ID ao usuário, busca a venda correspondente e mostra seus
    // itens e informações completas.
    private void verDetalhes() {

        System.out.print("Digite o ID da venda: ");
        int vendaId = sc.nextInt();
        sc.nextLine();

        try {
            Venda venda = vendaService.buscarPorId(vendaId);

            if (venda == null) {
                System.out.println("❌ Venda não encontrada.");
                return;
            }

            Cliente cliente = clienteService.buscarPorId(venda.getClienteId());

            System.out.println("\n=== DETALHES DA VENDA ===");
            System.out.println("ID: " + venda.getId());
            System.out.println("Cliente: " + (cliente != null ? cliente.getNome() : "Não informado"));
            System.out.println("Data: " + venda.getDataVenda());
            System.out.println("\nITENS:");

            for (ItemVenda item : venda.getItens()) {
                Produto p = produtoService.listar().stream()
                        .filter(prod -> prod.getId() == item.getProdutoId())
                        .findFirst()
                        .orElse(null);

                if (p != null) {
                    System.out.println("  - " + p.getNome());
                    System.out.println("    Qtd: " + item.getQuantidade() + " x R$ " + item.getPrecoUnitario());
                    System.out.printf("    Subtotal: R$ %.2f%n", item.getSubtotal());
                }
            }

            System.out.printf("\nTotal: R$ %.2f%n", venda.getValorTotal());

        } catch (RuntimeException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    // ===============================
    // RELATÓRIO DE VENDAS
    // ===============================
    // Gera estatísticas simples (total de vendas, receita, média, maior e
    // menor venda) usando métodos do serviço de vendas.
    private void relatorio() {

        List<Venda> vendas = vendaService.listar();

        if (vendas.isEmpty()) {
            System.out.println("\n❌ Nenhuma venda registrada.");
            return;
        }

        double totalReceita = vendaService.calcularReceitaTotal();
        double mediaVenda = totalReceita / vendas.size();

        System.out.println("\n=== RELATÓRIO DE VENDAS ===");
        System.out.println("Total de vendas: " + vendas.size());
        System.out.printf("Receita total: R$ %.2f%n", totalReceita);
        System.out.printf("Média por venda: R$ %.2f%n", mediaVenda);
        System.out.printf("Maior venda: R$ %.2f%n", vendas.stream().mapToDouble(Venda::getValorTotal).max().orElse(0));
        System.out.printf("Menor venda: R$ %.2f%n", vendas.stream().mapToDouble(Venda::getValorTotal).min().orElse(0));
    }
}
