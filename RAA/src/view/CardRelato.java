package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CardRelato extends JPanel {

    public CardRelato(String autor,
                      String tipo,
                      String relato,
                      String campus,
                      String horario) {

        setLayout(new BorderLayout());

        setBackground(Color.WHITE);

        setBorder(BorderFactory.createCompoundBorder(

                BorderFactory.createLineBorder(new Color(220,220,220)),

                new EmptyBorder(15,15,15,15)

        ));


        JPanel cabecalho = new JPanel();

        cabecalho.setLayout(new BoxLayout(cabecalho, BoxLayout.Y_AXIS));

        cabecalho.setOpaque(false);

        JLabel lblAutor = new JLabel("👤 " + autor);

        lblAutor.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lblTipo = new JLabel("🏷 " + tipo);

        lblTipo.setFont(new Font("Arial", Font.BOLD, 13));

        lblTipo.setForeground(new Color(75,40,130));

        cabecalho.add(lblAutor);

        cabecalho.add(Box.createVerticalStrut(5));

        cabecalho.add(lblTipo);

        add(cabecalho, BorderLayout.NORTH);



        JTextArea area = new JTextArea(relato);

        area.setEditable(false);

        area.setLineWrap(true);

        area.setWrapStyleWord(true);

        area.setBackground(Color.WHITE);

        area.setFont(new Font("Arial", Font.PLAIN, 15));

        area.setBorder(new EmptyBorder(10,0,10,0));

        add(area, BorderLayout.CENTER);


        JPanel rodape = new JPanel(new BorderLayout());

        rodape.setOpaque(false);

        JLabel lblCampus = new JLabel("📍 " + campus);

        JLabel lblHora = new JLabel("🕒 " + horario);

        lblCampus.setForeground(Color.GRAY);

        lblHora.setForeground(Color.GRAY);

        rodape.add(lblCampus, BorderLayout.WEST);

        rodape.add(lblHora, BorderLayout.EAST);

        add(rodape, BorderLayout.SOUTH);

    }

}
