package org.example;

import org.example.gui.*;
import org.example.model.*;

import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        Torneo torneo = new Torneo("Los insanos cup", "pelota", jugadores, "10-03-2025 13:14", "brackets");

        torneo.addparticipante(new Jugador("Pepe", "98"));
        torneo.addparticipante(new Jugador("Ana", "32"));
        torneo.addparticipante(new Jugador("Luis", "77"));

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sistema de Torneos");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);

            PanelPrincipal panel = new PanelPrincipal(frame);
            frame.setContentPane(panel);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
