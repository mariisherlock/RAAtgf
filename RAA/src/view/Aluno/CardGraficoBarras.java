package view.Aluno;

import javax.swing.*;
import java.awt.*;

public class CardGraficoBarras extends JPanel {

    private final int[] valores = {35,50,22,40};

    private final String[] nomes = {
            "Campus A",
            "Campus B",
            "Campus C",
            "Campus D"
    };

    public CardGraficoBarras(){

        setBackground(Color.WHITE);

        setPreferredSize(new Dimension(350,250));

    }

    @Override
    protected void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        int larguraBarra = 45;

        int espacamento = 35;

        int base = 180;

        int x = 40;

        for(int i=0;i<valores.length;i++){

            int altura = valores[i]*3;

            g2.setColor(new Color(95,45,170));

            g2.fillRoundRect(
                    x,
                    base-altura,
                    larguraBarra,
                    altura,
                    10,
                    10
            );

            g2.setColor(Color.DARK_GRAY);

            g2.drawString(nomes[i],x-5,205);

            x += larguraBarra+espacamento;

        }

    }

}