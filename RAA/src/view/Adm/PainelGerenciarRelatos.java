package view.Adm;

import controller.RelatoController;
import model.GerenciadorRelatos;
import model.Relato;
import model.StatusRelato;

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


        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        GridBagConstraints gbc = new GridBagConstraints();


        JLabel lblTitle = new JLabel("Gerenciamento de Relatos Recebidos");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 15, 0);
        topPanel.add(lblTitle, gbc);


        JPanel moderaçãoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        moderaçãoPanel.setBackground(Color.WHITE);

        JButton btnAprovar = new JButton("Aprovar Relato");
        btnAprovar.setBackground(Color.WHITE);
        btnAprovar.setForeground(new Color(40, 167, 69));
        btnAprovar.setBorder(BorderFactory.createLineBorder(new Color(40, 167, 69), 1));
        btnAprovar.setPreferredSize(new Dimension(130, 35));
        btnAprovar.setFocusPainted(false);

        JButton btnExcluir = new JButton("Apagar Relato");
        btnExcluir.setBackground(Color.WHITE);
        btnExcluir.setForeground(new Color(210, 50, 50));
        btnExcluir.setBorder(BorderFactory.createLineBorder(new Color(210, 50, 50), 1));
        btnExcluir.setPreferredSize(new Dimension(120, 35));
        btnExcluir.setFocusPainted(false);

        moderaçãoPanel.add(btnAprovar);
        moderaçãoPanel.add(btnExcluir);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;
        topPanel.add(moderaçãoPanel, gbc);


        JPanel linhaInferiorEsquerda = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        linhaInferiorEsquerda.setBackground(Color.WHITE);

        txtSearch = new JTextField("Buscar relato por título...");
        txtSearch.setPreferredSize(new Dimension(300, 35));

        txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtSearch.getText().equals("Buscar relato por título...")) txtSearch.setText("");
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtSearch.getText().isEmpty()) txtSearch.setText("Buscar relato por título...");
            }
        });

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) { filtrarRelatos(txtSearch.getText()); }
        });


        JButton btnLer = new JButton("Ler Relato Selecionado");
        btnLer.setBackground(Color.WHITE);
        btnLer.setForeground(roxoSistema);
        btnLer.setBorder(BorderFactory.createLineBorder(roxoSistema, 1));
        btnLer.setPreferredSize(new Dimension(170, 35));
        btnLer.setFocusPainted(false);

        linhaInferiorEsquerda.add(txtSearch);
        linhaInferiorEsquerda.add(btnLer);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 0);
        topPanel.add(linhaInferiorEsquerda, gbc);


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

        btnAprovar.addActionListener(e -> {
            int líneaSelecionada = table.getSelectedRow();
            if (líneaSelecionada == -1) {
                JOptionPane.showMessageDialog(pai, "Selecione um relato para aprovar!");
                return;
            }
            int idRelato = (int) table.getValueAt(líneaSelecionada, 0);
            int resposta = JOptionPane.showConfirmDialog(pai, "Deseja aprovar o relato #" + idRelato + "?", "Aprovar Relato", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                Relato relato = encontrarRelatoPorId(idRelato);

                if(relato != null){

                    relato.setStatus(StatusRelato.APROVADO);

                }
                atualizarTabelaCompleta();
                JOptionPane.showMessageDialog(pai, "Relato aprovado com sucesso!");
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

        todosOsRelatos = GerenciadorRelatos.relatos;

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