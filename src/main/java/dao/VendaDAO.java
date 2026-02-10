package main.java.dao;

import main.java.model.ItemVenda;
import main.java.model.Venda;
import main.java.util.Conexao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

    // ===============================
    // INSERIR VENDA
    // ===============================
    public int inserir(Venda venda) {

        String sql = "INSERT INTO venda (cliente_id, usuario_id, valor_total) " +
                "VALUES (?, ?, ?)";

        int vendaId = -1;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, venda.getClienteId());
            ps.setInt(2, venda.getUsuarioId());
            ps.setDouble(3, venda.getValorTotal());

            ps.executeUpdate();

            // Obter o ID gerado
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    vendaId = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir venda: " + e.getMessage());
        }

        return vendaId;
    }

    // ===============================
    // INSERIR ITEM DE VENDA
    // ===============================
    public void inserirItem(ItemVenda item) {

        String sql = "INSERT INTO item_venda (venda_id, produto_id, quantidade, preco_unitario, subtotal) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, item.getVendaId());
            ps.setInt(2, item.getProdutoId());
            ps.setInt(3, item.getQuantidade());
            ps.setDouble(4, item.getPrecoUnitario());
            ps.setDouble(5, item.getSubtotal());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir item de venda: " + e.getMessage());
        }
    }

    // ===============================
    // LISTAR TODAS AS VENDAS
    // ===============================
    public List<Venda> listar() {

        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT * FROM venda ORDER BY data_venda DESC";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Venda v = new Venda();
                v.setId(rs.getInt("id"));
                v.setClienteId(rs.getInt("cliente_id"));
                v.setUsuarioId(rs.getInt("usuario_id"));
                v.setDataVenda(rs.getTimestamp("data_venda").toLocalDateTime());
                v.setValorTotal(rs.getDouble("valor_total"));

                vendas.add(v);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar vendas: " + e.getMessage());
        }

        return vendas;
    }

    // ===============================
    // BUSCAR VENDA POR ID
    // ===============================
    public Venda buscarPorId(int id) {

        String sql = "SELECT * FROM venda WHERE id = ?";
        Venda venda = null;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setClienteId(rs.getInt("cliente_id"));
                venda.setUsuarioId(rs.getInt("usuario_id"));
                venda.setDataVenda(rs.getTimestamp("data_venda").toLocalDateTime());
                venda.setValorTotal(rs.getDouble("valor_total"));

                // Buscar itens da venda
                venda.setItens(buscarItensPorVenda(id));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar venda: " + e.getMessage());
        }

        return venda;
    }

    // ===============================
    // BUSCAR ITENS DA VENDA
    // ===============================
    public List<ItemVenda> buscarItensPorVenda(int vendaId) {

        List<ItemVenda> itens = new ArrayList<>();
        String sql = "SELECT * FROM item_venda WHERE venda_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, vendaId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ItemVenda item = new ItemVenda();
                item.setId(rs.getInt("id"));
                item.setVendaId(rs.getInt("venda_id"));
                item.setProdutoId(rs.getInt("produto_id"));
                item.setQuantidade(rs.getInt("quantidade"));
                item.setPrecoUnitario(rs.getDouble("preco_unitario"));
                item.setSubtotal(rs.getDouble("subtotal"));

                itens.add(item);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar itens da venda: " + e.getMessage());
        }

        return itens;
    }

    // ===============================
    // LISTAR VENDAS POR CLIENTE
    // ===============================
    public List<Venda> buscarPorCliente(int clienteId) {

        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT * FROM venda WHERE cliente_id = ? ORDER BY data_venda DESC";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clienteId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Venda v = new Venda();
                v.setId(rs.getInt("id"));
                v.setClienteId(rs.getInt("cliente_id"));
                v.setUsuarioId(rs.getInt("usuario_id"));
                v.setDataVenda(rs.getTimestamp("data_venda").toLocalDateTime());
                v.setValorTotal(rs.getDouble("valor_total"));

                vendas.add(v);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar vendas do cliente: " + e.getMessage());
        }

        return vendas;
    }

    // ===============================
    // ATUALIZAR VALOR TOTAL DA VENDA
    // ===============================
    public void atualizarValorTotal(int vendaId, double novoValorTotal) {

        String sql = "UPDATE venda SET valor_total = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, novoValorTotal);
            ps.setInt(2, vendaId);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar valor total: " + e.getMessage());
        }
    }
}
