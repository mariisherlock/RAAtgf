package view;

import controller.RelatoController;
import model.Relato;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.stream.Collectors;

public class JanelaAdministrador extends JFrame {

    private RelatoController relatoController = new RelatoController();
    private DefaultTableModel model;
    private JTable table;
    private JTextField txtSearch;
    private List<Relato> todosLosRelatos;

    public JanelaAdministrador() {
        setTitle("Dashboard - Proteção e Acolhimento");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Color roxoSistema = new Color(75, 40, 130);


        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(245, 245, 250));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBorder(new EmptyBorder(20, 10, 20, 10));




        JButton btnInicio = new JButton("Tela Inicial", new ImageIcon("src/imagens/1.png"));
        JButton btnRelatos = new JButton("Relatos", new ImageIcon("src/imagens/2.png"));
        JButton btnTipos = new JButton("Tipos de Relatos", new ImageIcon("src/imagens/3.png"));
        JButton btnConfig = new JButton("Configurações", new ImageIcon("src/imagens/4.png"));

        JButton[] botoesMenu = {btnInicio, btnRelatos, btnTipos, btnConfig};

        for (JButton btn : botoesMenu) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(180, 40));
            btn.setBackground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setIconTextGap(10);


            if (btn == btnRelatos) {
                btn.setBackground(new Color(230, 225, 245));
                btn.setForeground(roxoSistema);
                btn.setFont(new Font("Arial", Font.BOLD, 14));
            } else {
                btn.setForeground(Color.DARK_GRAY);
                btn.setFont(new Font("Arial", Font.PLAIN, 14));
            }

            sidebar.add(btn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        }


        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));


        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel lblTitle = new JLabel("Gerenciamento de Relatos Recebidos");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel açõesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        açõesPanel.setBackground(Color.WHITE);


        JButton btnLer = new JButton("Ler Relato Selecionado");
        btnLer.setBackground(Color.WHITE);
        btnLer.setForeground(roxoSistema);
        btnLer.setBorder(BorderFactory.createLineBorder(roxoSistema, 1));
        btnLer.setPreferredSize(new Dimension(180, 35));
        btnLer.setFocusPainted(false);


        JButton btnExcluir = new JButton("Apagar Relato");
        btnExcluir.setBackground(Color.WHITE);
        btnExcluir.setForeground(new Color(210, 50, 50));
        btnExcluir.setBorder(BorderFactory.createLineBorder(new Color(210, 50, 50), 1));
        btnExcluir.setPreferredSize(new Dimension(130, 35));
        btnExcluir.setFocusPainted(false);

        açõesPanel.add(btnLer);
        açõesPanel.add(btnExcluir);

        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(açõesPanel, BorderLayout.EAST);

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        txtSearch = new JTextField("Buscar relato por título...");
        txtSearch.setPreferredSize(new Dimension(300, 35));
        searchPanel.add(txtSearch, BorderLayout.WEST);

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarRelatos(txtSearch.getText());
            }
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.SOUTH);

        String[] colunas = {"ID", "Título do Relato", "Categoria", "Local", "Status"};
        model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(40);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(245, 245, 250));

        atualizarTabelaCompleta();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);



        btnLer.addActionListener(e -> {
            int linhaSelecionada = table.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um relato na tabela primeiro!");
                return;
            }

            int idRelato = (int) table.getValueAt(linhaSelecionada, 0);
            Relato relato = encontrarRelatoPorId(idRelato);

            if (relato != null) {
                String mensagem = "Título: " + relato.getTitulo() + "\n" +
                        "Local: " + relato.getLocal() + "\n" +
                        "Data: " + relato.getData() + "\n" +
                        "Anônimo: " + (relato.isUsuarioAnonimo() ? "Sim" : "Não") + "\n" +
                        "Autor: " + (relato.isUsuarioAnonimo() ? "Anônimo" : relato.getAutor().getNome()) + "\n\n" +
                        "Descrição:\n" + relato.getDescricao();

                JTextArea textArea = new JTextArea(mensagem);
                textArea.setEditable(false);
                JScrollPane scroll = new JScrollPane(textArea);
                scroll.setPreferredSize(new Dimension(400, 300));

                JOptionPane.showMessageDialog(this, scroll, "Visualizar Relato #" + idRelato, JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnExcluir.addActionListener(e -> {
            int linhaSelecionada = table.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um relato para excluir!");
                return;
            }

            int idRelato = (int) table.getValueAt(linhaSelecionada, 0);

            int resposta = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja apagar permanentemente o relato #" + idRelato + "?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                relatoController.excluirRelato(idRelato);
                atualizarTabelaCompleta();
                JOptionPane.showMessageDialog(this, "Relato excluído com sucesso!");
            }
        });
    }

    private void atualizarTabelaCompleta() {
        todosLosRelatos = relatoController.mostrarRelatos();
        preencherTabela(todosLosRelatos);
    }

    private void preencherTabela(List<Relato> lista) {
        model.setRowCount(0);
        for (Relato r : lista) {
            model.addRow(new Object[]{
                    r.getId(),
                    r.getTitulo(),
                    r.getCategoria(),
                    r.getLocal(),
                    r.getStatus().name()
            });
        }
    }

    private void filtrarRelatos(String texto) {
        if (texto.trim().isEmpty() || texto.equals("Buscar relato por título...")) {
            preencherTabela(todosLosRelatos);
        } else {
            List<Relato> filtrados = todosLosRelatos.stream()
                    .filter(r -> r.getTitulo().toLowerCase().contains(texto.toLowerCase()))
                    .collect(Collectors.toList());
            preencherTabela(filtrados);
        }
    }

    private Relato encontrarRelatoPorId(int id) {
        for (Relato r : todosLosRelatos) {
            if (r.getId() == id) return r;
        }
        return null;
    }

}