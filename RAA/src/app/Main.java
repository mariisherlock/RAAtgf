package app;
import view.JanelaAdministrador;

import javax.swing.*;
public class Main{
public static void main(String[] args) {

    try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
        e.printStackTrace();
    }

    SwingUtilities.invokeLater(() -> {
        new JanelaAdministrador().setVisible(true);
    });

}}