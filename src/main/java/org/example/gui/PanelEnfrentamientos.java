package org.example.gui;

import org.example.command.CambiarPanelCommand;
import org.example.model.Enfrentamiento;
import org.example.model.Torneo;
import org.example.model.Jugador;
import org.example.model.Equipo;

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

        String texto = "Partido " + num + ": " +
                e.getParticipante1().getNombre() + " vs " +
                e.getParticipante2().getNombre();

        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.PLAIN, 16));
        panelEnfrentamiento.add(lbl, BorderLayout.CENTER);

        JButton btnGanador = BotonBuilder.crearBoton(
                "Seleccionar Ganador",
                new Color(0, 120, 215),
                () -> seleccionarGanador(e)
        );

        panelEnfrentamiento.add(btnGanador, BorderLayout.EAST);

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
        Object ganadorObj = seleccionado.equals(e.getParticipante1().getNombre())
                ? e.getParticipante1()
                : e.getParticipante2();

        String nombreGanador = null;

        if (ganadorObj instanceof Jugador jugador) {
            torneo.registrarResultados(jugador, true);
            nombreGanador = jugador.getNombre();
        } else if (ganadorObj instanceof Equipo equipo) {
            // torneo.registrarResultados(equipo, true); // Si se habilita para equipos
            nombreGanador = equipo.getNombre();
        }

        if (nombreGanador != null) {
            JOptionPane.showMessageDialog(frame,
                    "Ganador registrado: " + nombreGanador);
            new CambiarPanelCommand(frame, new PanelEnfrentamientos(frame, torneo)).execute();
        } else {
            JOptionPane.showMessageDialog(frame,
                    "Error: tipo de ganador no soportado.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void inicializarPanelAbajo() {
        JButton btnVolver = BotonBuilder.crearBotonVolver(frame, new PanelDetalleTorneo(frame, torneo));
        JPanel panelAbajo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAbajo.setOpaque(false);
        panelAbajo.add(btnVolver);
        add(panelAbajo, BorderLayout.SOUTH);
    }
}
