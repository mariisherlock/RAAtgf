package view.Aluno;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PainelChatVoluntario extends JPanel {

    private JTextArea areaChat;
    private JTextField txtMensagem;
    private JButton btnEnviar;

    private Color roxo = new Color(75, 40, 130);

    public PainelChatVoluntario() {

        setLayout(new BorderLayout());

        setBackground(new Color(245, 245, 250));

        criarCabecalho();

        criarCentro();

        criarRodape();

    }

    private void criarCabecalho(){

        JPanel topo = new JPanel(new BorderLayout());

        topo.setBackground(roxo);

        topo.setPreferredSize(new Dimension(0,70));

        topo.setBorder(new EmptyBorder(15,20,15,20));

        JLabel titulo = new JLabel("Conversa com Voluntário");

        titulo.setForeground(Color.WHITE);

        titulo.setFont(new Font("Arial",Font.BOLD,24));

        topo.add(titulo,BorderLayout.WEST);

        JLabel status = new JLabel("● Online");

        status.setForeground(Color.WHITE);

        status.setFont(new Font("Arial",Font.PLAIN,16));

        topo.add(status,BorderLayout.EAST);

        add(topo,BorderLayout.NORTH);

    }

    private void criarCentro(){

        areaChat = new JTextArea();

        areaChat.setEditable(false);

        areaChat.setFont(new Font("Arial", Font.PLAIN, 15));

        areaChat.setLineWrap(true);

        areaChat.setWrapStyleWord(true);

        areaChat.setMargin(new Insets(15,15,15,15));

        areaChat.setText(
                "👩‍💼 Voluntário\n\n" +
                        "Olá!\n\n" +
                        "Este espaço é destinado ao acolhimento e orientação.\n\n" +
                        "Como posso ajudá-la hoje?\n\n"
        );

        JScrollPane scroll = new JScrollPane(areaChat);

        scroll.setBorder(new EmptyBorder(20,20,20,20));

        add(scroll, BorderLayout.CENTER);

    }

    private void criarRodape(){

        JPanel rodape = new JPanel(new BorderLayout(10,10));

        rodape.setBorder(new EmptyBorder(15,20,20,20));

        txtMensagem = new JTextField();

        txtMensagem.setFont(new Font("Arial", Font.PLAIN, 15));

        btnEnviar = new JButton("Enviar");

        btnEnviar.setBackground(roxo);

        btnEnviar.setForeground(Color.WHITE);

        btnEnviar.setFocusPainted(false);

        btnEnviar.setFont(new Font("Arial", Font.BOLD, 14));

        rodape.add(txtMensagem, BorderLayout.CENTER);

        rodape.add(btnEnviar, BorderLayout.EAST);

        add(rodape, BorderLayout.SOUTH);

        configurarEventos();

    }

    private void configurarEventos(){

        btnEnviar.addActionListener(e -> enviarMensagem());

        txtMensagem.addActionListener(e -> enviarMensagem());

    }

    private void enviarMensagem(){

        String mensagem = txtMensagem.getText().trim();

        if(mensagem.isEmpty()){
            return;
        }

        areaChat.append("👤 Você\n");

        areaChat.append(mensagem + "\n\n");

        txtMensagem.setText("");

        responderVoluntario();

    }

    private void responderVoluntario(){

        String[] respostas = {

                "Obrigado por compartilhar isso conosco. Estamos aqui para ajudar.\n\n",

                "Entendo sua situação. Você fez o certo em procurar apoio.\n\n",

                "Sua mensagem foi registrada. Um voluntário poderá acompanhá-la.\n\n",

                "Você não está sozinha. Conte conosco para orientá-la.\n\n",

                "Caso deseje, continue a conversa e explique um pouco mais.\n\n"

        };

        int indice = (int)(Math.random()*respostas.length);

        areaChat.append("👩‍💼 Voluntário\n");

        areaChat.append(respostas[indice]);

    }

}