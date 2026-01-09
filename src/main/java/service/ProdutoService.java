package main.java.service;
import main.java.dao.ProdutoDAO;
import main.java.model.Produto;

import java.sql.SQLException;
import java.util.List;
public class ProdutoService {

    private ProdutoDAO produtoDAO;

    public ProdutoService() {
        this.produtoDAO = new ProdutoDAO();
    }

    // ===============================
    // CADASTRAR PRODUTO
    // ===============================
    public void cadastrar(Produto produto) {

        // Regra 1: Nome obrigatório
        if (produto.getNome() == null || produto.getNome().isEmpty()) {
            throw new RuntimeException("Nome do produto é obrigatório.");
        }

        // Regra 2: Código obrigatório
        if (produto.getCodigo() == null || produto.getCodigo().isEmpty()) {
            throw new RuntimeException("Código do produto é obrigatório.");
        }

        // Regra 3: Preço de custo não pode ser negativo
        if (produto.getPrecoCusto() < 0) {
            throw new RuntimeException("Preço de custo não pode ser negativo.");
        }

        // Regra 4: Preço de venda deve ser maior que zero
        if (produto.getPrecoVenda() <= 0) {
            throw new RuntimeException("Preço de venda deve ser maior que zero.");
        }

        // Regra 5: Preço de venda não pode ser menor que o custo
        if (produto.getPrecoVenda() < produto.getPrecoCusto()) {
            throw new RuntimeException("Preço de venda não pode ser menor que o preço de custo.");
        }

        // Regra 6: Quantidade não pode ser negativa
        if (produto.getQuantidade() < 0) {
            throw new RuntimeException("Quantidade em estoque inválida.");
        }

        // Regra 7: Estoque mínimo não pode ser negativo
        if (produto.getEstoqueMinimo() < 0) {
            throw new RuntimeException("Estoque mínimo inválido.");
        }

        // Regra padrão
        produto.setAtivo(true);

        // Se tudo estiver OK → salva no banco
        produtoDAO.inserir(produto);
    }

    // ===============================
    // LISTAR PRODUTOS
    // ===============================
    public List<Produto> listar() {
        return produtoDAO.listar();
    }

    // ===============================
    // ATUALIZAR PRODUTO
    // ===============================
    public void atualizar(Produto produto) {

        // Regra: produto deve existir
        if (produto.getId() <= 0) {
            throw new RuntimeException("Produto inválido.");
        }

        // Regra: Código obrigatório
        if (produto.getCodigo() == null || produto.getCodigo().isEmpty()) {
            throw new RuntimeException("Código do produto é obrigatório.");
        }

        // Regra: Código deve ser único
        Produto existente = produtoDAO.buscarPorCodigo(produto.getCodigo());
        if (existente != null && existente.getId() != produto.getId()) {
            throw new RuntimeException("Código do produto já existe.");
        }

        // Reaplica regras principais
        if (produto.getPrecoVenda() < produto.getPrecoCusto()) {
            throw new RuntimeException("Preço de venda não pode ser menor que o custo.");
        }

        produtoDAO.atualizar(produto);
        System.out.println("Produto atualizado com sucesso!");
    }

    // ===============================
    // INATIVAR PRODUTO
    // ===============================
public void inativar(int id) {

    if (id <= 0) {
        System.out.println("ID inválido.");
        return;
    }

    try {
        // Busca o produto para verificar existência e status
        Produto produto = produtoDAO.buscarPorId(id);
        if (produto == null) {
            System.out.println("Produto não encontrado.");
            return;
        }

        if (!produto.isAtivo()) {
            System.out.println("Produto já estava inativo.");
            return;
        }

        // Tenta inativar
        boolean sucesso = produtoDAO.inativar(id);

        if (sucesso) {
            System.out.println("Produto inativado com sucesso!");
        } else {
            System.out.println("Erro ao inativar produto.");
        }

    } catch (SQLException e) {
        System.out.println("Erro ao inativar produto:");
        System.out.println(e.getMessage());
    }
}




    // ===============================
    // VERIFICAR ESTOQUE BAIXO
    // ===============================
    public boolean estoqueBaixo(Produto produto) {
        return produto.getQuantidade() <= produto.getEstoqueMinimo();
    }
    
}
