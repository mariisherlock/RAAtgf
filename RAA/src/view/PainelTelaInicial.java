package view;

import controller.RelatoController;
import model.Relato;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class PainelTelaInicial extends JPanel {

    private RelatoController relatoController = new RelatoController();
    private JPanel feedContainer;
    private JScrollPane scrollPane;
    private Component janelaPai;

    public PainelTelaInicial(Component pai) {
        this.janelaPai = pai;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 30, 20, 30));


        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel lblTitle = new JLabel("Feed de Relatos Recentes");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        headerPanel.add(lblTitle, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);


        feedContainer = new JPanel();
        feedContainer.setBackground(new Color(245, 245, 250));
        feedContainer.setLayout(new BoxLayout(feedContainer, BoxLayout.Y_AXIS));
        feedContainer.setBorder(new EmptyBorder(15, 15, 15, 15));


        scrollPane = new JScrollPane(feedContainer);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);


        carregarFeed();
    }

    public void carregarFeed() {
        feedContainer.removeAll();

        List<Relato> relatos = relatoController.mostrarRelatos();

        if (relatos.isEmpty()) {
            JLabel lblVazio = new JLabel("Nenhum relato publicado no momento.");
            lblVazio.setFont(new Font("Arial", Font.ITALIC, 16));
            lblVazio.setAlignmentX(Component.CENTER_ALIGNMENT);
            feedContainer.add(lblVazio);
        } else {

            for (Relato relato : relatos) {
                JPanel card = criarCardRelato(relato);
                feedContainer.add(card);
                feedContainer.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }

        feedContainer.revalidate();
        feedContainer.repaint();
    }


    private JPanel criarCardRelato(Relato relato) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 230), 1, true), // Borda arredondada suave
                new EmptyBorder(15, 20, 15, 20) // Espaçamento interno
        ));
        card.setMaximumSize(new Dimension(800, 220)); // Limita a largura do post no meio da tela
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        Color roxoSistema = new Color(75, 40, 130);


        JPanel topoCard = new JPanel(new BorderLayout());
        topoCard.setBackground(Color.WHITE);

        String nomeAutor = relato.isUsuarioAnonimo() ? "Usuário Anônimo" : relato.getAutor().getNome();
        JLabel lblAutor = new JLabel(nomeAutor);
        lblAutor.setFont(new Font("Arial", Font.BOLD, 14));
        lblAutor.setForeground(roxoSistema);

        JLabel lblCategoria = new JLabel(relato.getCategoria().name());
        lblCategoria.setFont(new Font("Arial", Font.BOLD, 11));
        lblCategoria.setForeground(Color.GRAY);

        topoCard.add(lblAutor, BorderLayout.WEST);
        topoCard.add(lblCategoria, BorderLayout.EAST);


        JPanel corpoCard = new JPanel(new BorderLayout());
        corpoCard.setBackground(Color.WHITE);
        corpoCard.setBorder(new EmptyBorder(10, 0, 10, 0));

        JLabel lblTitulo = new JLabel(relato.getTitulo());
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        JTextArea txtDescricao = new JTextArea(relato.getDescricao());
        txtDescricao.setFont(new Font("Arial", Font.PLAIN, 13));
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        txtDescricao.setEditable(false);
        txtDescricao.setBackground(Color.WHITE);
        txtDescricao.setBorder(new EmptyBorder(5, 0, 0, 0));

        corpoCard.add(lblTitulo, BorderLayout.NORTH);
        corpoCard.add(txtDescricao, BorderLayout.CENTER);

        JPanel rodapeCard = new JPanel(new BorderLayout());
        rodapeCard.setBackground(Color.WHITE);

        JLabel lblInfo = new JLabel("Local: " + relato.getLocal() + " | Data: " + relato.getData());
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 11));
        lblInfo.setForeground(Color.LIGHT_GRAY);


        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        botoesPanel.setBackground(Color.WHITE);

        JButton btnVerComentarios = new JButton("Ver Comentários");
        btnVerComentarios.setBackground(Color.WHITE);
        btnVerComentarios.setForeground(roxoSistema);
        btnVerComentarios.setBorder(BorderFactory.createLineBorder(roxoSistema, 1));
        btnVerComentarios.setFocusPainted(false);

        JButton btnApagarRelato = new JButton("Apagar Relato");
        btnApagarRelato.setBackground(Color.WHITE);
        btnApagarRelato.setForeground(new Color(210, 50, 50));
        btnApagarRelato.setBorder(BorderFactory.createLineBorder(new Color(210, 50, 50), 1));
        btnApagarRelato.setFocusPainted(false);

        botoesPanel.add(btnVerComentarios);
        botoesPanel.add(btnApagarRelato);

        rodapeCard.add(lblInfo, BorderLayout.WEST);
        rodapeCard.add(botoesPanel, BorderLayout.EAST);


        card.add(topoCard, BorderLayout.NORTH);
        card.add(corpoCard, BorderLayout.CENTER);
        card.add(rodapeCard, BorderLayout.SOUTH);




        btnApagarRelato.addActionListener(e -> {
            int resposta = JOptionPane.showConfirmDialog(janelaPai, "Deseja mesmo apagar este relato permanentemente do sistema?", "Moderação de Relato", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                relatoController.deletarRelato(relato.getId());
                carregarFeed();
                JOptionPane.showMessageDialog(janelaPai, "Relato removido com sucesso!");
            }
        });


        btnVerComentarios.addActionListener(e -> {

            JOptionPane.showMessageDialog(janelaPai, "Abrindo área de moderação de comentários para o Relato #" + relato.getId() + ".\n(Aqui listaremos os comentários com botão de apagar).");
        });

        return card;
    }
}