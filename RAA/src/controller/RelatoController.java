package controller;

import dao.RelatoDao;
import model.Relato;
import model.Aluno;
import model.CategoriaRelato;
import model.StatusRelato;

import java.time.LocalDate;
import java.util.List;

public class RelatoController {
    private RelatoDao dao = new RelatoDao();

    public void criarRelato(String titulo, String descricao, String local, boolean anonimo, String categoria, int idAutor) {
        Relato relato = new Relato();
        relato.setTitulo(titulo);
        relato.setDescricao(descricao);
        relato.setLocal(local);

        relato.setData(LocalDate.now());
        relato.setUsuarioAnonimo(anonimo);

        relato.setCategoria(CategoriaRelato.valueOf(categoria.toUpperCase()));
        relato.setStatus(StatusRelato.PENDENTE);

        Aluno autor = new Aluno();
        autor.setId(idAutor);
        relato.setAutor(autor);

        dao.criarRelato(relato);
    }
    public List<Relato> mostrarRelatos() {

        return dao.mostrarRelatos();
    }
    public void atualizarRelato(int id, String titulo, String descricao, String local, boolean anonimo, String categoria, String status) {
        Relato relato = new Relato();
        relato.setId(id);
        relato.setTitulo(titulo);
        relato.setDescricao(descricao);
        relato.setLocal(local);
        relato.setData(LocalDate.now());
        relato.setUsuarioAnonimo(anonimo);
        relato.setCategoria(CategoriaRelato.valueOf(categoria.toUpperCase()));
        relato.setStatus(StatusRelato.valueOf(status.toUpperCase()));

        dao.atualizarRelato(relato);
    }

    public void deletarRelato(int id) {
        dao.deletarRelato(id);
    }
}
