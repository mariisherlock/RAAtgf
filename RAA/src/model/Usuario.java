package model;

public class Usuario extends Pessoa {

    private int id;
    private String email;
    private String senha;
    private String tipo;
    private String status;

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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}