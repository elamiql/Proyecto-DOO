package org.example.gui;

import org.example.model.ActualizadorEstadoTorneos;

import javax.swing.*;

public class Ventana extends JFrame {

    public Ventana(){

        super("Sistema de torneos");
        ActualizadorEstadoTorneos actualizador = new ActualizadorEstadoTorneos(1_000); // cada 1 seg
        actualizador.iniciar();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);

        setContentPane(new PanelPrincipal(this));
        setVisible(true);
    }
}
