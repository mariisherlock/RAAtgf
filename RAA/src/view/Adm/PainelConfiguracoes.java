package view.Adm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class PainelConfiguracoes extends JPanel {

    private JTextField txtEmail;
    private JPasswordField txtSenhaAtual, txtNovaSenha;
    private JLabel lblFotoPerfil;
    private String caminhoFotoSelecionada = "";

    public PainelConfiguracoes() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(40, 50, 40, 50));

        Color roxoSistema = new Color(75, 40, 130);


        JLabel lblTitle = new JLabel("Configurações de Conta");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitle.setForeground(roxoSistema);
        lblTitle.setBorder(new EmptyBorder(0, 0, 30, 0));
        add(lblTitle, BorderLayout.NORTH);


        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        JPanel fotoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        fotoPanel.setBackground(Color.WHITE);

        lblFotoPerfil = new JLabel();
        lblFotoPerfil.setPreferredSize(new Dimension(120, 120));
        lblFotoPerfil.setBorder(BorderFactory.createLineBorder(roxoSistema, 2));
        lblFotoPerfil.setHorizontalAlignment(SwingConstants.CENTER);
        lblFotoPerfil.setText("Sem Foto");

        JButton btnMudarFoto = new JButton("Alterar Foto");
        btnMudarFoto.setFocusPainted(false);
        btnMudarFoto.setBackground(Color.WHITE);
        btnMudarFoto.setForeground(roxoSistema);

        fotoPanel.add(lblFotoPerfil);
        fotoPanel.add(btnMudarFoto);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        formPanel.add(fotoPanel, gbc);


        gbc.gridwidth = 1;
        gbc.gridy++;
        formPanel.add(new JLabel("E-mail do Administrador:"), gbc);

        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        txtEmail.setPreferredSize(new Dimension(0, 35));
        formPanel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Senha Atual:"), gbc);

        gbc.gridx = 1;
        txtSenhaAtual = new JPasswordField();
        txtSenhaAtual.setPreferredSize(new Dimension(0, 35));
        formPanel.add(txtSenhaAtual, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Nova Senha:"), gbc);

        gbc.gridx = 1;
        txtNovaSenha = new JPasswordField();
        txtNovaSenha.setPreferredSize(new Dimension(0, 35));
        formPanel.add(txtNovaSenha, gbc);

        add(formPanel, BorderLayout.CENTER);


        JButton btnSalvar = new JButton("Salvar Alterações");
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalvar.setBackground(roxoSistema);
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setPreferredSize(new Dimension(200, 45));
        btnSalvar.setFocusPainted(false);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.setBackground(Color.WHITE);
        southPanel.add(btnSalvar);
        add(southPanel, BorderLayout.SOUTH);




        btnMudarFoto.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int res = chooser.showOpenDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                caminhoFotoSelecionada = file.getAbsolutePath();


                ImageIcon img = new ImageIcon(caminhoFotoSelecionada);
                Image redim = img.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                lblFotoPerfil.setIcon(new ImageIcon(redim));
                lblFotoPerfil.setText("");
            }
        });


        btnSalvar.addActionListener(e -> {
            String email = txtEmail.getText();
            String senha = new String(txtNovaSenha.getPassword());


            JOptionPane.showMessageDialog(this, "Configurações de " + email + " atualizadas com sucesso!");
        });
    }
}