package controller;

import dao.CursoDao;
import model.Campus;
import model.Curso;

import javax.swing.JOptionPane;
import java.util.List;

public class CursoController {

    private final CursoDao cursoDao;

    public CursoController() {
        this.cursoDao = new CursoDao();
    }

    public void inserirCurso(String nome, Campus campusSelecionado) {
        if (nome == null || nome.trim().isEmpty() || campusSelecionado == null) {
            JOptionPane.showMessageDialog(null, "Preencha o nome do curso e selecione um campus válido.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Curso curso = new Curso(nome, campusSelecionado);
        cursoDao.inserir(curso);
    }

    public void atualizarCurso(int id, String nome, Campus campusSelecionado) {
        if (nome == null || nome.trim().isEmpty() || campusSelecionado == null) {
            JOptionPane.showMessageDialog(null, "Preencha o nome do curso e selecione um campus válido.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Curso curso = new Curso(nome, campusSelecionado);
        curso.setId(id);

        cursoDao.atualizar(curso);
    }

    public void deletarCurso(int id) {
        int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este curso?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            cursoDao.deletar(id);
        }
    }

    public List<Curso> listarCursos() {
        return cursoDao.listarTodos();
    }
}