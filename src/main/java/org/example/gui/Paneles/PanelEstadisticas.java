package org.example.gui.Paneles;

import org.example.gui.Otros.BotonBuilder;
import org.example.gui.Otros.Imagen;
import org.example.model.Enfrentamientos.Enfrentamiento;
import org.example.model.Enfrentamientos.GenerarCalendario;
import org.example.model.Estadisticas.*;
import org.example.model.Formatos.Eliminatoria;
import org.example.model.Formatos.Liga;
import org.example.model.Participante.Participante;
import org.example.model.Resultado.*;
import org.example.model.torneo.Torneo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import static org.example.enums.Formato.LIGA;

public class PanelEstadisticas extends PanelFondo {

    private final JFrame frame;
    private final Torneo torneo;

    private JComboBox<Participante> comboParticipantes;
    private JTextArea areaEstadisticas;
    private JButton botonVer;

    public PanelEstadisticas(JFrame frame, Torneo torneo) {
        super(Imagen.cargarImagen("/Fondos/Fondo2.jpg"));
        this.frame = frame;
        this.torneo = torneo;

        inicializarComponentes();
        configurarLayout();
        configurarEventos();
    }

    private void inicializarComponentes() {
        ArrayList<Participante> participantes = torneo.getParticipantes();
        comboParticipantes = BotonBuilder.crearComboBox(participantes.toArray(new Participante[0]));
        botonVer = new JButton("Ver estadísticas");

        areaEstadisticas = new JTextArea(20, 45);
        areaEstadisticas.setEditable(false);
        areaEstadisticas.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaEstadisticas.setLineWrap(true);
        areaEstadisticas.setWrapStyleWord(true);
    }

    private void configurarLayout() {
        setLayout(new BorderLayout());


        if (!(torneo.getFormato() == LIGA)) {
            JPanel panelSuperior = new JPanel(new FlowLayout());
            panelSuperior.setOpaque(false);

            JLabel lblSeleccion = new JLabel("Selecciona participante:");
            lblSeleccion.setForeground(Color.WHITE);
            panelSuperior.add(lblSeleccion);
            panelSuperior.add(comboParticipantes);
            panelSuperior.add(botonVer);

            add(panelSuperior, BorderLayout.NORTH);
        } else {
            mostrarEstadisticasLiga();
        }


        areaEstadisticas.setOpaque(false);
        areaEstadisticas.setForeground(Color.WHITE);

        areaEstadisticas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(areaEstadisticas);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        add(scroll, BorderLayout.CENTER);


        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setOpaque(false);

        JButton btnVolver = BotonBuilder.crearBotonVolver(frame, new PanelDetalleTorneo(frame, torneo));
        panelInferior.add(btnVolver);

        add(panelInferior, BorderLayout.SOUTH);
    }


    private void configurarEventos() {
        botonVer.addActionListener((ActionEvent e) -> {
            Participante seleccionado = (Participante) comboParticipantes.getSelectedItem();
            if (seleccionado != null) {
                mostrarEstadisticas(seleccionado);
            }
        });
    }

    private void mostrarEstadisticas(Participante p) {
        if (torneo.getFormato() == LIGA) {
            mostrarEstadisticasLiga();
        } else {
            mostrarEstadisticasEliminatoria(p);
        }
    }
    private void mostrarEstadisticasLiga() {
        StringBuilder resultado = new StringBuilder();
        GenerarCalendario<?> generador = torneo.getGeneradorActivo();

        if (generador instanceof Liga<?> liga) {

            // Obtener las estadísticas y ordenarlas por puntos (de mayor a menor)
            List<Map.Entry<Participante, EstadisticasFutbol>> listaOrdenada =
                    new ArrayList<>(liga.getTablaEstadisticas().entrySet());

            listaOrdenada.sort((e1, e2) ->
                    Integer.compare(e2.getValue().getPuntos(), e1.getValue().getPuntos()));

            // Mostrar participantes ordenados por posición
            int posicion = 1;
            for (Map.Entry<Participante, EstadisticasFutbol> entry : listaOrdenada) {
                Participante participante = entry.getKey();
                EstadisticasFutbol estadisticas = entry.getValue();

                resultado.append(posicion).append(". ")
                        .append(participante.getNombre()).append("\n")
                        .append(estadisticas.toTablaString()).append("\n\n");

                posicion++;
            }

        } else {
            resultado.append("No se pudo recuperar la liga activa.");
        }

        areaEstadisticas.setText(resultado.toString());
    }



    private void mostrarEstadisticasEliminatoria(Participante p) {
        String disciplina = torneo.getDisciplina().getNombre().toUpperCase();
        StringBuilder resultado = new StringBuilder();
        GenerarCalendario<?> generador = torneo.getGeneradorActivo();

        if (!(generador instanceof Eliminatoria<?, ?, ?> eliminatoria)) {
            areaEstadisticas.setText("No se pudo recuperar la eliminatoria activa.");
            return;
        }


        Object estadistica = eliminatoria.getTablaEstadisticas().get(p);
        if (estadistica == null) {
            areaEstadisticas.setText("No hay estadísticas registradas para este participante.");
            return;
        }

        switch (disciplina) {
            case "FUTBOL", "FIFA" -> {
                EstadisticasFutbol ef = (EstadisticasFutbol) estadistica;
                resultado.append(ef.toTablaString());
            }
            case "AJEDREZ" -> {
                EstadisticasAjedrez ea = (EstadisticasAjedrez) estadistica;
                resultado.append(ea.toTablaString());
            }
            case "TENIS" -> {
                EstadisticasTenis et = (EstadisticasTenis) estadistica;
                resultado.append(et.getEstadisticasCompletas());
            }
            case "TENIS_DE_MESA" -> {
                EstadisticaTenisDeMesa etm = (EstadisticaTenisDeMesa) estadistica;
                resultado.append(etm.toTablaString());
            }
            case "LOL" -> {
                EstadisticasLol el = (EstadisticasLol) estadistica;
                resultado.append(el.toTablaString());
            }
            default -> {
                resultado.append("Disciplina no soportada.");
            }
        }

        areaEstadisticas.setText(resultado.toString());
    }



}

