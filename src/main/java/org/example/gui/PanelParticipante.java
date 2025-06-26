package org.example.gui;

import org.example.command.CambiarPanelCommand;
import org.example.model.GestorTorneos;
import org.example.model.Torneo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelParticipante extends JPanel {

    private JPanel panelLista;
    private JScrollPane scrollPane;

    public PanelParticipante(JFrame frame) {
        setLayout(new BorderLayout(10, 10));

        panelLista = new JPanel();
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(panelLista);
        add(scrollPane, BorderLayout.CENTER);

        cargarTorneos(frame);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> new CambiarPanelCommand(frame, new PanelPrincipal(frame)).execute());
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarTorneos(JFrame frame) {
        panelLista.removeAll();

        List<Torneo> torneos = GestorTorneos.obtenerTorneos();
        for (Torneo torneo : torneos) {
            JPanel panelTorneo = new JPanel(new BorderLayout(10, 10));
            JLabel etiqueta = new JLabel(torneo.toString());

            JButton btnVer = new JButton("Ver");
            btnVer.addActionListener(e -> {
                //new CambiarPanelCommand(frame, new PanelDetalleTorneo(frame, torneo)).execute();
            });

            panelTorneo.add(etiqueta, BorderLayout.CENTER);
            panelTorneo.add(btnVer, BorderLayout.EAST);
            panelTorneo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            panelLista.add(panelTorneo);
        }

        panelLista.revalidate();
        panelLista.repaint();
    }
}
