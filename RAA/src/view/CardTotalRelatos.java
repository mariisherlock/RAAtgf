package view;

import javax.swing.*;
import java.awt.*;

public class CardTotalRelatos extends JPanel {

    public CardTotalRelatos(){

        setBackground(Color.WHITE);

        setLayout(new GridBagLayout());

        JLabel numero = new JLabel("412");

        numero.setFont(new Font("Arial",Font.BOLD,60));

        numero.setForeground(new Color(95,45,170));

        add(numero);

    }

}
