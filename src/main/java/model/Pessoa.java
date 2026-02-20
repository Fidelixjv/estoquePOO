package main.java.model;

public abstract class Pessoa {

    private int id;
    private String nome;

    public Pessoa() {
    }

    public Pessoa(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

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

    // Cada subclasse deve fornecer seu documento de identificação (CPF/CNPJ)
    public abstract String getDocumento();

    // Utilitário opcional para exibir identificação completa
    public String getIdentificacao() {
        String doc = getDocumento();
        return (nome != null ? nome : "") + (doc != null && !doc.isEmpty() ? " (" + doc + ")" : "");
    }
}
