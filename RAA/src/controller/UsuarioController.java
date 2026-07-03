package controller;

import dao.UsuarioDao;
import model.Usuario;

import javax.swing.JOptionPane;
import java.util.List;

public class UsuarioController {

    private final UsuarioDao usuarioDao;

    public UsuarioController() {
        this.usuarioDao = new UsuarioDao();
    }

    public boolean fazerLogin(String email, String senha) {
        if (email == null || email.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha o e-mail e a senha.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        Usuario usuarioLogado = usuarioDao.autenticar(email, senha);

        if (usuarioLogado != null) {
            JOptionPane.showMessageDialog(null, "Bem-vindo(a), " + usuarioLogado.getNome() + "!");
            return true; // A tela Swing recebe true e libera o acesso ao sistema
        } else {
            JOptionPane.showMessageDialog(null, "E-mail ou senha incorretos.", "Falha no Login", JOptionPane.ERROR_MESSAGE);
            return false; // A tela Swing recebe false e não deixa entrar
        }
    }

    public void inserirUsuario(String nome, String email, String senha) {
        if (nome == null || nome.isEmpty() || email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos (Nome, E-mail e Senha).", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario usuario = new Usuario(nome, email, senha);
        usuarioDao.inserir(usuario);
    }

    public void atualizarUsuario(int id, String nome, String email, String senha) {
        if (nome == null || nome.isEmpty() || email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario usuario = new Usuario(nome, email, senha);
        usuario.setId(id);

        usuarioDao.atualizar(usuario);
    }

    public void deletarUsuario(int id) {
        int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este usuário?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            usuarioDao.deletar(id);
        }
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDao.listarTodos();
    }
}