package org.example.gui;

import org.example.command.CambiarPanelCommand;
import org.example.enums.Deporte;
import org.example.enums.Videojuegos;
import org.example.enums.Formato;
import org.example.model.Disciplina;
import org.example.model.GestorTorneos;
import org.example.model.Torneo;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class PanelParticipante extends JPanel {

    private JPanel panelLista;
    private JScrollPane scrollPane;
    private JComboBox<String> filtroEstadoCombo;
    private JComboBox<String> filtroDisciplinaCombo;
    private JComboBox<String> filtroFormatoCombo;

    public PanelParticipante(JFrame frame) {
        setLayout(new BorderLayout(10, 10));

        // Panel de filtros arriba
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        filtroEstadoCombo = new JComboBox<>(new String[]{"Todos", "Por empezar", "Empezados"});
        filtroDisciplinaCombo = new JComboBox<>();
        filtroFormatoCombo = new JComboBox<>();

        filtroDisciplinaCombo.addItem("Todas las disciplinas");
        for (Deporte d : Deporte.values()) {
            filtroDisciplinaCombo.addItem(d.getNombre());
        }
        for (Videojuegos v : Videojuegos.values()) {
            filtroDisciplinaCombo.addItem(v.getNombre());
        }

        filtroFormatoCombo.addItem("Todos los formatos");
        for (Formato f : Formato.values()) {
            filtroFormatoCombo.addItem(f.name());
        }

        filtroEstadoCombo.addActionListener(e -> cargarTorneos(frame));
        filtroDisciplinaCombo.addActionListener(e -> cargarTorneos(frame));
        filtroFormatoCombo.addActionListener(e -> cargarTorneos(frame));

        panelSuperior.add(new JLabel("Estado:"));
        panelSuperior.add(filtroEstadoCombo);
        panelSuperior.add(new JLabel("Disciplina:"));
        panelSuperior.add(filtroDisciplinaCombo);
        panelSuperior.add(new JLabel("Formato:"));
        panelSuperior.add(filtroFormatoCombo);

        add(panelSuperior, BorderLayout.NORTH);

        // Panel central con lista de torneos
        panelLista = new JPanel();
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(panelLista);
        add(scrollPane, BorderLayout.CENTER);

        // Botón volver
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> new CambiarPanelCommand(frame, new PanelPrincipal(frame)).execute());
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);

        // Cargar torneos al iniciar
        cargarTorneos(frame);
    }

    private void cargarTorneos(JFrame frame) {
        panelLista.removeAll();

        List<Torneo> torneos = GestorTorneos.obtenerTorneos();

        String filtroEstado = (String) filtroEstadoCombo.getSelectedItem();
        String filtroDisciplina = (String) filtroDisciplinaCombo.getSelectedItem();
        String filtroFormato = (String) filtroFormatoCombo.getSelectedItem();

        // Filtrar por estado
        if (filtroEstado != null) {
            switch (filtroEstado) {
                case "Por empezar":
                    torneos = torneos.stream().filter(t -> !t.isActivo()).collect(Collectors.toList());
                    break;
                case "Empezados":
                    torneos = torneos.stream().filter(Torneo::isActivo).collect(Collectors.toList());
                    break;
                case "Todos":
                default:
                    break;
            }
        }

        // Filtrar por disciplina
        if (filtroDisciplina != null && !filtroDisciplina.equals("Todas las disciplinas")) {
            torneos = torneos.stream()
                    .filter(t -> t.getDisciplina().getNombre().equals(filtroDisciplina))
                    .collect(Collectors.toList());
        }

        // Filtrar por formato
        if (filtroFormato != null && !filtroFormato.equals("Todos los formatos")) {
            torneos = torneos.stream()
                    .filter(t -> t.getFormato().name().equals(filtroFormato))
                    .collect(Collectors.toList());
        }

        // Mostrar los torneos filtrados
        for (Torneo torneo : torneos) {
            JPanel panelTorneo = new JPanel(new BorderLayout(10, 10));
            JLabel etiqueta = new JLabel(torneo.toString());

            JButton btnVer = new JButton("Ver");
            btnVer.addActionListener(e ->
                    new CambiarPanelCommand(frame, new PanelDetalleTorneo(frame, torneo)).execute()
            );

            panelTorneo.add(etiqueta, BorderLayout.CENTER);
            panelTorneo.add(btnVer, BorderLayout.EAST);
            panelTorneo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            panelLista.add(panelTorneo);
        }

        panelLista.revalidate();
        panelLista.repaint();
    }
}
