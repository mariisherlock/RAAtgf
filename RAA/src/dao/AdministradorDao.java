package dao;

import model.Administrador;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdministradorDao {
    private UsuarioDao usuarioDao = new UsuarioDao();

    public void cadastrarAdmin(Administrador administrador) {

        administrador.setTipo("ADMINISTRADOR");

        int idUsuario = usuarioDao.inserir(administrador);

        if (idUsuario > 0){
            String sql = "insert into administrador (usuario_id) values (?)";

            try (Connection conn = Conexao.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql);
            ){
                statement.setInt(1, idUsuario);
                statement.executeUpdate();

                System.out.println("Administrador cadastrado com sucesso!");

            } catch (SQLException e){
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Nao foi possivel cadastrar o administrador");
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

