package dao;

import model.Campus;
import util.Conexao;

import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CampusDao {

    public void inserir(Campus campus) {
        String sql = "INSERT INTO campus (nome) VALUES (?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, campus.getNome());
            statement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Campus Cadastrado com sucesso!");

        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Já existe um campus cadastrado com este nome.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar campus: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM campus WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);
            int linhasAfetadas = statement.executeUpdate();

            if(linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Campus Excluído com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum campus encontrado com esse ID.");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Não é possível excluir este campus pois existem alunos ou equipamentos vinculados a ele.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir campus: " + e.getMessage());
        }
    }

    public void atualizar(Campus campus) {
        String sql = "UPDATE campus SET nome = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, campus.getNome());
            statement.setInt(2, campus.getId());

            int linhasAfetadas = statement.executeUpdate();

            if(linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Campus Atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum campus encontrado para atualizar.");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Já existe outro campus com este nome.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar campus: " + e.getMessage());
        }
    }

    public List<Campus> listarTodos() {
        List<Campus> campi = new ArrayList<>();
        String sql = "SELECT * FROM campus ORDER BY nome";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Campus campus = new Campus();
                campus.setId(rs.getInt("id"));
                campus.setNome(rs.getString("nome"));

                campi.add(campus);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar campi: " + e.getMessage());
        }

        return campi;
    }
}