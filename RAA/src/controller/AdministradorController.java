package controller;

import dao.AdministradorDao;
import model.Administrador;

public class AdministradorController {
    private AdministradorDao dao = new AdministradorDao();

    public void cadastrarAdmin(String nome, String email, String senha){
        Administrador adm = new Administrador();
        adm.setNome(nome);
        adm.setEmail(email);
        adm.setSenha(senha);

        dao.cadastrarAdmin(adm);
    }
    public Administrador consultarAdmin(String nome) {
        return dao.consultarAdmin(nome);
    }

    public void atualizarAdmin(int id, String nome, String email) {
        Administrador adm = new Administrador();
        adm.setId(id);
        adm.setNome(nome);
        adm.setEmail(email);

        dao.atualizarAdmin(adm);
    }

    public void deletarAdmin(int idUsuario) {
        dao.deletarAdmin(idUsuario);
    }

    public void validarUsuario(String nome, String novoTipo){
        dao.validarUsuario(nome, novoTipo);
    }

    public void analisarRelato(int idRelato, String novoStatus){
        dao.analisarRelato(idRelato, novoStatus);
    }

    public void removerRelato(int idRelato){
        dao.removerRelato(idRelato);
    }
}
