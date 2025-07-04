package org.example.gui;

import org.example.command.CambiarPanelCommand;
import org.example.enums.Deporte;
import org.example.enums.Videojuegos;
import org.example.enums.Formato;
import org.example.model.GestorTorneos;
import org.example.model.Torneo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PanelParticipante extends PanelFondo {

    private final JFrame frame;
    private JPanel panelLista;
    private JScrollPane scrollPane;
    private JComboBox<String> filtroEstadoCombo;
    private JComboBox<String> filtroDisciplinaCombo;
    private JComboBox<String> filtroFormatoCombo;


    public PanelParticipante(JFrame frame) {
        super(Imagen.cargarImagen("/Fondos/Fondo2.jpg"));
        this.frame = frame;
        setLayout(new BorderLayout(10, 10));

        inicializarPanelSuperior();
        inicializarPanelLista();
        inicializarPanelBotones();

        cargarTorneos();
    }

    private void inicializarPanelSuperior() {
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSuperior.setOpaque(false);

        filtroEstadoCombo = BotonBuilder.crearComboBox(new String[]{"Todos", "Por empezar", "Empezados"});
        filtroDisciplinaCombo = BotonBuilder.crearComboBox(getDisciplinas());
        filtroFormatoCombo = BotonBuilder.crearComboBox(getFormatos());

        filtroEstadoCombo.addActionListener(e -> cargarTorneos());
        filtroDisciplinaCombo.addActionListener(e -> cargarTorneos());
        filtroFormatoCombo.addActionListener(e -> cargarTorneos());

        panelSuperior.add(new JLabel("Estado:"));
        panelSuperior.add(filtroEstadoCombo);
        panelSuperior.add(new JLabel("Disciplina:"));
        panelSuperior.add(filtroDisciplinaCombo);
        panelSuperior.add(new JLabel("Formato:"));
        panelSuperior.add(filtroFormatoCombo);

        add(panelSuperior, BorderLayout.NORTH);
    }

    private void inicializarPanelLista() {
        panelLista = new JPanel();
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));
        panelLista.setOpaque(false);

        scrollPane = new JScrollPane(panelLista);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void inicializarPanelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setOpaque(false);

        JButton btnVolver = BotonBuilder.crearBotonVolver(frame, new PanelPrincipal(frame));
        panelBotones.add(btnVolver);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarTorneos() {
        panelLista.removeAll();

        List<Torneo> torneos = GestorTorneos.obtenerTorneos();

        String filtroEstado = (String) filtroEstadoCombo.getSelectedItem();
        String filtroDisciplina = (String) filtroDisciplinaCombo.getSelectedItem();
        String filtroFormato = (String) filtroFormatoCombo.getSelectedItem();

        // Filtros
        if (filtroEstado != null) {
            switch (filtroEstado) {
                case "Por empezar" -> torneos = torneos.stream().filter(t -> !t.isActivo()).collect(Collectors.toList());
                case "Empezados" -> torneos = torneos.stream().filter(Torneo::isActivo).collect(Collectors.toList());
            }
        }

        if (filtroDisciplina != null && !filtroDisciplina.equals("Todas las disciplinas")) {
            torneos = torneos.stream()
                    .filter(t -> t.getDisciplina().getNombre().equals(filtroDisciplina))
                    .collect(Collectors.toList());
        }

        if (filtroFormato != null && !filtroFormato.equals("Todos los formatos")) {
            torneos = torneos.stream()
                    .filter(t -> t.getFormato().name().equals(filtroFormato))
                    .collect(Collectors.toList());
        }

        for (Torneo torneo : torneos) {
            JPanel panelTorneo = new JPanel(new BorderLayout(10, 10));
            panelTorneo.setOpaque(false);

            JLabel etiqueta = new JLabel(torneo.toString());
            JButton btnVer = BotonBuilder.crearBoton("Ver", new Color(0, 153, 204),
                    () -> new CambiarPanelCommand(frame, new PanelDetalleTorneo(frame, torneo)).execute());

            panelTorneo.add(etiqueta, BorderLayout.CENTER);
            panelTorneo.add(btnVer, BorderLayout.EAST);
            panelTorneo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            panelLista.add(panelTorneo);
        }

        panelLista.revalidate();
        panelLista.repaint();
    }

    private String[] getDisciplinas() {
        List<String> disciplinas = new ArrayList<>();
        disciplinas.add("Todas las disciplinas");

        for (Deporte d : Deporte.values()) {
            disciplinas.add(d.getNombre());
        }

        for (Videojuegos v : Videojuegos.values()) {
            disciplinas.add(v.getNombre());
        }

        return disciplinas.toArray(new String[0]);
    }

    private String[] getFormatos() {
        String[] valores = new String[Formato.values().length + 1];
        valores[0] = "Todos los formatos";
        for (int i = 0; i < Formato.values().length; i++) {
            valores[i + 1] = Formato.values()[i].name();
        }
        return valores;
    }
}
