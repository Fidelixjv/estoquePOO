package main.java.service;

import main.java.dao.ClienteDAO;
import main.java.model.Cliente;

import java.util.List;

public class ClienteService {

    private ClienteDAO clienteDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }

    // ===============================
    // CADASTRAR CLIENTE
    // ===============================
    public void cadastrar(Cliente cliente) {

        // Regra 1: CPF obrigatório
        if (cliente.getCpf() == null || cliente.getCpf().isEmpty()) {
            throw new RuntimeException("CPF é obrigatório.");
        }

        // Regra 2: Validar formato CPF (11 dígitos)
        if (!cliente.getCpf().matches("\\d{11}")) {
            throw new RuntimeException("CPF deve conter 11 dígitos.");
        }

        // Regra 3: CPF deve ser único
        Cliente existente = clienteDAO.buscarPorCpf(cliente.getCpf());
        if (existente != null) {
            throw new RuntimeException("CPF já cadastrado.");
        }

        // Se tudo estiver OK → salva no banco
        clienteDAO.inserir(cliente);
    }

    // ===============================
    // LISTAR CLIENTES
    // ===============================
    public List<Cliente> listar() {
        return clienteDAO.listar();
    }

    // ===============================
    // BUSCAR CLIENTE POR ID
    // ===============================
    public Cliente buscarPorId(int id) {
        if (id <= 0) {
            throw new RuntimeException("ID inválido.");
        }
        return clienteDAO.buscarPorId(id);
    }

    // ===============================
    // BUSCAR CLIENTE POR CPF
    // ===============================
    public Cliente buscarPorCpf(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            throw new RuntimeException("CPF inválido.");
        }
        return clienteDAO.buscarPorCpf(cpf);
    }

    // ===============================
    // ATUALIZAR CLIENTE
    // ===============================
    public void atualizar(Cliente cliente) {

        // Regra: cliente deve existir
        if (cliente.getId() <= 0) {
            throw new RuntimeException("Cliente inválido.");
        }

        Cliente existente = clienteDAO.buscarPorId(cliente.getId());
        if (existente == null) {
            throw new RuntimeException("Cliente não encontrado.");
        }

        // Regra: Nome obrigatório
        if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
            throw new RuntimeException("Nome do cliente é obrigatório.");
        }

        // Regra: CPF deve ser único (se foi alterado)
        if (!existente.getCpf().equals(cliente.getCpf())) {
            Cliente cpfExistente = clienteDAO.buscarPorCpf(cliente.getCpf());
            if (cpfExistente != null) {
                throw new RuntimeException("CPF já cadastrado.");
            }
        }

        clienteDAO.atualizar(cliente);
        System.out.println("Cliente atualizado com sucesso!");
    }

    // ===============================
    // DELETAR CLIENTE
    // ===============================
    public void deletar(int id) {

        if (id <= 0) {
            throw new RuntimeException("ID inválido.");
        }

        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente == null) {
            throw new RuntimeException("Cliente não encontrado.");
        }

        boolean excluido = clienteDAO.deletar(id);
        if (!excluido) {
            throw new RuntimeException("Não é possível deletar cliente: existem vendas relacionadas.");
        }
        System.out.println("Cliente deletado com sucesso!");
    }
}
