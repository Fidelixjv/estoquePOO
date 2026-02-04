package main.java.service;

import main.java.dao.FornecedorDAO;
import main.java.model.Fornecedor;
import java.util.List;

public class FornecedorService {

    private FornecedorDAO fornecedorDAO = new FornecedorDAO();

    public void cadastrar(Fornecedor f) {
        fornecedorDAO.cadastrar(f);
    }

    public List<Fornecedor> listar() {
        return fornecedorDAO.listar();
    }
}
