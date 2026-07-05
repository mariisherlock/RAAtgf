package view.Aluno;

import model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PainelMeusRelatos extends JPanel {

    private final Usuario usuario;

    public PainelMeusRelatos(Usuario usuario) {

        this.usuario = usuario;

        setLayout(new BorderLayout());

        setBackground(new Color(75,40,130));

        montarTela();

    }

    private void montarTela() {

        JLabel titulo = new JLabel("Meus Relatos");

        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        titulo.setForeground(Color.WHITE);

        titulo.setFont(new Font("Arial", Font.BOLD, 30));

        titulo.setBorder(new EmptyBorder(20,0,20,0));

        add(titulo, BorderLayout.NORTH);


        JTextArea area = new JTextArea();

        area.setEditable(false);

        area.setFont(new Font("Arial", Font.PLAIN,16));

        area.setText(
                "Nenhum relato encontrado.\n\n" +
                        "Quando conectarmos ao banco,\n" +
                        "seus relatos aparecerão aqui."
        );

        JScrollPane scroll = new JScrollPane(area);

        scroll.setBorder(new EmptyBorder(20,30,30,30));

        add(scroll, BorderLayout.CENTER);

    }

}
