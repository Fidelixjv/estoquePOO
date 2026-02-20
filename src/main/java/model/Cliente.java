package main.java.model;

public class Cliente extends Pessoa {

    private String cpf;
    private String telefone;
    private String email;

    public Cliente() {
        super();
    }

    public Cliente(String cpf) {
        super();
        this.cpf = cpf;
    }

    public Cliente(int id, String cpf) {
        super(id, null);
        this.cpf = cpf;
    }

    public Cliente(int id, String nome, String cpf, String telefone, String email) {
        super(id, nome);
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
