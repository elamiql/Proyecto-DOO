package org.example.gui;

import javax.swing.*;
import java.awt.*;

public class PanelParticipante extends JPanel {

    public PanelParticipante(JFrame frame) {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Panel del Participante", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.CENTER);

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> {
            frame.setContentPane(new PanelPrincipal(frame));
            frame.revalidate();
        });

        add(btnVolver, BorderLayout.SOUTH);
    }
}