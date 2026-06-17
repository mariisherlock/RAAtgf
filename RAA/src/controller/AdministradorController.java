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
