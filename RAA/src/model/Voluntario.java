package model;

public class Voluntario extends Usuario {
    private String contato;

    public Voluntario() {}

    public Voluntario(String nome, String email, String senha, String contato) {
        super(nome, email, senha);
        this.contato = contato;
    }

    @Override
    public void criarConta(){}

    public void mostrarContato(){}
    public void mostrarInformacoes(){}

    public String getContato() {
        return contato;
    }
    public void   setContato(String contato) {
        this.contato = contato;
    }

    public void setTipo(TipoUsuario voluntario) {
    }
}