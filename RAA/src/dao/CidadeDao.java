package dao;

import model.Cidade;
import util.Conexao;

import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CidadeDao {

    public void inserir(Cidade cidade) {
        String sql = "INSERT INTO cidade (nome) VALUES (?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, cidade.getNome());
            statement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Cidade cadastrada com sucesso!");

        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Já existe uma cidade cadastrada com este nome.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar cidade: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM cidade WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);
            int linhasAfetadas = statement.executeUpdate();

            if(linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Cidade excluída com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhuma cidade encontrada com esse ID.");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Não é possível excluir esta cidade pois existem alunos vinculados a ela.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir cidade: " + e.getMessage());
        }
    }

    public void atualizar(Cidade cidade) {
        String sql = "UPDATE cidade SET nome = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, cidade.getNome());
            statement.setInt(2, cidade.getId());

            int linhasAfetadas = statement.executeUpdate();

            if(linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Cidade atualizada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhuma cidade encontrada para atualizar.");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Já existe outra cidade com este nome.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar cidade: " + e.getMessage());
        }
    }

    public List<Cidade> listarTodas() {
        List<Cidade> cidades = new ArrayList<>();
        String sql = "SELECT * FROM cidade ORDER BY nome";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cidade cidade = new Cidade();
                cidade.setId(rs.getInt("id"));
                cidade.setNome(rs.getString("nome"));

                cidades.add(cidade);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar cidades: " + e.getMessage());
        }

        return cidades;
    }
}