package app;

import view.JanelaAdministrador;
import javax.swing.SwingUtilities;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            JanelaAdministrador janela = new JanelaAdministrador();


            janela.setVisible(true);
        });

    }
}