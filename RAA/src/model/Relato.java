package model;

import java.time.LocalDate;

public class Relato {
    private int  id;
    private String titulo;
    private String descricao;
    private String local;
    private LocalDate data;
    private boolean usuarioAnonimo;
    private CategoriaRelato categoria;
    private StatusRelato   status;
    private Aluno autor;

    public Relato() {}

    public void mostrarRelato() {}
    public void adicionarComentario(){}

    public int    getId(){
        return id;
    }
    public void   setId(int id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void   setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescricao() {
        return descricao;
    }
    public void   setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocal() {
        return local;
    }
    public void   setLocal(String local) {
        this.local = local;
    }

    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isUsuarioAnonimo() {
        return usuarioAnonimo;
    }
    public void    setUsuarioAnonimo(boolean usuarioAnonimo) {
        this.usuarioAnonimo = usuarioAnonimo;
    }

    public CategoriaRelato getCategoria() {
        return categoria;
    }
    public void setCategoria(CategoriaRelato categoria) {
        this.categoria = categoria;
    }
    public StatusRelato getStatus() {
        return status;
    }
    public void setStatus(StatusRelato status) {
        this.status = status;
    }
    public Aluno getAutor() {
        return autor;
    }
    public void  setAutor(Aluno autor) {
        this.autor = autor;
    }
}