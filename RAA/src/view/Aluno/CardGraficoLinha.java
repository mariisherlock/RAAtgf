package view.Aluno;

import javax.swing.*;
import java.awt.*;

public class CardGraficoLinha extends JPanel {

    private final int[] valores = {12,18,28,22,30,40,32,40,26,34,29,24};

    public CardGraficoLinha() {

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

        int margem = 40;

        int largura = getWidth()-margem*2;

        int altura = getHeight()-margem*2;

        g2.setColor(new Color(230,230,230));

        g2.drawLine(margem,margem,margem,margem+altura);

        g2.drawLine(margem,margem+altura,margem+largura,margem+altura);

        int passo = largura/(valores.length-1);

        g2.setStroke(new BasicStroke(3));

        g2.setColor(new Color(95,45,170));

        for(int i=0;i<valores.length-1;i++){

            int x1 = margem+i*passo;

            int y1 = margem+altura-(valores[i]*altura/50);

            int x2 = margem+(i+1)*passo;

            int y2 = margem+altura-(valores[i+1]*altura/50);

            g2.drawLine(x1,y1,x2,y2);

            g2.fillOval(x1-4,y1-4,8,8);

        }

        int ultimoX = margem+(valores.length-1)*passo;

        int ultimoY = margem+altura-(valores[valores.length-1]*altura/50);

        g2.fillOval(ultimoX-4,ultimoY-4,8,8);

    }

}