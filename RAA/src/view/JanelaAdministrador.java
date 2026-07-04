package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class JanelaAdministrador extends JFrame {

    private JPanel centroPanel;
    private Color roxoSistema = new Color(75, 40, 130);

    private JButton btnInicio, btnRelatos, btnTipos, btnUsuarios, btnVoluntarios, btnConfig;

    public JanelaAdministrador() {
        setTitle("Dashboard - Proteção e Acolhimento");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(245, 245, 250));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBorder(new EmptyBorder(20, 10, 20, 10));

        Icon iconeHome = redimensionarIcone("src/imagens/nome_da_imagem_home.png");
        Icon iconeRelatos = redimensionarIcone("src/imagens/nome_da_imagem_relatos.png");
        Icon iconeTipos = redimensionarIcone("src/imagens/nome_da_imagem_tipos.png");
        Icon iconeUsuarios = redimensionarIcone("src/imagens/nome_da_imagem_usuarios.png"); // Ícone de Usuários
        Icon iconeVoluntarios = redimensionarIcone("src/imagens/nome_da_imagem_voluntarios.png"); // Ícone de Voluntários
        Icon iconeConfig = redimensionarIcone("src/imagens/nome_da_imagem_config.png");

        btnInicio = new JButton("Tela Inicial", iconeHome);
        btnRelatos = new JButton("Relatos", iconeRelatos);
        btnTipos = new JButton("Tipos de Relatos", iconeTipos);
        btnUsuarios = new JButton("Usuários", iconeUsuarios); // Mantido!
        btnVoluntarios = new JButton("Voluntários", iconeVoluntarios); // Adicionado!
        btnConfig = new JButton("Configurações", iconeConfig);



        JButton[] botoesMenu = {btnInicio, btnRelatos, btnTipos, btnUsuarios, btnVoluntarios, btnConfig};

        for (JButton btn : botoesMenu) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(180, 40));
            btn.setBackground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setIconTextGap(10);
            btn.setForeground(Color.DARK_GRAY);
            btn.setFont(new Font("Arial", Font.PLAIN, 14));

            sidebar.add(btn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        }


        centroPanel = new JPanel(new BorderLayout());


        btnTipos.addActionListener(e -> trocarTela(new PainelTiposdeRelato(), btnTipos));



        btnInicio.addActionListener(e -> trocarTela(new PainelTelaInicial(this), btnInicio));
        btnRelatos.addActionListener(e -> trocarTela(new PainelGerenciarRelatos(this), btnRelatos));
        btnTipos.addActionListener(e -> trocarTela(new PainelTiposdeRelato(), btnTipos));
        btnUsuarios.addActionListener(e -> trocarTela(new PainelUsuarios(this), btnUsuarios)); // Abre a tela de usuários
        btnVoluntarios.addActionListener(e -> trocarTela(new PainelVoluntarios(this), btnVoluntarios)); // Abre a tela de voluntários
        btnConfig.addActionListener(e -> trocarTela(new PainelConfiguracoes(), btnConfig));

        trocarTela(new PainelTelaInicial(this), btnInicio);

        btnInicio.addActionListener(e -> trocarTela(new JPanel(), btnInicio));
        btnConfig.addActionListener(e -> trocarTela(new JPanel(), btnConfig));

        add(sidebar, BorderLayout.WEST);
        add(centroPanel, BorderLayout.CENTER);
    }


    private void trocarTela(JPanel novoPainel, JButton botaoAtivo) {
        centroPanel.removeAll();
        centroPanel.add(novoPainel, BorderLayout.CENTER);
        centroPanel.revalidate();
        centroPanel.repaint();


        JButton[] todos = {btnInicio, btnRelatos, btnTipos, btnUsuarios, btnVoluntarios, btnConfig};
        for (JButton b : todos) {
            b.setBackground(Color.WHITE);
            b.setForeground(Color.DARK_GRAY);
            b.setFont(new Font("Arial", Font.PLAIN, 14));
        }


        botaoAtivo.setBackground(new Color(230, 225, 245));
        botaoAtivo.setForeground(roxoSistema);
        botaoAtivo.setFont(new Font("Arial", Font.BOLD, 14));
    }


    private Icon redimensionarIcone(String caminho) {
        try {
            ImageIcon imgBruta = new ImageIcon(caminho);
            if (imgBruta.getImage() != null) {
                return new ImageIcon(imgBruta.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            }
        } catch (Exception e) {
            System.out.println("Não foi possível carregar o ícone: " + caminho);
        }
        return null;
    }


}