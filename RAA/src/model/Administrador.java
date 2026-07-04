package model;

public class Administrador extends Usuario {

    public Administrador() {}

    public Administrador(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    @Override
    public void criarConta() {}

    public void validarUsuario(){}
    public void cadastrarVoluntario(){}
    public void removerRelato(){}
    public void analisarRelato(){}



}