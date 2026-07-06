package dao;

import model.Aluno;
import model.Cidade;
import model.Curso;
import util.Conexao;

import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDao {

    public void inserir(Aluno aluno) {
        String sqlUsuario = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";
        String sqlAluno = "INSERT INTO aluno (id_usuario, data_nascimento, id_cidade, id_curso) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection()) {

            conn.setAutoCommit(false);

            try (PreparedStatement stmtUser = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                stmtUser.setString(1, aluno.getNome());
                stmtUser.setString(2, aluno.getEmail());
                stmtUser.setString(3, aluno.getSenha());
                stmtUser.executeUpdate();


                ResultSet rs = stmtUser.getGeneratedKeys();
                if (rs.next()) {
                    int idUsuario = rs.getInt(1);
                    aluno.setId(idUsuario);

                    try (PreparedStatement stmtAluno = conn.prepareStatement(sqlAluno)) {
                        stmtAluno.setInt(1, idUsuario);
                        stmtAluno.setDate(2, Date.valueOf(aluno.getDataNascimento()));
                        stmtAluno.setInt(3, aluno.getCidade().getId());
                        stmtAluno.setInt(4, aluno.getCurso().getId());
                        stmtAluno.executeUpdate();
                    }
                }

                conn.commit();
                JOptionPane.showMessageDialog(null, "Aluno Cadastrado com sucesso!");

            } catch (SQLIntegrityConstraintViolationException e) {
                conn.rollback();
                JOptionPane.showMessageDialog(null, "Já existe um usuário cadastrado com este e-mail (ou dados de Cidade/Curso inválidos).");
            } catch (SQLException e) {
                conn.rollback();
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar aluno: " + e.getMessage());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de conexão com o banco: " + e.getMessage());
        }
    }

    public void deletar(int idUsuario) {
        String sqlAluno = "DELETE FROM aluno WHERE id_usuario = ?";
        String sqlUsuario = "DELETE FROM usuario WHERE id = ?";

        try (Connection conn = Conexao.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtAluno = conn.prepareStatement(sqlAluno)) {
                stmtAluno.setInt(1, idUsuario);
                stmtAluno.executeUpdate(); // Deleta o filho primeiro

                try (PreparedStatement stmtUser = conn.prepareStatement(sqlUsuario)) {
                    stmtUser.setInt(1, idUsuario);
                    int linhasAfetadas = stmtUser.executeUpdate(); // Deleta o pai

                    if(linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(null, "Aluno Excluído com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Nenhum aluno encontrado com esse ID.");
                    }
                }
                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                JOptionPane.showMessageDialog(null, "Erro ao excluir aluno: " + e.getMessage());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de conexão com o banco: " + e.getMessage());
        }
    }

    public void atualizar(Aluno aluno) {
        String sqlUsuario = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";
        String sqlAluno = "UPDATE aluno SET data_nascimento = ?, id_cidade = ?, id_curso = ? WHERE id_usuario = ?";

        try (Connection conn = Conexao.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtUser = conn.prepareStatement(sqlUsuario)) {
                stmtUser.setString(1, aluno.getNome());
                stmtUser.setString(2, aluno.getEmail());
                stmtUser.setString(3, aluno.getSenha());
                stmtUser.setInt(4, aluno.getId());
                stmtUser.executeUpdate();

                try (PreparedStatement stmtAluno = conn.prepareStatement(sqlAluno)) {
                    stmtAluno.setDate(1, Date.valueOf(aluno.getDataNascimento()));
                    stmtAluno.setInt(2, aluno.getCidade().getId());
                    stmtAluno.setInt(3, aluno.getCurso().getId());
                    stmtAluno.setInt(4, aluno.getId());
                    int linhasAfetadas = stmtAluno.executeUpdate();

                    if(linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(null, "Aluno Atualizado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Nenhum aluno encontrado para atualizar.");
                    }
                }
                conn.commit();
            } catch (SQLIntegrityConstraintViolationException e) {
                conn.rollback();
                JOptionPane.showMessageDialog(null, "Conflito de dados ao atualizar (e-mail duplicado, etc).");
            } catch (SQLException e) {
                conn.rollback();
                JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + e.getMessage());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de conexão com o banco: " + e.getMessage());
        }
    }

    public List<Aluno> listarTodos() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT u.id, u.nome, u.email, u.senha, a.data_nascimento, " +
                "c.id AS id_cidade, c.nome AS nome_cidade, cu.id AS id_curso, cu.nome AS nome_curso " +
                "FROM aluno a " +
                "JOIN usuario u ON a.id_usuario = u.id " +
                "JOIN cidade c ON a.id_cidade = c.id " +
                "JOIN curso cu ON a.id_curso = cu.id";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setEmail(rs.getString("email"));
                aluno.setSenha(rs.getString("senha"));
                aluno.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());

                Cidade cidade = new Cidade(rs.getInt("id_cidade"), rs.getString("nome_cidade"));
                Curso curso = new Curso(rs.getInt("id_curso"), rs.getString("nome_curso"));

                aluno.setCidade(cidade);
                aluno.setCurso(curso);

                alunos.add(aluno);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar alunos: " + e.getMessage());
        }
        return alunos;
    }

    public Aluno buscarPorUsuarioId(int usuarioId){

        String sql = """
            SELECT *
            FROM aluno
            WHERE usuario_id = ?
            """;

        try(Connection conn = Conexao.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, usuarioId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                Aluno aluno = new Aluno();

                aluno.setId(rs.getInt("usuario_id"));

                return aluno;

            }

        }catch(Exception e){

            e.printStackTrace();

        }

        return null;

    }
}
