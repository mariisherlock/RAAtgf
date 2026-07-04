package dao;

import model.Comentario;
import model.Aluno;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDao {

    public void adicionarComentario(Comentario comentario){
        String sql = "insert into comentario (texto, anonimo, data, autor_id, relato_id) values (?,?,?,?,?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement= conn.prepareStatement(sql)
        ){
            statement.setString(1,comentario.getTexto());
            statement.setBoolean(2,comentario.isAnonimo());
            statement.setDate(3, java.sql.Date.valueOf(comentario.getData()));
            statement.setInt(4,comentario.getAutor().getId());
            statement.setInt(5, comentario.getRelato().getId());
            statement.executeUpdate();

            System.out.println("Comentario adicionado com sucesso!");
        } catch (SQLException e){
            System.out.println("Erro: "+e.getMessage());
        }
    }
    public List<Comentario> mostrarComentario(int idRelato){
        List<Comentario> comentarios = new ArrayList<>();

        String sql = "select c.id, c.texto, c.anonimo, c.data, c.autor_id, u.nome as nome_autor from comentario c "+
                "inner join usuario u on c.autor_id = u.id where c.relato_id = ? order by c.data desc";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)
        ){
            statement.setInt(1, idRelato);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Comentario coment = new Comentario();

                    coment.setId(resultSet.getInt("id"));
                    coment.setTexto(resultSet.getString("texto"));
                    coment.setAnonimo(resultSet.getBoolean("anonimo"));
                    coment.setData(resultSet.getDate("data").toLocalDate());

                    Aluno autor = new Aluno();
                    autor.setId(resultSet.getInt("autor_id"));
                    autor.setNome(resultSet.getString("nome_autor"));
                    coment.setAutor(autor);

                    comentarios.add(coment);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return comentarios;
    }

    public void atualizarComentario(Comentario comentario) {
        String sql = "update comentario set texto = ?, anonimo = ? where id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)
        ){
            statement.setString(1, comentario.getTexto());
            statement.setBoolean(2, comentario.isAnonimo());
            statement.setInt(3, comentario.getId());
            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Comentário atualizado!");
            } else {
                System.out.println("Comentário não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void deletarComentario(int idComentario) {
        String sql = "delete from comentario where id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)
        ){
            statement.setInt(1, idComentario);
            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Comentário removido com sucesso!");
            } else {
                System.out.println("Comentário não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}