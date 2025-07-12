package org.example.gui.Paneles;

import org.example.gui.Otros.BotonBuilder;
import org.example.gui.Otros.Imagen;
import org.example.model.Enfrentamientos.Enfrentamiento;
import org.example.model.Enfrentamientos.GenerarCalendario;
import org.example.model.Estadisticas.*;
import org.example.model.Formatos.Liga;
import org.example.model.Participante.*;
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
        comboParticipantes = new JComboBox<>();
        for (Object p : torneo.getParticipantes()) {
            comboParticipantes.addItem((Participante) p);
        }

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

        areaEstadisticas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // padding

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

        if (generador instanceof Liga<?, ?, ?> liga) {
            liga.actualizarEstadisticasDesdeResultados();

            List<Map.Entry<Participante, EstadisticasFutbol>> listaOrdenada = new ArrayList<>();
            for (Map.Entry<?, ?> entry : liga.getTablaEstadisticas().entrySet()) {
                listaOrdenada.add((Map.Entry<Participante, EstadisticasFutbol>) entry);
            }

            listaOrdenada.sort((e1, e2) -> {
                int cmp = Integer.compare(e2.getValue().getPuntos(), e1.getValue().getPuntos());
                if (cmp == 0) {
                    int dif1 = e1.getValue().getDiferenciaGoles();
                    int dif2 = e2.getValue().getDiferenciaGoles();
                    cmp = Integer.compare(dif2, dif1);
                }
                return cmp;
            });

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

        switch (disciplina) {
            case "FUTBOL", "FIFA" -> {
                EstadisticasFutbol estadisticas = new EstadisticasFutbol((Equipo) p);
                for (Object obj : torneo.getEnfrentamientos()) {
                    Enfrentamiento enf = (Enfrentamiento) obj;
                    if (enf.getResultado() instanceof ResultadoFutbol) {
                        estadisticas.registrarResultado((ResultadoFutbol) enf.getResultado(), (Equipo) p, enf.getParticipante1().equals(p));
                    }
                }
                resultado.append(estadisticas.toTablaString());
            }

            case "AJEDREZ" -> {
                EstadisticasAjedrez estadisticas = new EstadisticasAjedrez(p);
                for (Object obj : torneo.getEnfrentamientos()) {
                    Enfrentamiento enf = (Enfrentamiento) obj;
                    if (enf.getResultado() instanceof ResultadoAjedrez) {
                        estadisticas.registrarResultado((ResultadoAjedrez) enf.getResultado(), p, enf.getParticipante1().equals(p));
                    }
                }
                resultado.append(estadisticas.toTablaString());
            }

            case "TENIS" -> {
                EstadisticasTenis estadisticas = new EstadisticasTenis((Jugador) p);
                for (Object obj : torneo.getEnfrentamientos()) {
                    Enfrentamiento enf = (Enfrentamiento) obj;
                    if (enf.getResultado() instanceof ResultadoTenis) {
                        estadisticas.registrarResultado((ResultadoTenis) enf.getResultado(), (Jugador) p, enf.getParticipante1().equals(p));
                    }
                }
                resultado.append(estadisticas.getEstadisticasCompletas());
            }

            case "TENIS_DE_MESA" -> {
                EstadisticaTenisDeMesa estadisticas = new EstadisticaTenisDeMesa(p);
                for (Object obj : torneo.getEnfrentamientos()) {
                    Enfrentamiento enf = (Enfrentamiento) obj;
                    if (enf.getResultado() instanceof ResultadoTenisDeMesa) {
                        estadisticas.registrarResultado((ResultadoTenisDeMesa) enf.getResultado(), p, enf.getParticipante1().equals(p));
                    }
                }
                resultado.append(estadisticas.toTablaString());
            }

            case "LOL" -> {
                EstadisticasLol estadisticas = new EstadisticasLol(p);
                for (Object obj : torneo.getEnfrentamientos()) {
                    Enfrentamiento enf = (Enfrentamiento) obj;
                    if (enf.getResultado() instanceof ResultadoLol) {
                        estadisticas.registrarResultado((ResultadoLol) enf.getResultado(), p, enf.getParticipante1().equals(p));
                    }
                }
                resultado.append(estadisticas.toTablaString());
            }

            default -> {
                resultado.append("Disciplina no soportada.");
            }
        }

        areaEstadisticas.setText(resultado.toString());
    }

}

