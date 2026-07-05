package view.Aluno;

import javax.swing.*;
import java.awt.*;

public class CardGraficoPizza extends JPanel {

    public CardGraficoPizza() {

        setBackground(Color.WHITE);

        setPreferredSize(new Dimension(350,250));

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        int tamanho = 140;

        int x = 30;

        int y = 35;

        g2.setColor(new Color(95,45,170));
        g2.fillArc(x,y,tamanho,tamanho,0,140);

        g2.setColor(new Color(170,130,230));
        g2.fillArc(x,y,tamanho,tamanho,140,110);

        g2.setColor(new Color(220,210,245));
        g2.fillArc(x,y,tamanho,tamanho,250,110);

        desenharLegenda(g2,new Color(95,45,170),"Assédio",200,60);
        desenharLegenda(g2,new Color(170,130,230),"Infraestrutura",200,95);
        desenharLegenda(g2,new Color(220,210,245),"Outros",200,130);

    }

    private void desenharLegenda(Graphics2D g2,
                                 Color cor,
                                 String texto,
                                 int x,
                                 int y){

        g2.setColor(cor);

        g2.fillRect(x,y,15,15);

        g2.setColor(Color.DARK_GRAY);

        g2.setFont(new Font("Arial",Font.PLAIN,14));

        g2.drawString(texto,x+25,y+13);

    }

}
