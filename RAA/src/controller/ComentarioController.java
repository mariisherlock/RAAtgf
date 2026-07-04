package controller;

import dao.ComentarioDao;
import model.Comentario;
import model.Aluno;
import model.Relato;

import java.time.LocalDate;
import java.util.List;

public class ComentarioController {
    private ComentarioDao dao = new ComentarioDao();
    public void adicionarComentario(String texto, boolean anonimo, int idAutor, int idRelato) {
        Comentario comentario = new Comentario();

        comentario.setTexto(texto);
        comentario.setAnonimo(anonimo);
        comentario.setData(LocalDate.now());

        Aluno autor = new Aluno();
        autor.setId(idAutor);
        comentario.setAutor(autor);

        Relato relato = new Relato();
        relato.setId(idRelato);
        comentario.setRelato(relato);

        dao.adicionarComentario(comentario);
    }

    public List<Comentario> mostrarComentario(int idRelato) {
        return dao.mostrarComentario(idRelato);
    }

    public void atualizarComentario(int idComentario, String novoTexto, boolean anonimo) {
        Comentario comentario = new Comentario();
        comentario.setId(idComentario);
        comentario.setTexto(novoTexto);
        comentario.setAnonimo(anonimo);

        dao.atualizarComentario(comentario);
    }

    public void deletarComentario(int idComentario) {
        dao.deletarComentario(idComentario);
    }
}

