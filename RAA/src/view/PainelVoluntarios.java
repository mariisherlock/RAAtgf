package view;

import controller.UsuarioController;
import dao.UsuarioDao;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PainelVoluntarios extends JPanel {

    private DefaultTableModel modelTodos, modelPendentes;
    private JTable tableTodos, tablePendentes;
    private Color roxoSistema = new Color(75, 40, 130);

    public PainelVoluntarios(Component pai) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 30, 20, 30));


        JLabel lblTitle = new JLabel("Gerenciamento de Voluntários");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(lblTitle, BorderLayout.NORTH);


        JTabbedPane abas = new JTabbedPane();
        abas.setFont(new Font("Arial", Font.BOLD, 14));


        JPanel abaTodos = criarAbaTodos();
        abas.addTab("Todos os Voluntários", abaTodos);


        JPanel abaPendentes = criarAbaPendentes(pai);
        abas.addTab("Aguardando Aprovação", abaPendentes);

        add(abas, BorderLayout.CENTER);
    }


    private JPanel criarAbaTodos() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Color.WHITE);

        String[] colunas = {"ID", "Nome do Voluntário", "E-mail", "Área de Atuação", "Status"};
        modelTodos = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tableTodos = new JTable(modelTodos);
        tableTodos.setRowHeight(35);


        painel.add(new JScrollPane(tableTodos), BorderLayout.CENTER);
        return painel;
    }


    private JPanel criarAbaPendentes(Component pai) {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Color.WHITE);
        UsuarioController conn = new UsuarioController();

        String[] colunas = {"ID", "Nome do Candidato", "E-mail", "Área/Especialidade"};
        modelPendentes = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tablePendentes = new JTable(modelPendentes);
        tablePendentes.setRowHeight(35);


        JPanel botoesAcao = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        botoesAcao.setBackground(Color.WHITE);

        JButton btnAprovar = new JButton("Aprovar Voluntário");
        btnAprovar.setFont(new Font("Arial", Font.BOLD, 13));
        btnAprovar.setBackground(new Color(40, 167, 69));
        btnAprovar.setForeground(Color.WHITE);
        btnAprovar.setPreferredSize(new Dimension(170, 38));
        btnAprovar.setFocusPainted(false);

        JButton btnReprovar = new JButton("Reprovar Inscrição");
        btnReprovar.setFont(new Font("Arial", Font.BOLD, 13));
        btnReprovar.setBackground(new Color(220, 53, 69));
        btnReprovar.setForeground(Color.WHITE);
        btnReprovar.setPreferredSize(new Dimension(170, 38));
        btnReprovar.setFocusPainted(false);

        botoesAcao.add(btnReprovar);
        botoesAcao.add(btnAprovar);

        painel.add(new JScrollPane(tablePendentes), BorderLayout.CENTER);
        painel.add(botoesAcao, BorderLayout.SOUTH);



        btnAprovar.addActionListener(e -> {
            int linhaSelecionada = tablePendentes.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(pai, "Por favor, selecione um voluntário na tabela para aprovar!");
                return;
            }


            String email = (String) tablePendentes.getValueAt(linhaSelecionada, 1);

            int resposta = JOptionPane.showConfirmDialog(pai, "Deseja aprovar a entrada do voluntário " + email + "?", "Aprovar Voluntário", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {


                //add controler aq


                ((DefaultTableModel) tablePendentes.getModel()).removeRow(linhaSelecionada);

                JOptionPane.showMessageDialog(pai, "Voluntário aprovado com sucesso!");
            }
        });


        btnReprovar.addActionListener(e -> {
            int linhaSelecionada = tablePendentes.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(pai, "Por favor, selecione um voluntário na tabela para reprovar!");
                return;
            }


            int idVoluntario = (int) tablePendentes.getValueAt(linhaSelecionada, 0);

            int resposta = JOptionPane.showConfirmDialog(pai, "Tem certeza que deseja reprovar e remover esta inscrição?", "Reprovar Voluntário", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {


                //add dps conn.deletarUsuario(idVoluntario);


                ((DefaultTableModel) tablePendentes.getModel()).removeRow(linhaSelecionada);

                JOptionPane.showMessageDialog(pai, "Inscrição rejeitada e removida da lista.");
            }
        });
        return painel;
    }
}