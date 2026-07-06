package dao;

import model.Cidade;
import model.Curso;
import model.Usuario;
import util.Conexao;

import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class UsuarioDao {


    public Usuario autenticar(String email, String senha) {
        String sql = """
            SELECT
                p.id,
                p.nome,
                u.email,
                u.senha,
                u.tipo,
                u.status
            FROM usuario u
            INNER JOIN pessoa p
                ON p.id = u.pessoa_id
            WHERE u.email = ?
              AND u.senha = ?
            """;
        Usuario usuario = null;
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, senha);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setTipo(rs.getString("tipo"));
                usuario.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao realizar login:\n" + e.getMessage()
            );
        }
        return usuario;
    }

    public int inserir(Usuario usuario,
                       String dataNascimento,
                       Cidade cidade,
                       Curso curso) {

        String sqlPessoa =
                "INSERT INTO pessoa(nome) VALUES(?)";

        String sqlUsuario =
                "INSERT INTO usuario(pessoa_id,email,senha,tipo,status) VALUES(?,?,?,?,?)";

        String sqlAluno =
                "INSERT INTO aluno(usuario_id,data_nascimento,cidade_id,curso_id) VALUES(?,?,?,?)";

        String sqlAdministrador =
                "INSERT INTO administrador(usuario_id) VALUES(?)";

        String sqlVoluntario =
                "INSERT INTO voluntario(usuario_id,contato) VALUES(?,NULL)";

        try (Connection conn = Conexao.getConnection()) {

            conn.setAutoCommit(false);

            int pessoaId;



            try (PreparedStatement ps =
                         conn.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, usuario.getNome());

                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();

                if (!rs.next()) {

                    conn.rollback();

                    return 0;

                }

                pessoaId = rs.getInt(1);

            }



            try (PreparedStatement ps =
                         conn.prepareStatement(sqlUsuario)) {

                ps.setInt(1, pessoaId);

                ps.setString(2, usuario.getEmail());

                ps.setString(3, usuario.getSenha());

                ps.setString(4, usuario.getTipo());
                ps.setString(5, "PENDENTE");

                ps.executeUpdate();

            }



            if(usuario.getTipo().equals("ALUNO")){

                try(PreparedStatement ps =
                            conn.prepareStatement(sqlAluno)){

                    ps.setInt(1,pessoaId);

                    ps.setDate(2,
                            Date.valueOf(LocalDate.parse(dataNascimento)));

                    ps.setInt(3,cidade.getId());

                    ps.setInt(4,curso.getId());

                    ps.executeUpdate();

                }

            }else if(usuario.getTipo().equals("ADMINISTRADOR")){

                try(PreparedStatement ps =
                            conn.prepareStatement(sqlAdministrador)){

                    ps.setInt(1,pessoaId);

                    ps.executeUpdate();

                }

            }else{

                try(PreparedStatement ps =
                            conn.prepareStatement(sqlVoluntario)){

                    ps.setInt(1,pessoaId);

                    ps.executeUpdate();

                }

            }

            conn.commit();

            JOptionPane.showMessageDialog(
                    null,
                    "Usuário cadastrado com sucesso!"
            );

            return pessoaId;

        }catch(Exception e){

            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao cadastrar usuário:\n"+e.getMessage()
            );

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

        String sql = """
        SELECT
            p.id,
            p.nome,
            u.email,
            u.senha,
            u.tipo,
            u.status
        FROM usuario u
        INNER JOIN pessoa p
            ON u.pessoa_id = p.id
        ORDER BY p.nome
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setTipo(rs.getString("tipo"));
                usuario.setStatus(rs.getString("status"));

                usuarios.add(usuario);

            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao listar usuários: " + e.getMessage()
            );

        }

        return usuarios;

    }

    public void aprovarUsuario(int id) {

        String sql = "UPDATE usuario SET status='APROVADO' WHERE pessoa_id=?";

        try(Connection conn = Conexao.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, id);

            ps.executeUpdate();

        }catch(Exception e){

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );

        }

    }
}