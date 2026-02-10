package main.java.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venda {

    private int id;
    private int clienteId;
    private int usuarioId;
    private LocalDateTime dataVenda;
    private double valorTotal;
    private List<ItemVenda> itens;

    public Venda() {
        this.itens = new ArrayList<>();
        this.dataVenda = LocalDateTime.now();
    }

    public Venda(int id, int clienteId, int usuarioId, LocalDateTime dataVenda, double valorTotal) {
        this.id = id;
        this.clienteId = clienteId;
        this.usuarioId = usuarioId;
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
        this.itens = new ArrayList<>();
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    public void adicionarItem(ItemVenda item) {
        this.itens.add(item);
    }

    public void removerItem(ItemVenda item) {
        this.itens.remove(item);
    }

    public void calcularTotal() {
        this.valorTotal = itens.stream().mapToDouble(ItemVenda::getSubtotal).sum();
    }
}
