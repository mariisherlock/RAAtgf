package view.Adm;

import controller.RelatoController;
import model.CategoriaRelato;
import model.Relato;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class PainelTiposdeRelato extends JPanel {

    private RelatoController relatoController = new RelatoController();
    private DefaultTableModel model;
    private JComboBox<String> comboCategorias;
    private List<Relato> todosOsRelatos;

    public PainelTiposdeRelato() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 30, 20, 30));

        Color roxoSistema = new Color(75, 40, 130);


        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel lblTitle = new JLabel("Filtrar por Tipos de Relatos");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        headerPanel.add(lblTitle, BorderLayout.WEST);

        JPanel filtroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtroPanel.setBackground(Color.WHITE);
        filtroPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel lblFiltro = new JLabel("Selecionar Categoria: ");
        lblFiltro.setFont(new Font("Arial", Font.PLAIN, 14));


        comboCategorias = new JComboBox<>();
        comboCategorias.addItem("TODOS");
        for (CategoriaRelato cat : CategoriaRelato.values()) {
            comboCategorias.addItem(cat.name());
        }
        comboCategorias.setPreferredSize(new Dimension(200, 35));


        comboCategorias.addActionListener(e -> filtrarPorCategoria());

        filtroPanel.add(lblFiltro);
        filtroPanel.add(comboCategorias);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(filtroPanel, BorderLayout.SOUTH);


        String[] colunas = {"ID", "Título do Relato", "Categoria", "Local", "Status"};
        model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(245, 245, 250));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);


        carregarDadosBanco();
    }

    private void carregarDadosBanco() {
        todosOsRelatos = relatoController.mostrarRelatos();
        preencherTabela(todosOsRelatos);
    }

    private void preencherTabela(List<Relato> lista) {
        model.setRowCount(0);
        for (Relato r : lista) {
            model.addRow(new Object[]{
                    r.getId(),
                    r.getTitulo(),
                    r.getCategoria().name(),
                    r.getLocal(),
                    r.getStatus().name()
            });
        }
    }


    private void filtrarPorCategoria() {
        String selecionada = (String) comboCategorias.getSelectedItem();

        if (selecionada == null || selecionada.equals("TODOS")) {
            preencherTabela(todosOsRelatos);
        } else {

            List<Relato> filtrados = todosOsRelatos.stream()
                    .filter(r -> r.getCategoria().name().equals(selecionada))
                    .collect(Collectors.toList());
            preencherTabela(filtrados);
        }
    }
}