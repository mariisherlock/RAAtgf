package view;

import model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PainelEstatisticas extends JPanel {

    private Usuario usuario;

    private JComboBox<String> cbPeriodo;
    private JComboBox<String> cbCampus;
    private JButton btnAtualizar;

    private JPanel painelGraficos;

    public PainelEstatisticas(Usuario usuario) {

        this.usuario = usuario;

        setLayout(new BorderLayout());

        setBackground(Color.WHITE);

        inicializarComponentes();

        montarTela();

    }

    private void inicializarComponentes(){

        cbPeriodo = new JComboBox<>(new String[]{

                "Últimos 30 dias",
                "Últimos 6 meses",
                "Últimos 12 meses"

        });

        cbCampus = new JComboBox<>(new String[]{

                "Todos"

        });

        btnAtualizar = new JButton("Atualizar");

        painelGraficos = new JPanel();

    }

    private void montarTela(){

        JPanel topo = new JPanel();

        topo.setLayout(new BoxLayout(topo,BoxLayout.Y_AXIS));

        topo.setBackground(Color.WHITE);

        topo.setBorder(new EmptyBorder(25,30,20,30));

        JLabel titulo = new JLabel("Gráficos de Incidência");

        titulo.setFont(new Font("Arial",Font.BOLD,30));

        JLabel subtitulo = new JLabel("Dados e estatísticas sobre relatos na instituição.");

        subtitulo.setFont(new Font("Arial",Font.PLAIN,16));

        subtitulo.setForeground(Color.GRAY);

        topo.add(titulo);

        topo.add(Box.createVerticalStrut(10));

        topo.add(subtitulo);

        topo.add(Box.createVerticalStrut(25));



        JPanel filtros = new JPanel(new GridLayout(1,3,15,0));

        filtros.setOpaque(false);

        filtros.add(cbPeriodo);

        filtros.add(cbCampus);

        filtros.add(btnAtualizar);

        topo.add(filtros);

        add(topo,BorderLayout.NORTH);



        JPanel card1 = criarCard("Relatos por mês");
        card1.add(new CardGraficoLinha(), BorderLayout.CENTER);


        JPanel card2 = criarCard("Relatos por tipo");
        card2.add(new CardGraficoPizza(), BorderLayout.CENTER);


        JPanel card3 = criarCard("Relatos por campus");
        card3.add(new CardGraficoBarras(), BorderLayout.CENTER);


        JPanel card4 = criarCard("Total de relatos");
        card4.add(new CardTotalRelatos(), BorderLayout.CENTER);

        painelGraficos.add(card1);
        painelGraficos.add(card2);
        painelGraficos.add(card3);
        painelGraficos.add(card4);
    }

    private JPanel criarCard(String titulo){

        JPanel card = new JPanel(new BorderLayout());

        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(

                BorderFactory.createLineBorder(new Color(225,225,225),1),

                new EmptyBorder(15,15,15,15)

        ));

        JLabel lblTitulo = new JLabel(titulo);

        lblTitulo.setFont(new Font("Arial",Font.BOLD,20));

        card.add(lblTitulo,BorderLayout.NORTH);

        return card;

    }
}
