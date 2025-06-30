package org.example.gui;

import org.example.command.CambiarPanelCommand;
import org.example.model.Enfrentamiento;
import org.example.model.Torneo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelEnfrentamientos extends JPanel {

    public PanelEnfrentamientos(JFrame frame, Torneo<?> torneo) {
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Enfrentamientos del Torneo", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        JTextArea area = new JTextArea();
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        area.setEditable(false);

        List<Enfrentamiento> enfrentamientos = torneo.getEnfrentamientos();
        if (enfrentamientos.isEmpty()) {
            area.setText("Aún no se han generado enfrentamientos.");
        } else {
            StringBuilder sb = new StringBuilder();
            int num = 1;
            for (Enfrentamiento e : enfrentamientos) {
                sb.append("Partido ").append(num++).append(": ")
                        .append(e.getParticipante1().getNombre())
                        .append(" vs ")
                        .append(e.getParticipante2().getNombre())
                        .append("\n");
            }
            area.setText(sb.toString());
        }

        JScrollPane scroll = new JScrollPane(area);
        add(scroll, BorderLayout.CENTER);

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> {
            new CambiarPanelCommand(frame, new PanelDetalleTorneo(frame, torneo)).execute();
        });

        JPanel abajo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        abajo.add(btnVolver);
        add(abajo, BorderLayout.SOUTH);
    }
}
