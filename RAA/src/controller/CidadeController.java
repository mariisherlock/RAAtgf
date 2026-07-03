package controller;

import dao.CidadeDao;
import model.Cidade;

import javax.swing.JOptionPane;
import java.util.List;

public class CidadeController {

    private final CidadeDao cidadeDao;

    public CidadeController() {
        this.cidadeDao = new CidadeDao();
    }

    public void inserirCidade(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O nome da cidade não pode estar vazio.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cidade cidade = new Cidade(nome);
        cidadeDao.inserir(cidade);
    }

    public void atualizarCidade(int id, String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O nome da cidade não pode estar vazio.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cidade cidade = new Cidade(id, nome);
        cidadeDao.atualizar(cidade);
    }

    public void deletarCidade(int id) {
        int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir esta cidade?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            cidadeDao.deletar(id);
        }
    }

    public List<Cidade> listarCidades() {
        return cidadeDao.listarTodas();
    }
}