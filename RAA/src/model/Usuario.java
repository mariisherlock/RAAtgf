package model;

public class Usuario extends Pessoa {

    private String email;
    private String senha;
    private String tipo;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha) {
        super(nome);
        this.email = email;
        this.senha = senha;
        this.tipo = "ALUNO";
    }

    @Override
    public void criarConta() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}