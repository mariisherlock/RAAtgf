package model;

public class Curso {
    private int    id;
    private String nome;
    private Campus campus;

    public Curso() {}

    public Curso(String nome, Campus campus) {
        this.nome   = nome;
        this.campus = campus;
    }

    public int    getId()     {
        return id;
    }
    public void   setId(int id) {
        this.id = id;
    }
    public String getNome()   {
        return nome;
    }
    public void   setNome(String nome) {
        this.nome = nome;
    }
    public Campus getCampus() {
        return campus;
    }
    public void   setCampus(Campus campus) {
        this.campus = campus;
    }

    @Override
    public String toString() {
        return nome + " - " + (campus != null ? campus.getNome() : "");
    }


    public Curso(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}