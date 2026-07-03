package controller;

import dao.CampusDao;
import model.Campus;

import javax.swing.JOptionPane;
import java.util.List;

public class CampusController {

    private final CampusDao campusDao;

    public CampusController() {
        this.campusDao = new CampusDao();
    }

    public void inserirCampus(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O nome do campus não pode estar vazio.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Campus campus = new Campus(nome);
        campusDao.inserir(campus);
    }

    public void atualizarCampus(int id, String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O nome do campus não pode estar vazio.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Campus campus = new Campus(nome);
        campus.setId(id);

        campusDao.atualizar(campus);
    }

    public void deletarCampus(int id) {
        int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este campus?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            campusDao.deletar(id);
        }
    }

    public List<Campus> listarCampi() {
        return campusDao.listarTodos();
    }
}