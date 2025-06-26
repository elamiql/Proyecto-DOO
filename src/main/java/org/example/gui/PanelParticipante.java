package org.example.gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;


import org.example.model.Torneo;
import org.example.model.GestorTorneos;

public class PanelParticipante extends JPanel {

    private JPanel panelLista;
    private JScrollPane scrollPane;

    public PanelParticipante(JFrame frame) {
        setLayout(new BorderLayout(10, 10));

        panelLista = new JPanel();
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(panelLista);
        add(scrollPane, BorderLayout.CENTER);

        cargarTorneos();

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnVolver = new JButton("Volver");
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);

        btnVolver.addActionListener(e -> {
            frame.setContentPane(new PanelPrincipal(frame));
            frame.revalidate();
        });
    }

    private void cargarTorneos() {
        panelLista.removeAll();

        List<Torneo> torneos = GestorTorneos.obtenerTorneos();
        for (Torneo torneo : torneos) {
            JPanel panelTorneo = new JPanel(new BorderLayout(10, 10));
            JLabel etiqueta = new JLabel(torneo.toString());//Descripcion general de los torneos

            JButton btnVer = new JButton("Ver");
            btnVer.addActionListener(e -> {
                //Te lleva a un panel de detalles del torneo aun sin hacer
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                //topFrame.setContentPane(new PanelDetalleTorneo(topFrame, torneo));
                topFrame.revalidate();
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

