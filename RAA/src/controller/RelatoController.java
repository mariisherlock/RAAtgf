package controller;

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
}
