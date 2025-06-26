package org.example.gui;

import org.example.command.CambiarPanelCommand;

import javax.swing.*;
import java.awt.*;

public class PanelPrincipal extends JPanel {

    private final JButton btnCrearTorneo;
    private final JButton btnInscribirse;

    public PanelPrincipal(JFrame frame) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        btnCrearTorneo = new JButton("Crear Torneo");
        btnInscribirse = new JButton("Ver Torneos");

        Font botonFont = new Font("Arial", Font.BOLD, 18);
        Dimension botonSize = new Dimension(220, 60);

        btnCrearTorneo.setFont(botonFont);
        btnCrearTorneo.setPreferredSize(botonSize);

        btnInscribirse.setFont(botonFont);
        btnInscribirse.setPreferredSize(botonSize);

        gbc.insets = new Insets(20, 10, 20, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(btnCrearTorneo, gbc);

        gbc.gridy = 1;
        add(btnInscribirse, gbc);


        btnCrearTorneo.addActionListener(e -> new CambiarPanelCommand(frame, new PanelOrganizador(frame)).execute());
        btnInscribirse.addActionListener(e -> new CambiarPanelCommand(frame, new PanelParticipante(frame)).execute());

        setPreferredSize(new Dimension(500, 300));
    }
}
