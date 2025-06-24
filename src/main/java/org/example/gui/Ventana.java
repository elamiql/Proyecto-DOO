package org.example.gui;

import javax.swing.*;

public class Ventana extends JFrame {

    public Ventana(){
        super("Sistema de torneos");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);

        setContentPane(new PanelPrincipal(this));
        setVisible(true);
    }
}
