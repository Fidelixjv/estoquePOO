package main.java.dao;

import main.java.model.Usuario;
import main.java.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public void cadastrar(Usuario u) {

    String sql = "INSERT INTO usuario (nome, login, senha, tipo, ativo) "
               + "VALUES (?, ?, ?, ?, true)";

    try (Connection conn = Conexao.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, u.getNome());
        stmt.setString(2, u.getLogin());
        stmt.setString(3, u.getSenha());
        stmt.setString(4, u.getTipo());

        stmt.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    public Usuario login(String login, String senha) {

        String sql = "SELECT * FROM usuario "
                   + "WHERE login = ? AND senha = ? AND ativo = true";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setLogin(rs.getString("login"));
                u.setTipo(rs.getString("tipo"));
                u.setAtivo(rs.getBoolean("ativo"));
                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
