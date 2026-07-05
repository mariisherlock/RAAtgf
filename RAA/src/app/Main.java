package app;

import model.Usuario;
import view.Adm.JanelaAdministrador;
import view.Aluno.HomeView;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            JanelaAdministrador janela = new JanelaAdministrador();


            janela.setVisible(true);
        });
        SwingUtilities.invokeLater(() -> {

            Usuario usuario = new Usuario();

            usuario.setNome("Almerindo");

            usuario.setEmail("almerindo@email.com");

            usuario.setTipo("ALUNO");

            new HomeView(usuario).setVisible(true);

        });
    }
}