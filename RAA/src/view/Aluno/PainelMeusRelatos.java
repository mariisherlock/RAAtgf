package view.Aluno;

import controller.RelatoController;
import model.Relato;
import model.Usuario;
import model.StatusRelato;
import model.GerenciadorRelatos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class PainelMeusRelatos extends JPanel {

    private final Usuario usuario;
    private final RelatoController controller = new RelatoController();

    public PainelMeusRelatos(Usuario usuario) {

        this.usuario = usuario;

        setLayout(new BorderLayout());

        setBackground(new Color(75,40,130));

        montarTela();

    }

    private void montarTela() {

        JLabel titulo = new JLabel("Meus Relatos");

        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        titulo.setForeground(Color.WHITE);

        titulo.setFont(new Font("Arial", Font.BOLD, 30));

        titulo.setBorder(new EmptyBorder(20,0,20,0));

        add(titulo, BorderLayout.NORTH);


        JPanel painel = new JPanel();

        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        painel.setBackground(new Color(245,245,250));


        List<Relato> relatos = GerenciadorRelatos.relatos;


        if(relatos.isEmpty()){

            JLabel vazio = new JLabel("Você ainda não publicou nenhum relato.");

            vazio.setFont(new Font("Arial",Font.PLAIN,18));

            vazio.setBorder(new EmptyBorder(20,20,20,20));

            painel.add(vazio);

        }else{

            for(Relato relato : relatos){

                painel.add(criarCard(relato));

                painel.add(Box.createVerticalStrut(15));

            }

        }

        JScrollPane scroll = new JScrollPane(painel);

        scroll.setBorder(null);

        add(scroll,BorderLayout.CENTER);

    }

    private JPanel criarCard(Relato relato){

        JPanel card = new JPanel(new BorderLayout());

        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(

                BorderFactory.createLineBorder(new Color(220,220,220)),

                new EmptyBorder(15,15,15,15)

        ));


        JLabel lblTitulo = new JLabel(relato.getTitulo());

        lblTitulo.setFont(new Font("Arial",Font.BOLD,18));

        card.add(lblTitulo,BorderLayout.NORTH);


        JTextArea descricao = new JTextArea(relato.getDescricao());

        descricao.setEditable(false);

        descricao.setLineWrap(true);

        descricao.setWrapStyleWord(true);

        descricao.setBackground(Color.WHITE);

        descricao.setFont(new Font("Arial",Font.PLAIN,15));

        descricao.setBorder(new EmptyBorder(10,0,10,0));

        card.add(descricao,BorderLayout.CENTER);


        JPanel rodape = new JPanel(new GridLayout(3,1));

        rodape.setOpaque(false);

        rodape.add(new JLabel("Categoria: " + relato.getCategoria()));

        rodape.add(new JLabel("Local: " + relato.getLocal()));

        JLabel status = new JLabel("Status: " + relato.getStatus());

        if(relato.getStatus() == StatusRelato.PENDENTE){

            status.setForeground(new Color(220,140,0));

        }else if(relato.getStatus() == StatusRelato.APROVADO){

            status.setForeground(new Color(0,150,0));

        }else{

            status.setForeground(Color.BLUE);

        }

        rodape.add(status);

        card.add(rodape,BorderLayout.SOUTH);

        return card;

    }

}
