package main.java.dao;

import main.java.model.Produto;
import main.java.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    // ===============================
    // INSERIR PRODUTO
    // ===============================
    public void inserir(Produto produto) {

        String sql = "INSERT INTO produto " +
                "(nome, codigo, categoria, preco_custo, preco_venda, quantidade, estoque_minimo, ativo, fornecedor_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getCodigo());
            ps.setString(3, produto.getCategoria());
            ps.setDouble(4, produto.getPrecoCusto());
            ps.setDouble(5, produto.getPrecoVenda());
            ps.setInt(6, produto.getQuantidade());
            ps.setInt(7, produto.getEstoqueMinimo());
            ps.setBoolean(8, produto.isAtivo());
            ps.setInt(9, produto.getFornecedorId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ===============================
    // LISTAR TODOS OS PRODUTOS
    // ===============================
    public List<Produto> listar() {

        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setCodigo(rs.getString("codigo"));
                p.setCategoria(rs.getString("categoria"));
                p.setPrecoCusto(rs.getDouble("preco_custo"));
                p.setPrecoVenda(rs.getDouble("preco_venda"));
                p.setQuantidade(rs.getInt("quantidade"));
                p.setEstoqueMinimo(rs.getInt("estoque_minimo"));
                p.setAtivo(rs.getBoolean("ativo"));
                p.setFornecedorId(rs.getInt("fornecedor_id"));

                produtos.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }

    // ===============================
    // BUSCAR POR ID
    // ===============================
    public Produto buscarPorId(int id) {

        String sql = "SELECT * FROM produto WHERE id = ?";
        Produto produto = null;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setCodigo(rs.getString("codigo"));
                produto.setCategoria(rs.getString("categoria"));
                produto.setPrecoCusto(rs.getDouble("preco_custo"));
                produto.setPrecoVenda(rs.getDouble("preco_venda"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setEstoqueMinimo(rs.getInt("estoque_minimo"));
                produto.setAtivo(rs.getBoolean("ativo"));
                produto.setFornecedorId(rs.getInt("fornecedor_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produto;
    }

    // ===============================
    // ATUALIZAR PRODUTO
    // ===============================
    public void atualizar(Produto produto) {

        String sql = "UPDATE produto SET nome=?, codigo=?, categoria=?, preco_custo=?, preco_venda=?, " +
                "quantidade=?, estoque_minimo=?, ativo=?, fornecedor_id=? WHERE id=?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getCodigo());
            ps.setString(3, produto.getCategoria());
            ps.setDouble(4, produto.getPrecoCusto());
            ps.setDouble(5, produto.getPrecoVenda());
            ps.setInt(6, produto.getQuantidade());
            ps.setInt(7, produto.getEstoqueMinimo());
            ps.setBoolean(8, produto.isAtivo());
            ps.setInt(9, produto.getFornecedorId());
            ps.setInt(10, produto.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ===============================
    // INATIVAR PRODUTO (DELETE LÓGICO)
    // ===============================
public boolean inativar(int id) throws SQLException {

    String sql = "UPDATE produto SET ativo = false WHERE id = ? AND ativo = true";

    PreparedStatement stmt = Conexao.getConnection().prepareStatement(sql);
    stmt.setInt(1, id);

    int linhasAfetadas = stmt.executeUpdate();

    return linhasAfetadas > 0;
}



}

