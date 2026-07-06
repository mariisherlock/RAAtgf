package view.Aluno;

import controller.RelatoController;
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;

public class PainelNovoRelato extends JPanel {

    private Usuario usuario;

    private JTextArea txtRelato;

    private JTextField txtTitulo;
    private JTextField txtLocal;

    private JCheckBox chkAnonimo;

    private RelatoController controller = new RelatoController();

    private JButton btnPublicar;

    private JPanel painelFeed;

    private JComboBox<String> cbTipoRelato;

    public PainelNovoRelato(Usuario usuario) {

        this.usuario = usuario;

        setLayout(new BorderLayout());

        setBackground(new Color(245,245,250));

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

        JLabel lblTitulo = new JLabel("Título");

        lblTitulo.setFont(new Font("Arial", Font.BOLD, 15));

        topo.add(lblTitulo);

        topo.add(Box.createVerticalStrut(8));

        txtTitulo = new JTextField();

        txtTitulo.setMaximumSize(new Dimension(Integer.MAX_VALUE,35));

        topo.add(txtTitulo);

        topo.add(Box.createVerticalStrut(15));

        JLabel lblDescricao = new JLabel("Descrição");

        lblDescricao.setFont(new Font("Arial", Font.BOLD, 15));

        topo.add(lblDescricao);

        topo.add(Box.createVerticalStrut(8));

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

        JLabel lblLocal = new JLabel("Local");

        chkAnonimo = new JCheckBox("Publicar anonimamente");

        chkAnonimo.setBackground(Color.WHITE);

        topo.add(chkAnonimo);

        topo.add(Box.createVerticalStrut(15));

        lblLocal.setFont(new Font("Arial", Font.BOLD, 15));

        topo.add(lblLocal);

        topo.add(Box.createVerticalStrut(8));

        txtLocal = new JTextField();

        txtLocal.setMaximumSize(new Dimension(Integer.MAX_VALUE,35));

        topo.add(txtLocal);

        topo.add(Box.createVerticalStrut(15));

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

        carregarFeed();

        JScrollPane scroll = new JScrollPane(painelFeed);

        scroll.setBorder(null);

        scroll.getVerticalScrollBar().setUnitIncrement(16);

        container.add(scroll, BorderLayout.CENTER);

        add(container, BorderLayout.CENTER);

    }



    private void configurarEventos(){

        btnPublicar.addActionListener(e -> publicarRelato());

    }

    private void publicarRelato() {

        String titulo = txtTitulo.getText().trim();
        String descricao = txtRelato.getText().trim();
        String local = txtLocal.getText().trim();

        if (titulo.isEmpty() || descricao.isEmpty() || local.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha todos os campos."
            );

            return;
        }

        String categoria = cbTipoRelato.getSelectedItem().toString()
                .replace("ASSÉDIO", "ASSEDIO")
                .replace("DISCRIMINAÇÃO", "DISCRIMINACAO")
                .replace("OPRESSÃO", "OPRESSAO")
                .replace(" ", "_");

        Relato relato = new Relato();

        relato.setTitulo(titulo);
        relato.setDescricao(descricao);
        relato.setLocal(local);
        relato.setUsuarioAnonimo(chkAnonimo.isSelected());

        relato.setCategoria(
                CategoriaRelato.valueOf(categoria)
        );

        relato.setStatus(StatusRelato.PENDENTE);

        relato.setData(LocalDate.now());

        GerenciadorRelatos.relatos.add(relato);
        carregarFeed();

        txtTitulo.setText("");
        txtLocal.setText("");
        txtRelato.setText("");
        chkAnonimo.setSelected(false);

        JOptionPane.showMessageDialog(
                this,
                "Relato enviado para análise do administrador!"
        );

    }

    private void carregarFeed() {

        painelFeed.removeAll();

        for (Relato relato : GerenciadorRelatos.relatos) {

            if (relato.getStatus() == StatusRelato.APROVADO) {

                String autor = relato.isUsuarioAnonimo()
                        ? "Usuário Anônimo"
                        : "Autor";

                painelFeed.add(new CardRelato(

                        autor,

                        relato.getCategoria().name().replace("_", " "),

                        relato.getDescricao(),

                        relato.getLocal(),

                        relato.getData().toString()

                ));

                painelFeed.add(Box.createVerticalStrut(15));

            }

        }

        if (painelFeed.getComponentCount() == 0) {

            JLabel vazio = new JLabel("Nenhum relato aprovado até o momento.");

            vazio.setFont(new Font("Arial", Font.PLAIN, 16));

            vazio.setBorder(new EmptyBorder(20,20,20,20));

            painelFeed.add(vazio);

        }

        painelFeed.revalidate();

        painelFeed.repaint();

    }

}