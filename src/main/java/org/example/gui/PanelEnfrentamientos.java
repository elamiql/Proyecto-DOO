package org.example.gui;

import org.example.command.CambiarPanelCommand;
import org.example.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelEnfrentamientos extends JPanel {

    private final JFrame frame;
    private final Torneo<?> torneo;
    private final JPanel panelCentral;

    public PanelEnfrentamientos(JFrame frame, Torneo<?> torneo) {
        this.frame = frame;
        this.torneo = torneo;
        setLayout(new BorderLayout(10, 10));

        panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setOpaque(false);

        inicializarTitulo();
        inicializarPanelCentral();
        inicializarPanelAbajo();
    }

    private void inicializarTitulo() {
        JLabel titulo = new JLabel("Enfrentamientos del Torneo", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);
    }

    private void inicializarPanelCentral() {
        cargarEnfrentamientos();
        JScrollPane scroll = new JScrollPane(panelCentral);
        add(scroll, BorderLayout.CENTER);
    }

    private void cargarEnfrentamientos() {
        panelCentral.removeAll();

        List<Enfrentamiento> enfrentamientos = torneo.getEnfrentamientos();
        if (enfrentamientos.isEmpty()) {
            JLabel lbl = new JLabel("Aún no se han generado enfrentamientos.");
            lbl.setFont(new Font("Arial", Font.ITALIC, 16));
            panelCentral.add(lbl);
        } else {
            int num = 1;
            for (Enfrentamiento e : enfrentamientos) {
                panelCentral.add(crearPanelEnfrentamiento(e, num++));
            }
        }
        panelCentral.revalidate();
        panelCentral.repaint();
    }

    private JPanel crearPanelEnfrentamiento(Enfrentamiento e, int num) {
        JPanel panelEnfrentamiento = new JPanel(new BorderLayout(5, 5));
        panelEnfrentamiento.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelEnfrentamiento.setOpaque(false);

        String baseTexto = "Partido " + num + ": " +
                e.getParticipante1().getNombre() + " vs " +
                e.getParticipante2().getNombre();

        JLabel lbl = new JLabel(baseTexto);
        lbl.setFont(new Font("Arial", Font.PLAIN, 16));
        panelEnfrentamiento.add(lbl, BorderLayout.CENTER);

        JButton btnGanador = new JButton("Seleccionar Ganador");
        panelEnfrentamiento.add(btnGanador, BorderLayout.EAST);

        if (e.getGanador() != null) {
            lbl.setText(baseTexto + " - Ganador: " + obtenerNombreGanador(e.getGanador()));
            btnGanador.setEnabled(false);
        } else {
            btnGanador.addActionListener(ae -> seleccionarGanador(e));
        }

        return panelEnfrentamiento;
    }

    private void seleccionarGanador(Enfrentamiento e) {
        String[] opciones = {
                e.getParticipante1().getNombre(),
                e.getParticipante2().getNombre()
        };

        String seleccionado = (String) JOptionPane.showInputDialog(
                frame,
                "Selecciona el ganador:",
                "Ganador del Partido",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (seleccionado != null) {
            JPasswordField passwordField = new JPasswordField();
            int confirmacion = JOptionPane.showConfirmDialog(
                    frame,
                    passwordField,
                    "Ingresa la contraseña para confirmar",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (confirmacion == JOptionPane.OK_OPTION) {
                String claveIngresada = new String(passwordField.getPassword());
                if (claveIngresada.equals(torneo.getContraseña())) {
                    registrarGanador(e, seleccionado);
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Contraseña incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void registrarGanador(Enfrentamiento e, String seleccionado) {
        Participante ganador = seleccionado.equals(e.getParticipante1().getNombre())
                ? e.getParticipante1()
                : e.getParticipante2();

        e.setGanador(ganador);

        String nombreGanadorLimpio = limpiarNombreGanador(ganador.getNombre());
        String patron = "Ganador " + e.getParticipante1().getNombre() + " vs " + e.getParticipante2().getNombre();

        actualizarEnfrentamientosPosteriores(patron, nombreGanadorLimpio);

        JOptionPane.showMessageDialog(frame, "Ganador registrado: " + nombreGanadorLimpio);

        new CambiarPanelCommand(frame, new PanelEnfrentamientos(frame, torneo)).execute();
    }

    private void actualizarEnfrentamientosPosteriores(String patron, String nombreGanadorLimpio) {
        List<Enfrentamiento> enfrentamientos = torneo.getEnfrentamientos();

        for (Enfrentamiento enf : enfrentamientos) {
            boolean actualizado = false;

            // Verificar participante 1
            if (enf.getParticipante1() != null) {
                String nombreActual1 = enf.getParticipante1().getNombre();
                if (nombreActual1.equals(patron) || nombreActual1.contains(patron)) {
                    String nuevoNombre1 = nombreActual1.replace(patron, nombreGanadorLimpio);
                    enf.setParticipante1(new ParticipantePlaceholder(nuevoNombre1));
                    actualizado = true;
                }
            }

            // Verificar participante 2
            if (enf.getParticipante2() != null) {
                String nombreActual2 = enf.getParticipante2().getNombre();
                if (nombreActual2.equals(patron) || nombreActual2.contains(patron)) {
                    String nuevoNombre2 = nombreActual2.replace(patron, nombreGanadorLimpio);
                    enf.setParticipante2(new ParticipantePlaceholder(nuevoNombre2));
                    actualizado = true;
                }
            }
        }
    }

    private String limpiarNombreGanador(String nombre) {
        String nombreLimpio = nombre;

        while (nombreLimpio.startsWith("Ganador ")) {
            nombreLimpio = nombreLimpio.substring(8);
        }
        return nombreLimpio;
    }

    private String obtenerNombreGanador(Object ganador) {
        if (ganador instanceof Jugador) {
            return ((Jugador) ganador).getNombre();
        } else if (ganador instanceof Equipo) {
            return ((Equipo) ganador).getNombre();
        }
        return ganador.toString();
    }

    private void inicializarPanelAbajo() {
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> {
            new CambiarPanelCommand(frame, new PanelDetalleTorneo(frame, torneo)).execute();
        });

        JPanel panelAbajo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAbajo.setOpaque(false);
        panelAbajo.add(btnVolver);
        add(panelAbajo, BorderLayout.SOUTH);
    }
}