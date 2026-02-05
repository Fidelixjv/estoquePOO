package main.java.service;

import main.java.dao.UsuarioDAO;
import main.java.model.Usuario;

public class UsuarioService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void cadastrar(Usuario u) {
    usuarioDAO.cadastrar(u);
}


    public Usuario login(String login, String senha) {
        return usuarioDAO.login(login, senha);
    }
}
