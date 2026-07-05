package view.Aluno;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PainelNovoRelato extends JPanel {

    private JTextArea txtRelato;

    private JButton btnPublicar;

    private JPanel painelFeed;

    private JComboBox<String> cbTipoRelato;

    public PainelNovoRelato() {

        setLayout(new BorderLayout());

        setBackground(new Color(245, 245, 250));

        criarTopo();

        criarFeed();

        configurarEventos();

    }

    private void criarTopo(){

        JPanel topo = new JPanel();

        topo.setLayout(new BoxLayout(topo,BoxLayout.Y_AXIS));

        topo.setBackground(Color.WHITE);

        topo.setBorder(new EmptyBorder(20,30,20,30));

        JLabel titulo = new JLabel("Sistema de Apoio");

        titulo.setFont(new Font("Arial",Font.BOLD,28));

        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        topo.add(titulo);

        topo.add(Box.createVerticalStrut(20));

        JLabel subtitulo = new JLabel("Compartilhe seu relato");

        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        subtitulo.setFont(new Font("Arial",Font.PLAIN,18));

        topo.add(subtitulo);

        topo.add(Box.createVerticalStrut(20));

        txtRelato = new JTextArea(5,40);

        JLabel lblTipo = new JLabel("Tipo do Relato");

        lblTipo.setFont(new Font("Arial", Font.BOLD, 15));

        topo.add(lblTipo);

        topo.add(Box.createVerticalStrut(8));

        cbTipoRelato = new JComboBox<>(new String[]{

                "ASSÉDIO MORAL",

                "ASSÉDIO SEXUAL",

                "DISCRIMINAÇÃO",

                "OPRESSÃO",

                "OUTRO"

        });

        cbTipoRelato.setMaximumSize(new Dimension(Integer.MAX_VALUE,35));

        topo.add(cbTipoRelato);

        topo.add(Box.createVerticalStrut(20));

        txtRelato.setLineWrap(true);

        txtRelato.setWrapStyleWord(true);

        txtRelato.setFont(new Font("Arial",Font.PLAIN,16));

        JScrollPane scrollTexto = new JScrollPane(txtRelato);

        topo.add(scrollTexto);

        topo.add(Box.createVerticalStrut(15));

        btnPublicar = new JButton("Publicar Relato");

        btnPublicar.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnPublicar.setBackground(new Color(75,40,130));

        btnPublicar.setForeground(Color.WHITE);

        btnPublicar.setFocusPainted(false);

        topo.add(btnPublicar);

        add(topo,BorderLayout.NORTH);

    }

    private void criarFeed(){

        JPanel container = new JPanel(new BorderLayout());

        container.setBackground(new Color(245,245,250));

        JLabel tituloFeed = new JLabel("Relatos Recentes");

        tituloFeed.setFont(new Font("Arial", Font.BOLD, 24));

        tituloFeed.setBorder(new EmptyBorder(20,25,10,0));

        container.add(tituloFeed, BorderLayout.NORTH);

        painelFeed = new JPanel();

        painelFeed.setLayout(new BoxLayout(painelFeed, BoxLayout.Y_AXIS));

        painelFeed.setBackground(new Color(245,245,250));

        painelFeed.setBorder(new EmptyBorder(10,20,20,20));

        adicionarRelatosIniciais();

        JScrollPane scroll = new JScrollPane(painelFeed);

        scroll.setBorder(null);

        scroll.getVerticalScrollBar().setUnitIncrement(16);

        container.add(scroll, BorderLayout.CENTER);

        add(container, BorderLayout.CENTER);

    }

    private void adicionarRelatosIniciais(){

        painelFeed.add(new CardRelato(

                "Usuário Anônimo",

                "ASSÉDIO MORAL",

                "Hoje me senti desconfortável durante uma atividade e gostaria de relatar o ocorrido.",

                "Campus II",

                "Há 2 horas"

        ));

        painelFeed.add(new CardRelato(

                "Usuário Anônimo",

                "DISCRIMINAÇÃO",

                "Presenciei uma situação que considero inadequada e acredito que deve ser registrada.",

                "Campus I",

                "Ontem"

        ));

        painelFeed.add(Box.createVerticalStrut(15));

        painelFeed.add(new CardRelato(

                "Usuário Anônimo",

                "OUTRO",

                "Gostaria de compartilhar uma experiência para que outras pessoas saibam que não estão sozinhas.",

                "Campus III",

                "2 dias atrás"

        ));

    }

    private void configurarEventos(){

        btnPublicar.addActionListener(e -> publicarRelato());

    }

    private void publicarRelato(){

        String texto = txtRelato.getText().trim();

        if(texto.isEmpty()){

            JOptionPane.showMessageDialog(

                    this,

                    "Digite um relato antes de publicar."

            );

            return;

        }

        String tipo = cbTipoRelato.getSelectedItem().toString();

        CardRelato novoRelato = new CardRelato(

                "Você",

                tipo,

                texto,

                "Campus II",

                "Agora"

        );

        painelFeed.add(novoRelato,0);

        painelFeed.add(Box.createVerticalStrut(15),1);

        painelFeed.revalidate();

        painelFeed.repaint();

        txtRelato.setText("");

        JOptionPane.showMessageDialog(

                this,

                "Relato publicado com sucesso!"

        );

    }



}