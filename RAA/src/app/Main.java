package app;

import model.Usuario;
import view.Adm.JanelaAdministrador;
import view.HomeView;

import javax.swing.SwingUtilities;
import java.sql.Connection;

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