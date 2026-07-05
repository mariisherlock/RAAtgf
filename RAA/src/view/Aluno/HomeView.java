package view.Aluno;

import model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HomeView extends JFrame {

    private JPanel centroPanel;

    private final Usuario usuario;

    private final Color roxoSistema = new Color(75, 40, 130);

    private JButton btnNovoRelato;
    private JButton btnPerfil;
    private JButton btnMeusRelatos;
    private JButton btnEstatisticas;
    private JButton btnSair;
    private JButton btnChat;

    public HomeView(Usuario usuario){

        this.usuario = usuario;

        setTitle("Sistema de Apoio");

        setSize(1024,768);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        criarMenu();

        criarCentro();

        configurarEventos();

        trocarTela(new PainelNovoRelato(), btnNovoRelato);

    }

    private void criarMenu(){

        JPanel sidebar = new JPanel();

        sidebar.setLayout(new BoxLayout(sidebar,BoxLayout.Y_AXIS));

        sidebar.setBackground(new Color(245,245,250));

        sidebar.setPreferredSize(new Dimension(210,0));

        sidebar.setBorder(new EmptyBorder(20,10,20,10));

        JLabel titulo = new JLabel("RAA");

        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        titulo.setFont(new Font("Arial",Font.BOLD,28));

        titulo.setForeground(roxoSistema);

        sidebar.add(titulo);

        sidebar.add(Box.createRigidArea(new Dimension(0,30)));

        btnNovoRelato = new JButton("Novo Relato");

        btnPerfil = new JButton("Perfil");

        btnMeusRelatos = new JButton("Meus Relatos");

        btnEstatisticas = new JButton("Estatísticas");

        btnSair = new JButton("Sair");

        btnChat = new JButton("Conversar");

        JButton[] botoes = {

                btnNovoRelato,

                btnPerfil,

                btnMeusRelatos,

                btnEstatisticas,

                btnChat,

                btnSair

        };

        for(JButton btn : botoes){

            btn.setAlignmentX(Component.CENTER_ALIGNMENT);

            btn.setMaximumSize(new Dimension(180,40));

            btn.setBackground(Color.WHITE);

            btn.setFocusPainted(false);

            btn.setHorizontalAlignment(SwingConstants.LEFT);

            btn.setForeground(Color.DARK_GRAY);

            btn.setFont(new Font("Arial",Font.PLAIN,14));

            sidebar.add(btn);

            sidebar.add(Box.createRigidArea(new Dimension(0,10)));

        }

        add(sidebar,BorderLayout.WEST);

    }

    private void criarCentro(){

        centroPanel = new JPanel(new BorderLayout());

        add(centroPanel,BorderLayout.CENTER);

    }


    private void trocarTela(JPanel novoPainel, JButton botaoAtivo){

        centroPanel.removeAll();

        centroPanel.add(novoPainel, BorderLayout.CENTER);

        centroPanel.revalidate();

        centroPanel.repaint();

        JButton[] todos = {

                btnNovoRelato,

                btnPerfil,

                btnMeusRelatos,

                btnEstatisticas,

                btnChat,

                btnSair

        };

        for(JButton b : todos){

            b.setBackground(Color.WHITE);

            b.setForeground(Color.DARK_GRAY);

            b.setFont(new Font("Arial",Font.PLAIN,14));

        }

        botaoAtivo.setBackground(new Color(230,225,245));

        botaoAtivo.setForeground(roxoSistema);

        botaoAtivo.setFont(new Font("Arial",Font.BOLD,14));

    }

    private void configurarEventos(){

        btnNovoRelato.addActionListener(e ->
                trocarTela(new PainelNovoRelato(), btnNovoRelato));

        btnPerfil.addActionListener(e ->
                trocarTela(new PainelPerfil(usuario), btnPerfil));

        btnMeusRelatos.addActionListener(e ->
                trocarTela(new PainelMeusRelatos(usuario), btnMeusRelatos));

        btnEstatisticas.addActionListener(e ->
                trocarTela(new PainelEstatisticas(usuario), btnEstatisticas));

        btnChat.addActionListener(e ->
                trocarTela(
                        new PainelChatVoluntario(),
                        btnChat
                )
        );

        btnSair.addActionListener(e -> {

            dispose();

            new LoginView().setVisible(true);

        });

    }


}