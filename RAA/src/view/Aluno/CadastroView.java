package view.Aluno;

import controller.CampusController;
import controller.CidadeController;
import controller.CursoController;
import controller.UsuarioController;

import model.Campus;
import model.Cidade;
import model.Curso;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CadastroView extends JFrame {

    //=========================
    // Controllers
    //=========================

    private final UsuarioController usuarioController = new UsuarioController();
    private final CampusController campusController = new CampusController();
    private final CidadeController cidadeController = new CidadeController();
    private final CursoController cursoController = new CursoController();

    //=========================
    // Componentes
    //=========================

    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtTelefone;
    private JTextField txtNascimento;

    private JPasswordField txtSenha;
    private JPasswordField txtConfirmarSenha;

    private JComboBox<Campus> cbCampus;
    private JComboBox<Cidade> cbCidade;
    private JComboBox<Curso> cbCurso;

    private JRadioButton rbAluno;
    private JRadioButton rbVoluntario;
    private JRadioButton rbAdministrador;

    private JButton btnCadastrar;
    private JButton btnVoltar;

    public CadastroView() {

        configurarJanela();
        inicializarComponentes();
        montarTela();
        configurarEventos();

        // Ative quando o banco estiver pronto
        // carregarCampi();
        // carregarCidades();
        // carregarCursos();

    }

    private void configurarJanela() {

        setTitle("Cadastro - RAA");

        setSize(950,700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setResizable(false);

    }

    private void inicializarComponentes() {

        txtNome = new JTextField();
        txtEmail = new JTextField();
        txtTelefone = new JTextField();
        txtNascimento = new JTextField();

        txtSenha = new JPasswordField();
        txtConfirmarSenha = new JPasswordField();

        cbCampus = new JComboBox<>();
        cbCidade = new JComboBox<>();
        cbCurso = new JComboBox<>();

        rbAluno = new JRadioButton("Aluno");
        rbVoluntario = new JRadioButton("Voluntário");
        rbAdministrador = new JRadioButton("Administrador");

        ButtonGroup grupo = new ButtonGroup();

        grupo.add(rbAluno);
        grupo.add(rbVoluntario);
        grupo.add(rbAdministrador);

        rbAluno.setSelected(true);

        btnCadastrar = new JButton("Cadastrar");
        btnVoltar = new JButton("Voltar");

    }

    private void montarTela() {

        JPanel painel = new JPanel(new GridBagLayout());

        painel.setBorder(new EmptyBorder(20,35,20,35));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8,8,8,8);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;

        //-------------------------
        // Título
        //-------------------------

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel titulo = new JLabel("Cadastro de Usuário");

        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        titulo.setFont(new Font("Segoe UI",Font.BOLD,32));

        painel.add(titulo,gbc);

        //-------------------------
        // Nome
        //-------------------------

        gbc.gridy++;
        gbc.gridwidth = 2;

        painel.add(new JLabel("Nome"),gbc);

        gbc.gridy++;

        painel.add(txtNome,gbc);

        //-------------------------
        // Email
        //-------------------------

        gbc.gridy++;

        painel.add(new JLabel("E-mail"),gbc);

        gbc.gridy++;

        painel.add(txtEmail,gbc);

        //-------------------------
        // Senha
        //-------------------------

        gbc.gridy++;
        gbc.gridwidth = 1;

        gbc.gridx = 0;

        painel.add(new JLabel("Senha"),gbc);

        gbc.gridx = 1;

        painel.add(new JLabel("Confirmar Senha"),gbc);

        gbc.gridy++;

        gbc.gridx = 0;

        painel.add(txtSenha,gbc);

        gbc.gridx = 1;

        painel.add(txtConfirmarSenha,gbc);

        //-------------------------
        // Telefone
        //-------------------------

        gbc.gridy++;

        gbc.gridx = 0;
        gbc.gridwidth = 2;

        painel.add(new JLabel("Telefone"),gbc);

        gbc.gridy++;

        painel.add(txtTelefone,gbc);

        //-------------------------
        // Continua na Parte 2...
        //-------------------------
        // Data de nascimento
        //-------------------------

        gbc.gridy++;
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        painel.add(new JLabel("Data de Nascimento"), gbc);

        gbc.gridx = 1;
        painel.add(new JLabel("Cidade"), gbc);

        gbc.gridy++;

        gbc.gridx = 0;
        painel.add(txtNascimento, gbc);

        gbc.gridx = 1;
        painel.add(cbCidade, gbc);

        //-------------------------
        // Campus
        //-------------------------

        gbc.gridy++;

        gbc.gridx = 0;
        painel.add(new JLabel("Campus"), gbc);

        gbc.gridx = 1;
        painel.add(new JLabel("Curso"), gbc);

        gbc.gridy++;

        gbc.gridx = 0;
        painel.add(cbCampus, gbc);

        gbc.gridx = 1;
        painel.add(cbCurso, gbc);

        //-------------------------
        // Tipo de usuário
        //-------------------------

        gbc.gridy++;

        gbc.gridx = 0;
        gbc.gridwidth = 2;

        painel.add(new JLabel("Tipo de Usuário"), gbc);

        gbc.gridy++;

        JPanel painelTipo = new JPanel(new FlowLayout(FlowLayout.LEFT));

        painelTipo.add(rbAluno);
        painelTipo.add(rbVoluntario);
        painelTipo.add(rbAdministrador);

        painel.add(painelTipo, gbc);

        //-------------------------
        // Botões
        //-------------------------

        gbc.gridy++;

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));

        btnCadastrar.setPreferredSize(new Dimension(140,35));
        btnVoltar.setPreferredSize(new Dimension(140,35));

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnVoltar);

        painel.add(painelBotoes, gbc);

        add(painel);

    }

    private void configurarEventos() {

        //=========================
        // Botão Voltar
        //=========================

        btnVoltar.addActionListener(e -> {

            new LoginView().setVisible(true);

            dispose();

        });

        //=========================
        // Botão Cadastrar
        //=========================

        btnCadastrar.addActionListener(e -> {

            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String nascimento = txtNascimento.getText().trim();

            String senha = new String(txtSenha.getPassword());
            String confirmar = new String(txtConfirmarSenha.getPassword());

            if (nome.isEmpty() ||
                    email.isEmpty() ||
                    telefone.isEmpty() ||
                    nascimento.isEmpty() ||
                    senha.isEmpty() ||
                    confirmar.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Preencha todos os campos.");

                return;
            }

            if (!senha.equals(confirmar)) {

                JOptionPane.showMessageDialog(this,
                        "As senhas não coincidem.");

                return;
            }

            String tipo = "ALUNO";

            if (rbAdministrador.isSelected()) {

                tipo = "ADMINISTRADOR";

            } else if (rbVoluntario.isSelected()) {

                tipo = "VOLUNTARIO";

            }

            try {

                usuarioController.inserirUsuario(
                        nome,
                        email,
                        senha,
                        tipo
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Cadastro realizado com sucesso!"
                );

                new LoginView().setVisible(true);

                dispose();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage()
                );

            }

        });

    }

}