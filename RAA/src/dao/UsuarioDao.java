package dao;

import model.Usuario;
import util.Conexao;

import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {


    public Usuario autenticar(String email, String senha) {
        String sql = """
    SELECT
        p.id,
        p.nome,
        u.email,
        u.senha,
        u.tipo
    FROM usuario u
    INNER JOIN pessoa p
        ON p.id = u.pessoa_id
    WHERE u.email = ?
      AND u.senha = ?
""";
        Usuario usuarioLogado = null;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setString(2, senha);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    usuarioLogado = new Usuario();
                    usuarioLogado.setId(rs.getInt("id"));
                    usuarioLogado.setNome(rs.getString("nome"));
                    usuarioLogado.setEmail(rs.getString("email"));
                    usuarioLogado.setSenha(rs.getString("senha"));
                    usuarioLogado.setTipo(rs.getString("tipo"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar fazer login: " + e.getMessage());
        }

        return usuarioLogado;
    }

    public int inserir(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getEmail());
            statement.setString(3, usuario.getSenha());

            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");

        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Já existe um usuário cadastrado com este e-mail.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuário: " + e.getMessage());
        }
        return 0;
    }

    public void deletar(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);
            int linhasAfetadas = statement.executeUpdate();

            if(linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum usuário encontrado com esse ID.");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Não é possível excluir este usuário pois ele possui vínculos (ex: é um aluno cadastrado).");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir usuário: " + e.getMessage());
        }
    }

    public void atualizar(Usuario usuario) {
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getEmail());
            statement.setString(3, usuario.getSenha());
            statement.setInt(4, usuario.getId());

            int linhasAfetadas = statement.executeUpdate();

            if(linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Dados do usuário atualizados com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum usuário encontrado para atualizar.");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Já existe outro usuário usando este e-mail.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario ORDER BY nome";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));

                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar usuários: " + e.getMessage());
        }

        return usuarios;
    }
}