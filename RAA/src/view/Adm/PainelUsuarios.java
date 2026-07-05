package view.Adm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PainelUsuarios extends JPanel {

    private DefaultTableModel modelTodos, modelPendentes;
    private JTable tableTodos, tablePendentes;
    private Color roxoSistema = new Color(75, 40, 130);

    public PainelUsuarios(Component pai) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel lblTitle = new JLabel("Gestão de Usuários do Sistema");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(lblTitle, BorderLayout.NORTH);


        JTabbedPane abas = new JTabbedPane();
        abas.setFont(new Font("Arial", Font.BOLD, 14));


        JPanel abaTodos = criarAbaTodos();
        abas.addTab("Todos os Usuários", abaTodos);


        JPanel abaPendentes = criarAbaPendentes(pai);
        abas.addTab("Aguardando Aprovação", abaPendentes);

        add(abas, BorderLayout.CENTER);
    }

    private JPanel criarAbaTodos() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Color.WHITE);

        String[] colunas = {"ID", "Email", "Tipo", "Status"};
        modelTodos = new DefaultTableModel(colunas, 0);
        tableTodos = new JTable(modelTodos);
        tableTodos.setRowHeight(35);

        painel.add(new JScrollPane(tableTodos), BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarAbaPendentes(Component pai) {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Color.WHITE);

        String[] colunas = {"ID", "Email", "Tipo"};
        modelPendentes = new DefaultTableModel(colunas, 0);
        tablePendentes = new JTable(modelPendentes);
        tablePendentes.setRowHeight(35);


        JPanel botoesAcao = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoesAcao.setBackground(Color.WHITE);

        JButton btnAprovar = new JButton("Aprovar Selecionado");
        btnAprovar.setBackground(new Color(40, 167, 69)); // Verde
        btnAprovar.setForeground(Color.WHITE);

        JButton btnReprovar = new JButton("Reprovar Selecionado");
        btnReprovar.setBackground(new Color(220, 53, 69)); // Vermelho
        btnReprovar.setForeground(Color.WHITE);

        botoesAcao.add(btnAprovar);
        botoesAcao.add(btnReprovar);

        painel.add(new JScrollPane(tablePendentes), BorderLayout.CENTER);
        painel.add(botoesAcao, BorderLayout.SOUTH);


        btnAprovar.addActionListener(e -> {
            int row = tablePendentes.getSelectedRow();
            if (row != -1) {
                JOptionPane.showMessageDialog(pai, "Usuário aprovado com sucesso!");

            }
        });

        return painel;
    }
}