package model;

public class Cidade {
    private int id;
    private String nome;

    public Cidade() {}

    public Cidade(String nome) {
        this.nome = nome;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }


    public Cidade(int id, String nome) {
        this.id = id;
        this.nome = nome; // substitua pelo nome do seu atributo de texto se for diferente
    }
}