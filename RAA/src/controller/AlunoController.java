package controller;

import dao.AlunoDao;
import model.Aluno;
import model.Cidade;
import model.Curso;

import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AlunoController {

    private final AlunoDao alunoDao;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public AlunoController() {
        this.alunoDao = new AlunoDao();
    }

    public void inserirAluno(String nome, String email, String senha, String dataNascStr, Cidade cidade, Curso curso) {
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || dataNascStr.isEmpty() || cidade == null || curso == null) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos obrigatórios.");
            return;
        }

        try {
            LocalDate dataNascimento = LocalDate.parse(dataNascStr, formatter);
            Aluno aluno = new Aluno(nome, email, senha, dataNascimento, cidade, curso);

            alunoDao.inserir(aluno);

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Data de nascimento inválida! Use o formato DD/MM/AAAA.");
        }
    }

    public void deletarAluno(int idUsuario) {
        int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este aluno?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            alunoDao.deletar(idUsuario);
        }
    }

    public void atualizarAluno(int id, String nome, String email, String senha, String dataNascStr, Cidade cidade, Curso curso) {
        try {
            LocalDate dataNascimento = LocalDate.parse(dataNascStr, formatter);
            Aluno aluno = new Aluno(nome, email, senha, dataNascimento, cidade, curso);
            aluno.setId(id);

            alunoDao.atualizar(aluno);

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Data de nascimento inválida! Use o formato DD/MM/AAAA.");
        }
    }

    public List<Aluno> listarAlunos() {
        return alunoDao.listarTodos();
    }
}