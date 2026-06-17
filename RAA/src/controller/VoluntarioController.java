package controller;

import dao.VoluntarioDao;
import model.Voluntario;

public class VoluntarioController {
    private VoluntarioDao dao = new VoluntarioDao();

    public void cadastrarVoluntario (String nome, String email, String senha, String contato){
        Voluntario voluntario = new Voluntario();
        voluntario.setNome(nome);
        voluntario.setEmail(email);
        voluntario.setSenha(senha);
        voluntario.setContato(contato);

        dao.cadastrarVoluntario(voluntario);
    }

    public Voluntario mostrarInfos(int idUsuario){
        return dao.mostrarInfos(idUsuario);
    }
}
