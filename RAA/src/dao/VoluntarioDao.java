package dao;

import model.Voluntario;
import util.Conexao;
import model.TipoUsuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VoluntarioDao {

    public void cadastrarVoluntario(Voluntario voluntario) {
        voluntario.setTipo(TipoUsuario.VOLUNTARIO);

        String sqlUsuario = "insert into usuario (nome, email, tipo, senha) values (?, ?, ?, ?)";
        String sqlVoluntario = "insert into voluntario (usuario_id, contato) values (?, ?)";

        try (Connection conn = Conexao.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement statementUsuario = conn.prepareStatement(sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS);
                 PreparedStatement statementVoluntario = conn.prepareStatement(sqlVoluntario)
            ) {
                statementUsuario.setString(1, voluntario.getNome());
                statementUsuario.setString(2, voluntario.getEmail());
                statementUsuario.setString(3, voluntario.getTipo());
                statementUsuario.setString(4, voluntario.getSenha());
                statementUsuario.executeUpdate();

                int idUsuarioGerado = 0;

                try (ResultSet generatedKeys = statementUsuario.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idUsuarioGerado = generatedKeys.getInt(1);
                    }
                }

                if (idUsuarioGerado > 0) {
                    statementVoluntario.setInt(1, idUsuarioGerado);
                    statementVoluntario.setString(2, voluntario.getContato());
                    statementVoluntario.executeUpdate();

                    conn.commit();
                    System.out.println("Voluntário cadastrado com sucesso!");
                } else {
                    conn.rollback();
                    System.out.println("Não foi possível obter o ID do usuário.");
                }

            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Erro no processo de inserção: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Erro na conexão: " + e.getMessage());
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
    public void atualizarVoluntario(Voluntario voluntario) {
        String sqlUsuario = "update usuario set nome = ?, email = ? where id = ?";
        String sqlVoluntario = "update voluntario set contato = ? where usuario_id = ?";

        try (Connection conn = Conexao.getConnection()
        ){
            conn.setAutoCommit(false);

            try (PreparedStatement statementUsuario = conn.prepareStatement(sqlUsuario);
                 PreparedStatement statementVoluntario = conn.prepareStatement(sqlVoluntario)
            ){
                statementUsuario.setString(1, voluntario.getNome());
                statementUsuario.setString(2, voluntario.getEmail());
                statementUsuario.setInt(3, voluntario.getId());
                int linhasUsuario = statementUsuario.executeUpdate();

                statementVoluntario.setString(1, voluntario.getContato());
                statementVoluntario.setInt(2, voluntario.getId());
                int linhasVoluntario = statementVoluntario.executeUpdate();

                if (linhasUsuario > 0 && linhasVoluntario > 0) {
                    conn.commit();
                    System.out.println("Dados atualizados com sucesso!");
                } else {
                    conn.rollback();
                    System.out.println("Voluntario não encontrado");
                }
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Erro: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void deletarVoluntario(int idUsuario) {
        String sqlVoluntario = "delete from voluntario where usuario_id = ?";
        String sqlUsuario = "delete from usuario where id = ?";

        try (Connection conn = Conexao.getConnection()
        ){
            conn.setAutoCommit(false);

            try (PreparedStatement statementVoluntario = conn.prepareStatement(sqlVoluntario);
                 PreparedStatement statementUsuario = conn.prepareStatement(sqlUsuario)
            ){
                statementVoluntario.setInt(1, idUsuario);
                statementVoluntario.executeUpdate();

                statementUsuario.setInt(1, idUsuario);
                int linhasAfetadas = statementUsuario.executeUpdate();

                if (linhasAfetadas > 0) {
                    conn.commit();
                    System.out.println("Voluntario removido!");
                } else {
                    conn.rollback();
                    System.out.println("Voluntario não encontrado.");
                }

            } catch (SQLException e) {
                conn.rollback();
            }

        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}