package main.java.model;

public class Produto {
    private int id;
    private String nome;
    private String codigo;
    private String categoria;
    private double precoCusto;
    private double precoVenda;
    private int quantidade;
    private int estoqueMinimo;
    private boolean ativo;
    private int fornecedorId;  

    public Produto() {

    }

    public Produto(int id, String nome, String codigo, String categoria,double precoCusto, double precoVenda, int quantidade,
    int estoqueMinimo, boolean ativo, int fornecedorId) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.categoria = categoria;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
        this.quantidade = quantidade;
        this.estoqueMinimo = estoqueMinimo;
        this.ativo = ativo;
        this.fornecedorId = fornecedorId;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(double precoCusto) {
        this.precoCusto = precoCusto;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(int estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public int getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(int fornecedorId) {
        this.fornecedorId = fornecedorId;
    }
}
