package dao;

import model.Relato;
import model.Aluno;
import model.CategoriaRelato;
import model.StatusRelato;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelatoDao {

    public void criarRelato(Relato relato) {
        String sql = "insert into relato (titulo, descricao, local, data, usuario_anonimo, categoria, status, autor_id) values (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)
        ){
            statement.setString(1, relato.getTitulo());
            statement.setString(2, relato.getDescricao());
            statement.setString(3, relato.getLocal());
            statement.setDate(4, java.sql.Date.valueOf(relato.getData()));
            statement.setBoolean(5, relato.isUsuarioAnonimo());
            statement.setString(6, relato.getCategoria().name());
            statement.setString(7, relato.getStatus().name());
            statement.setInt(8, relato.getAutor().getId());
            statement.executeUpdate();

            System.out.println("Relato criado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    public List<Relato> mostrarRelatos() {
        List<Relato> relatos = new ArrayList<>();
        String sql = "select r.*, u.nome as nome_autor from relato r inner join usuario u on r.autor_id = u.id";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Relato relato = new Relato();

                relato.setId(resultSet.getInt("id"));
                relato.setTitulo(resultSet.getString("titulo"));
                relato.setDescricao(resultSet.getString("descricao"));
                relato.setLocal(resultSet.getString("local"));
                relato.setData(resultSet.getDate("data").toLocalDate());

                relato.setUsuarioAnonimo(resultSet.getBoolean("usuario_anonimo"));

                relato.setCategoria(CategoriaRelato.valueOf(resultSet.getString("categoria")));
                relato.setStatus(StatusRelato.valueOf(resultSet.getString("status")));

                Aluno autor = new Aluno();
                autor.setId(resultSet.getInt("autor_id"));

                autor.setNome(resultSet.getString("nome_autor"));
                relato.setAutor(autor);

                relatos.add(relato);
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return relatos;
    }
}
