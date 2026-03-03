package main.java.service;

import main.java.dao.ProdutoDAO;
import main.java.dao.VendaDAO;
import main.java.model.ItemVenda;
import main.java.model.Produto;
import main.java.model.Venda;

import java.util.List;

public class VendaService {

    private VendaDAO vendaDAO;
    private ProdutoDAO produtoDAO;

    public VendaService() {
        // Inicializa os DAOs necessários para operar sobre vendas e produtos
        this.vendaDAO = new VendaDAO();
        this.produtoDAO = new ProdutoDAO();
    }

    // ===============================
    // PROCESSAR NOVA VENDA
    // ===============================
    /*
     * Esta é a rotina central responsável por receber um objeto Venda preenchido
     * pela interface de usuário e executar todas as validações e regras de
     * negócio antes de delegar a persistência ao DAO.
     * Retorna o ID gerado ou lança RuntimeException em caso de falhas.
     */
    public int procesarVenda(Venda venda) {

        // Regra 1: Usuário é obrigatório
        if (venda.getUsuarioId() <= 0) {
            throw new RuntimeException("Usuário inválido.");
        }

        // Regra 2: Venda deve ter pelo menos um item
        if (venda.getItens().isEmpty()) {
            throw new RuntimeException("A venda deve conter pelo menos um produto.");
        }

        // Regra 3: Validar cada item individualmente (produto existe, estoque,
        // preço, etc.)
        for (ItemVenda item : venda.getItens()) {
            validarItem(item);
        }

        // Regra 4: Calcular total aproveitando método do modelo
        venda.calcularTotal();

        // Regra 5: Valor total deve ser maior que zero
        if (venda.getValorTotal() <= 0) {
            throw new RuntimeException("Valor total da venda inválido.");
        }

        // Persistência: inserir a venda no banco de dados
        int vendaId = vendaDAO.inserir(venda);

        // verificar se inserção foi bem‑sucedida (ID gerado positivo)
        if (vendaId <= 0) {
            throw new RuntimeException("Falha ao gravar a venda no banco de dados.");
        }

        // Após gravar a venda, salva cada item associado e ajusta estoque
        for (ItemVenda item : venda.getItens()) {
            item.setVendaId(vendaId);
            vendaDAO.inserirItem(item);

            // Reduzir estoque do produto correspondente
            atualizarEstoque(item.getProdutoId(), -item.getQuantidade());
        }

        return vendaId;
    }

    // ===============================
    // VALIDAR ITEM DE VENDA
    // ===============================
    /*
     * Executa verificações específicas para cada item antes de permitir que ele
     * faça parte da venda: produto existente, ativo, estoque suficiente e
     * preço válido. Também calcula o subtotal do item.
     */
    private void validarItem(ItemVenda item) {

        // Regra 1: Produto deve existir
        if (item.getProdutoId() <= 0) {
            throw new RuntimeException("Produto inválido.");
        }

        Produto produto = produtoDAO.buscarPorId(item.getProdutoId());
        if (produto == null) {
            throw new RuntimeException("Produto não encontrado.");
        }

        if (!produto.isAtivo()) {
            throw new RuntimeException("Produto '" + produto.getNome() + "' está inativo.");
        }

        // Regra 2: Quantidade deve ser maior que zero
        if (item.getQuantidade() <= 0) {
            throw new RuntimeException("Quantidade do produto '" + produto.getNome() + "' deve ser maior que zero.");
        }

        // Regra 3: Quantidade deve estar disponível em estoque
        if (item.getQuantidade() > produto.getQuantidade()) {
            throw new RuntimeException("Quantidade insuficiente em estoque para o produto '" + produto.getNome() + "'.");
        }

        // Regra 4: Preço unitário deve ser maior que zero
        if (item.getPrecoUnitario() <= 0) {
            throw new RuntimeException("Preço unitário inválido para o produto '" + produto.getNome() + "'.");
        }

        // Calcular subtotal
        item.calcularSubtotal();
    }

    // ===============================
    // ATUALIZAR ESTOQUE
    // ===============================
    /*
     * Busca o produto pelo ID, ajusta a quantidade em estoque e salva a
     * alteração via ProdutoDAO. O parâmetro 'quantidade' pode ser negativo
     * para reduzir estoque ou positivo para repor.
     */
    private void atualizarEstoque(int produtoId, int quantidade) {

        Produto produto = produtoDAO.buscarPorId(produtoId);
        if (produto != null) {
            produto.setQuantidade(produto.getQuantidade() + quantidade);
            produtoDAO.atualizar(produto);
        }
    }

    // ===============================
    // LISTAR VENDAS
    // ===============================
    public List<Venda> listar() {
        return vendaDAO.listar();
    }

    // ===============================
    // BUSCAR VENDA POR ID
    // ===============================
    public Venda buscarPorId(int id) {
        if (id <= 0) {
            throw new RuntimeException("ID inválido.");
        }
        return vendaDAO.buscarPorId(id);
    }

    // ===============================
    // LISTAR VENDAS POR CLIENTE
    // ===============================
    public List<Venda> listarPorCliente(int clienteId) {
        if (clienteId <= 0) {
            throw new RuntimeException("ID do cliente inválido.");
        }
        return vendaDAO.buscarPorCliente(clienteId);
    }

    // ===============================
    // CALCULAR RECEITA TOTAL
    // ===============================
    public double calcularReceitaTotal() {
        List<Venda> vendas = listar();
        return vendas.stream().mapToDouble(Venda::getValorTotal).sum();
    }

    // ===============================
    // CALCULAR RECEITA POR PERÍODO (simplificado)
    // ===============================
    public double calcularReceita(List<Venda> vendas) {
        return vendas.stream().mapToDouble(Venda::getValorTotal).sum();
    }
}
