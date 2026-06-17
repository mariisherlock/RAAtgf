package dao;

import model.Voluntario;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VoluntarioDao {

    private UsuarioDao usuarioDao = new UsuarioDao();

    public void cadastrarVoluntario(Voluntario voluntario) {
        voluntario.setTipo("VOLUNTARIO");

        int idUsuario = usuarioDao.inserir(voluntario);

        if (idUsuario > 0) {
            String sql = "insert into voluntario (usuario_id, contato) values (?,?)";

            try (Connection conn = Conexao.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)
            ) {
                statement.setInt(1, idUsuario);
                statement.setString(2, voluntario.getContato());
                statement.executeUpdate();

                System.out.println("Voluntario cadastrado com sucesso!");

            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Nao foi possivel cadastrar o voluntario");
        }
    }

    public Voluntario mostrarInfos(int idUsuario) {

        String sql = "select u.nome, u.email, v.contato from usuario u " +
                "inner join voluntario v on u.id = v.usuario_id where u.id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, idUsuario);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    Voluntario voluntario = new Voluntario();

                    voluntario.setNome(resultSet.getString("nome"));
                    voluntario.setEmail(resultSet.getString("email"));
                    voluntario.setContato(resultSet.getString("contato"));

                    return voluntario;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return null;
    }
}