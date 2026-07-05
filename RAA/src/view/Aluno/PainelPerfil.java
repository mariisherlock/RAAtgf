package view.Aluno;

import model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PainelPerfil extends JPanel {

    private final Usuario usuario;

    public PainelPerfil(Usuario usuario) {

        this.usuario = usuario;

        setLayout(new BorderLayout());

        setBackground(new Color(75,40,130));

        montarTela();

    }

    private void montarTela(){

        JLabel titulo = new JLabel("Meu Perfil");

        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        titulo.setForeground(Color.WHITE);

        titulo.setFont(new Font("Arial", Font.BOLD, 30));

        titulo.setBorder(new EmptyBorder(20,0,20,0));

        add(titulo, BorderLayout.NORTH);


        JPanel painel = new JPanel();

        painel.setOpaque(false);

        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        painel.setBorder(new EmptyBorder(30,60,30,60));


        painel.add(criarLabel("Nome: " + usuario.getNome()));

        painel.add(Box.createVerticalStrut(20));

        painel.add(criarLabel("E-mail: " + usuario.getEmail()));

        painel.add(Box.createVerticalStrut(20));

        painel.add(criarLabel("Tipo: " + usuario.getTipo()));


        add(painel, BorderLayout.CENTER);

    }

    private JLabel criarLabel(String texto){

        JLabel label = new JLabel(texto);

        label.setForeground(Color.WHITE);

        label.setFont(new Font("Arial", Font.PLAIN, 20));

        return label;

    }

}
