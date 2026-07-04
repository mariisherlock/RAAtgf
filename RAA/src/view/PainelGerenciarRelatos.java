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

public class PainelGerenciarRelatos extends JPanel {

    private RelatoController relatoController = new RelatoController();
    private DefaultTableModel model;
    private JTable table;
    private JTextField txtSearch;
    private List<Relato> todosOsRelatos;

    public PainelGerenciarRelatos(Component pai) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 30, 20, 30));

        Color roxoSistema = new Color(75, 40, 130);


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
            public void keyReleased(KeyEvent e) { filtrarRelatos(txtSearch.getText()); }
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.SOUTH);


        String[] colunas = {"ID", "Título do Relato", "Categoria", "Local", "Status"};
        model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(40);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(245, 245, 250));

        atualizarTabelaCompleta();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);


        btnLer.addActionListener(e -> {
            int linhaSelecionada = table.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(pai, "Selecione um relato na tabela primeiro!");
                return;
            }
            int idRelato = (int) table.getValueAt(linhaSelecionada, 0);
            Relato relato = encontrarRelatoPorId(idRelato);
            if (relato != null) {
                String mensagem = "Título: " + relato.getTitulo() + "\nLocal: " + relato.getLocal() + "\nDescrição:\n" + relato.getDescricao();
                JOptionPane.showMessageDialog(pai, new JScrollPane(new JTextArea(mensagem)), "Visualizar Relato #" + idRelato, JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnExcluir.addActionListener(e -> {
            int linhaSelecionada = table.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(pai, "Selecione um relato para excluir!");
                return;
            }
            int idRelato = (int) table.getValueAt(linhaSelecionada, 0);
            int resposta = JOptionPane.showConfirmDialog(pai, "Apagar permanentemente o relato #" + idRelato + "?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                relatoController.deletarRelato(idRelato);
                atualizarTabelaCompleta();
                JOptionPane.showMessageDialog(pai, "Relato excluído com sucesso!");
            }
        });
    }

    private void atualizarTabelaCompleta() {
        todosOsRelatos = relatoController.mostrarRelatos();
        preencherTabela(todosOsRelatos);
    }

    private void preencherTabela(List<Relato> lista) {
        model.setRowCount(0);
        for (Relato r : lista) {
            model.addRow(new Object[]{r.getId(), r.getTitulo(), r.getCategoria().name(), r.getLocal(), r.getStatus().name()});
        }
    }

    private void filtrarRelatos(String texto) {
        if (texto.trim().isEmpty() || texto.equals("Buscar relato por título...")) {
            preencherTabela(todosOsRelatos);
        } else {
            List<Relato> filtrados = todosOsRelatos.stream().filter(r -> r.getTitulo().toLowerCase().contains(texto.toLowerCase())).collect(Collectors.toList());
            preencherTabela(filtrados);
        }
    }

    private Relato encontrarRelatoPorId(int id) {
        for (Relato r : todosOsRelatos) { if (r.getId() == id) return r; }
        return null;
    }
}