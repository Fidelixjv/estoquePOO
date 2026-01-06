package main.java.view;
import main.java.model.Produto;
import main.java.service.ProdutoService;

import java.util.List;
import java.util.Scanner;
public class MenuProduto {

    private ProdutoService produtoService;
    private Scanner sc;

    public MenuProduto() {
        produtoService = new ProdutoService();
        sc = new Scanner(System.in);
    }

    public void menu() {

        int opcao;

        do {
            System.out.println("\n=== MENU DE PRODUTOS ===");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Listar produtos");
            System.out.println("3 - Atualizar produto");
            System.out.println("4 - Inativar produto");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer

switch (opcao) {
    case 1:
        cadastrar();
        break;
    case 2:
        listarProdutos();
        break;
    case 0:
        System.out.println("Saindo...");
        break;
    default:
        System.out.println("Opção inválida!");
}


        } while (opcao != 0);
    }

    private void listarProdutos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarProdutos'");
    }

    // ===============================
    // CADASTRAR
    // ===============================
    private void cadastrar() {

        Produto p = new Produto();

        System.out.print("Nome: ");
        p.setNome(sc.nextLine());

        System.out.print("Código: ");
        p.setCodigo(sc.nextLine());

        System.out.print("Categoria: ");
        p.setCategoria(sc.nextLine());

        System.out.print("Preço de custo: ");
        p.setPrecoCusto(sc.nextDouble());

        System.out.print("Preço de venda: ");
        p.setPrecoVenda(sc.nextDouble());

        System.out.print("Quantidade: ");
        p.setQuantidade(sc.nextInt());

        System.out.print("Estoque mínimo: ");
        p.setEstoqueMinimo(sc.nextInt());

        System.out.print("ID do fornecedor: ");
        p.setFornecedorId(sc.nextInt());

        produtoService.cadastrar(p);

        System.out.println("Produto cadastrado com sucesso!");
    }

    // ===============================
    // LISTAR
    // ===============================
    private void listar() {

        List<Produto> produtos = produtoService.listar();

        System.out.println("\n--- LISTA DE PRODUTOS ---");

        for (Produto p : produtos) {
            System.out.println(
                "ID: " + p.getId() +
                " | Nome: " + p.getNome() +
                " | Código: " + p.getCodigo() +
                " | Preço: " + p.getPrecoVenda() +
                " | Qtd: " + p.getQuantidade() +
                " | Ativo: " + p.isAtivo()
            );
        }
    }

    // ===============================
    // ATUALIZAR
    // ===============================
    private void atualizar() {

        Produto p = new Produto();

        System.out.print("ID do produto: ");
        p.setId(sc.nextInt());
        sc.nextLine();

        System.out.print("Novo nome: ");
        p.setNome(sc.nextLine());

        System.out.print("Novo código: ");
        p.setCodigo(sc.nextLine());

        System.out.print("Categoria: ");
        p.setCategoria(sc.nextLine());

        System.out.print("Preço de custo: ");
        p.setPrecoCusto(sc.nextDouble());

        System.out.print("Preço de venda: ");
        p.setPrecoVenda(sc.nextDouble());

        System.out.print("Quantidade: ");
        p.setQuantidade(sc.nextInt());

        System.out.print("Estoque mínimo: ");
        p.setEstoqueMinimo(sc.nextInt());

        System.out.print("Ativo (true/false): ");
        p.setAtivo(sc.nextBoolean());

        System.out.print("ID do fornecedor: ");
        p.setFornecedorId(sc.nextInt());

        produtoService.atualizar(p);

        System.out.println("Produto atualizado com sucesso!");
    }

    // ===============================
    // INATIVAR
    // ===============================
    private void inativar() {

        System.out.print("ID do produto: ");
        int id = sc.nextInt();

        produtoService.inativar(id);

        System.out.println("Produto inativado com sucesso!");
    }
}
