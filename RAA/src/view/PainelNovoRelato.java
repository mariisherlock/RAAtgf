package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PainelNovoRelato extends JPanel {

    private JTextArea txtRelato;

    private JButton btnPublicar;

    public PainelNovoRelato() {

        setLayout(new BorderLayout());

        setBackground(new Color(75, 40, 130));

        montarTela();

    }

    private void montarTela() {

        JLabel titulo = new JLabel("Sistema de Apoio");

        titulo.setForeground(Color.WHITE);

        titulo.setFont(new Font("Arial", Font.BOLD, 30));

        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        titulo.setBorder(new EmptyBorder(20, 0, 20, 0));

        add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout());

        centro.setOpaque(false);

        centro.setBorder(new EmptyBorder(20, 30, 30, 30));

        txtRelato = new JTextArea();

        txtRelato.setFont(new Font("Arial", Font.PLAIN, 16));

        txtRelato.setLineWrap(true);

        txtRelato.setWrapStyleWord(true);

        txtRelato.setBorder(
                BorderFactory.createTitledBorder("Digite seu relato...")
        );

        JScrollPane scroll = new JScrollPane(txtRelato);

        centro.add(scroll, BorderLayout.CENTER);

        btnPublicar = new JButton("Publicar Relato");

        btnPublicar.setPreferredSize(new Dimension(220, 45));

        JPanel painelBotao = new JPanel();

        painelBotao.setOpaque(false);

        painelBotao.add(btnPublicar);

        centro.add(painelBotao, BorderLayout.SOUTH);

        add(centro, BorderLayout.CENTER);

    }


}
