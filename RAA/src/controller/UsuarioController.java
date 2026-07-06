package controller;

import dao.UsuarioDao;
import model.Cidade;
import model.Curso;
import model.Usuario;

import javax.swing.JOptionPane;
import java.util.List;

public class UsuarioController {

    private final UsuarioDao usuarioDao;

    public UsuarioController() {
        this.usuarioDao = new UsuarioDao();
    }

    public Usuario fazerLogin(String email, String senha) {
        if (email == null || email.trim().isEmpty()
                || senha == null || senha.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Preencha e-mail e senha."
            );
            return null;
        }
        Usuario usuario = usuarioDao.autenticar(email, senha);
        if (usuario == null) {
            JOptionPane.showMessageDialog(
                    null,
                    "E-mail ou senha inválidos."
            );
        }
        return usuario;
    }

    public void inserirUsuario(String nome,
                               String email,
                               String senha,
                               String tipo,
                               String dataNascimento,
                               Cidade cidade,
                               Curso curso) {

        if (nome == null || nome.isEmpty() ||
                email == null || email.isEmpty() ||
                senha == null || senha.isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "Por favor, preencha todos os campos."
            );
            return;
        }

        Usuario usuario = new Usuario(nome, email, senha);
        usuario.setTipo(tipo);

        usuarioDao.inserir(
                usuario,
                dataNascimento,
                cidade,
                curso
        );
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