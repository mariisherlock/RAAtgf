package view.Aluno;

import controller.UsuarioController;
import model.Usuario;
import view.Adm.JanelaAdministrador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginView extends JFrame {


    private final UsuarioController controller = new UsuarioController();


    private JTextField txtEmail;
    private JPasswordField txtSenha;

    private JButton btnEntrar;
    private JButton btnCriarConta;



    public LoginView() {

        configurarJanela();
        inicializarComponentes();
        montarTela();
        configurarEventos();

    }



    private void configurarJanela() {

        setTitle("RAA - Login");

        setSize(900, 550);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setResizable(false);

    }



    private void inicializarComponentes() {

        txtEmail = new JTextField(25);

        txtSenha = new JPasswordField(25);

        btnEntrar = new JButton("Entrar");

        btnCriarConta = new JButton("Criar Conta");

        btnEntrar.setBackground(new Color(102, 51, 153));
        btnEntrar.setForeground(Color.WHITE);

    }



    private void montarTela() {

        setLayout(new BorderLayout());


        JPanel painelEsquerdo = new JPanel();

        painelEsquerdo.setBackground(new Color(102, 51, 153));

        painelEsquerdo.setPreferredSize(new Dimension(270, 0));

        painelEsquerdo.setLayout(new GridBagLayout());

        JLabel lblRAA = new JLabel("RAA");

        lblRAA.setFont(new Font("Segoe UI", Font.BOLD, 42));

        lblRAA.setForeground(Color.WHITE);

        painelEsquerdo.add(lblRAA);



        JPanel painelDireito = new JPanel();

        painelDireito.setBorder(new EmptyBorder(40, 50, 40, 50));

        painelDireito.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);

        gbc.anchor = GridBagConstraints.WEST;

        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel lblTitulo = new JLabel("Bem-vindo!");

        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));

        painelDireito.add(lblTitulo, gbc);

        gbc.gridy++;

        JLabel lblSubtitulo = new JLabel("Faça login para acessar o sistema.");

        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        painelDireito.add(lblSubtitulo, gbc);

        gbc.gridy++;

        painelDireito.add(Box.createVerticalStrut(20), gbc);


        gbc.gridy++;

        JLabel lblEmail = new JLabel("E-mail");

        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 16));

        painelDireito.add(lblEmail, gbc);

        gbc.gridy++;

        painelDireito.add(txtEmail, gbc);



        gbc.gridy++;

        JLabel lblSenha = new JLabel("Senha");

        lblSenha.setFont(new Font("Segoe UI", Font.BOLD, 16));

        painelDireito.add(lblSenha, gbc);

        gbc.gridy++;

        painelDireito.add(txtSenha, gbc);


        gbc.gridy++;

        gbc.insets = new Insets(20, 8, 8, 8);

        painelDireito.add(btnEntrar, gbc);


        gbc.gridy++;

        gbc.insets = new Insets(25, 8, 5, 8);

        JLabel lblCadastro = new JLabel("Não possui uma conta?");

        lblCadastro.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        painelDireito.add(lblCadastro, gbc);

        gbc.gridy++;

        gbc.insets = new Insets(5, 8, 8, 8);

        painelDireito.add(btnCriarConta, gbc);

        add(painelEsquerdo, BorderLayout.WEST);

        add(painelDireito, BorderLayout.CENTER);

    }


    private void configurarEventos() {

        // Login
        btnEntrar.addActionListener(e -> {

            String email = txtEmail.getText().trim();
            String senha = new String(txtSenha.getPassword());

            Usuario usuario = controller.fazerLogin(email, senha);

            if (usuario == null) {
                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Bem-vindo(a), " + usuario.getNome() + "!"
            );

            if (usuario.getTipo().equalsIgnoreCase("ADMINISTRADOR")) {

                new JanelaAdministrador().setVisible(true);

            } else {

                new HomeView(usuario).setVisible(true);

            }

            dispose();

        });

        // Abrir tela de cadastro
        btnCriarConta.addActionListener(e -> {

            new CadastroView().setVisible(true);

            dispose();

        });

        txtSenha.addActionListener(e -> btnEntrar.doClick());

    }

}
