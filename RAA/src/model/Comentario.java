package model;

import java.time.LocalDate;

public class Comentario {
    private int       id;
    private String    texto;
    private boolean   anonimo;
    private Aluno     autor;
    private LocalDate data;
    private Relato    relato;

    public Comentario() {}

    public void mostrarComentario() {}

    public int    getId()     {
        return id;
    }
    public void   setId(int id) {
        this.id = id;
    }
    public String getTexto()  {
        return texto;
    }
    public void   setTexto(String texto) {
        this.texto = texto;
    }
    public boolean isAnonimo() {
        return anonimo;
    }
    public void    setAnonimo(boolean anonimo) {
        this.anonimo = anonimo;
    }
    public Aluno   getAutor() {
        return autor;
    }
    public void    setAutor(Aluno autor) {
        this.autor = autor;
    }

    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }

    public Relato getRelato() {
        return relato;
    }
    public void   setRelato(Relato relato) {
        this.relato = relato;
    }
}