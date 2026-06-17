package model;

import java.time.LocalDate;

public class Aluno extends Usuario {
    private LocalDate dataNascimento;
    private Cidade    cidade;
    private Curso     curso;

    public Aluno() {}

    public Aluno(String nome, String email, String senha, LocalDate dataNascimento, Cidade cidade, Curso curso) {
        super(nome, email, senha);
        this.dataNascimento = dataNascimento;
        this.cidade = cidade;
        this.curso  = curso;
    }

    @Override
    public void criarConta(){}
    public void publicarRelato(){}
    public void comentar(){}

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public Cidade getCidade() {
        return cidade;
    }
    public void   setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
    public Curso  getCurso()  {
        return curso;
    }
    public void   setCurso(Curso curso) {
        this.curso = curso;
    }
}