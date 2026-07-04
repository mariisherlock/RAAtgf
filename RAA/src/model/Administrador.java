package model;

public class Administrador extends Usuario {
    private String tipo;
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
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}