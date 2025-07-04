package org.example.gui;

import org.example.command.CambiarPanelCommand;

import javax.swing.*;
import java.awt.*;

public class BotonBuilder {

    public static JButton crearBoton(String texto, Color colorFondo, Runnable accion) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        boton.setPreferredSize(new Dimension(240, 60));
        boton.setFocusPainted(false);
        boton.setForeground(Color.WHITE);
        boton.setBackground(colorFondo);
        boton.setBorder(BorderFactory.createLineBorder(colorFondo.darker(), 2, true));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo);
            }
        });

        boton.addActionListener(e -> accion.run());

        return boton;
    }

    public static JButton crearBotonVolver(JFrame frame, JPanel destino) {
        return crearBoton("<- Volver", new Color(244, 62, 77), () ->
                new CambiarPanelCommand(frame, destino).execute()
        );
    }
    public static <T> JComboBox<T> crearComboBox(T[] items) {
        JComboBox<T> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(Color.DARK_GRAY);
        comboBox.setFocusable(false);
        comboBox.setPreferredSize(new Dimension(200, 30));
        return comboBox;
    }
}

