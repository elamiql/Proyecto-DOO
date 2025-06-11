package org.example.gui;

import javax.swing.*;
import java.awt.*;

public class PanelPrincipal extends JPanel {

    private JButton btnCrearTorneo;
    private JButton btnInscribirse;

    public PanelPrincipal(JFrame frame) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        btnCrearTorneo = new JButton("Crear Torneo");
        btnInscribirse = new JButton("Inscribirse a Torneo");

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

        // Mostrar PanelOrganizador
        btnCrearTorneo.addActionListener(e -> {
            frame.setContentPane(new PanelOrganizador(frame));
            frame.revalidate();
        });

        // Mostrar PanelParticipante
        btnInscribirse.addActionListener(e -> {
            frame.setContentPane(new PanelParticipante(frame));
            frame.revalidate();
        });

        setPreferredSize(new Dimension(500, 300));
    }
}
