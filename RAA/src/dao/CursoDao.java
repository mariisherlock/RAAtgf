package dao;

import model.Campus;
import model.Curso;
import util.Conexao;

import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDao {

    public void inserir(Curso curso) {
        String sql = "INSERT INTO curso (nome, id_campus) VALUES (?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, curso.getNome());
            statement.setInt(2, curso.getCampus().getId());

            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Curso cadastrado com sucesso!");

        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Já existe um curso com este nome ou o Campus selecionado é inválido.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar curso: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM curso WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);
            int linhasAfetadas = statement.executeUpdate();

            if(linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Curso excluído com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum curso encontrado com esse ID.");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Não é possível excluir este curso pois existem alunos vinculados a ele.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir curso: " + e.getMessage());
        }
    }

    public void atualizar(Curso curso) {
        String sql = "UPDATE curso SET nome = ?, id_campus = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, curso.getNome());
            statement.setInt(2, curso.getCampus().getId());
            statement.setInt(3, curso.getId());

            int linhasAfetadas = statement.executeUpdate();

            if(linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Curso atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum curso encontrado para atualizar.");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Erro de integridade ao atualizar os dados do curso.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar curso: " + e.getMessage());
        }
    }

    public List<Curso> listarTodos() {
        List<Curso> cursos = new ArrayList<>();
        // Fazemos um JOIN para buscar os dados do Curso junto com os dados do Campus dele
        String sql = "SELECT cu.id AS id_curso, cu.nome AS nome_curso, " +
                "ca.id AS id_campus, ca.nome AS nome_campus " +
                "FROM curso cu " +
                "JOIN campus ca ON cu.id_campus = ca.id " +
                "ORDER BY cu.nome";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Campus campus = new Campus();
                campus.setId(rs.getInt("id_campus"));
                campus.setNome(rs.getString("nome_campus"));


                Curso curso = new Curso();
                curso.setId(rs.getInt("id_curso"));
                curso.setNome(rs.getString("nome_curso"));
                curso.setCampus(campus);

                cursos.add(curso);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar cursos: " + e.getMessage());
        }

        return cursos;
    }
}