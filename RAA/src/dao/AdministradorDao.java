package dao;

import model.Administrador;
import util.Conexao;
import model.TipoUsuario;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class AdministradorDao {

    public void cadastrarAdmin(Administrador administrador) {
        administrador.setTipo(TipoUsuario.ADMINISTRADOR.name());

        String sqlUsuario = "insert into usuario (nome, email, tipo, senha) values (?, ?, ?, ?)";
        String sqlAdmin = "insert into administrador (usuario_id) values (?)";

        try (Connection conn = Conexao.getConnection()
        ){
            conn.setAutoCommit(false);
            try (PreparedStatement statementUsuario = conn.prepareStatement(sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS);
                 PreparedStatement statementAdmin = conn.prepareStatement(sqlAdmin)
            ){
                statementUsuario.setString(1, administrador.getNome());
                statementUsuario.setString(2, administrador.getEmail());
                statementUsuario.setString(3, administrador.getTipo());
                statementUsuario.setString(4, administrador.getSenha());
                statementUsuario.executeUpdate();
                int idUsuarioGerado = 0;

                try (ResultSet generatedKeys = statementUsuario.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idUsuarioGerado = generatedKeys.getInt(1);
                    }
                }
                if (idUsuarioGerado > 0) {
                    statementAdmin.setInt(1, idUsuarioGerado);
                    statementAdmin.executeUpdate();
                    conn.commit();

                    System.out.println("Administrador cadastrado com sucesso!");
                } else {
                    conn.rollback();
                    System.out.println("Não foi possível obter o ID do usuário.");
                }
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Erro: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public Administrador consultarAdmin(String nome){
        String sql = "select u.id, u.nome, u.email, u.tipo from usuario u "+
                "inner join administrador a on u.id = a.usuario_id where u.nome = ?";

        Administrador administrador = null;
        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)
        ){
            statement.setString(1, nome);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    administrador = new Administrador();
                    administrador.setId(resultSet.getInt("id"));
                    administrador.setNome(resultSet.getString("nome"));
                    administrador.setEmail(resultSet.getString("email"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return administrador;
    }

    public void atualizarAdmin(Administrador administrador) {

        String sql = "update usuario set nome = ?, email = ? where id= ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)
        ){
            statement.setString(1, administrador.getNome());
            statement.setString(2, administrador.getEmail());

            statement.setInt(3, administrador.getId());

            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Dados atualizados com sucesso!");
            } else {
                System.out.println("Administrador não encontrado");
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void deletarAdmin(int idUsuario) {
        String sqlAdmin = "delete from administrador where usuario_id = ?";
        String sqlUsuario = "delete from usuario where id = ?";

        try (Connection conn = Conexao.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement statementAdmin = conn.prepareStatement(sqlAdmin);
                 PreparedStatement statementUsuario = conn.prepareStatement(sqlUsuario)
            ){
                statementAdmin.setInt(1, idUsuario);
                statementAdmin.executeUpdate();

                statementUsuario.setInt(1, idUsuario);
                int linhasAfetadas = statementUsuario.executeUpdate();

                if (linhasAfetadas > 0) {
                    conn.commit();
                    System.out.println("Administrador removido!");
                } else {
                    conn.rollback();
                    System.out.println("Administrador não encontrado.");
                }

            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());

                try {
                    conn.rollback();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void validarUsuario(String nome, String novoTipo){
        String sql = "update usuario set tipo = ? where nome = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
        ){
            statement.setString(1, novoTipo);
            statement.setString(2, nome);
            statement.executeUpdate();

            System.out.println("Usuario validado com sucesso!");

        } catch (SQLException e){
            System.out.println("Erro: "+e.getMessage());
        }
    }
    public void analisarRelato(int id, String novoStatus){
        String sql = "update relato set status = ? where id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
        ){
            statement.setString(1, novoStatus);
            statement.setInt(2, id);
            statement.executeUpdate();

            System.out.println("Status do relato atualizado para: "+novoStatus);

        } catch (SQLException e){
            System.out.println("Erro: "+e.getMessage());
        }
    }
    public void removerRelato(int id){
        String sql = "delete from relato where id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
        ){
            statement.setInt(1,id);
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Erro: "+ e.getMessage());
        }
    }

}
