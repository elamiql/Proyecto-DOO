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

    public PanelEnfrentamientos(JFrame frame, Torneo<?> torneo) {
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Enfrentamientos del Torneo", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));

        List<Enfrentamiento> enfrentamientos = torneo.getEnfrentamientos();
        if (enfrentamientos.isEmpty()) {
            panelCentral.add(new JLabel("Aún no se han generado enfrentamientos."));
        } else {
            int num = 1;
            for (Enfrentamiento e : enfrentamientos) {
                JPanel panelEnfrentamiento = new JPanel(new BorderLayout());
                String texto = "Partido " + num++ + ": " +
                        e.getParticipante1().getNombre() + " vs " +
                        e.getParticipante2().getNombre();
                JLabel lbl = new JLabel(texto);
                panelEnfrentamiento.add(lbl, BorderLayout.CENTER);

                JButton btnGanador = new JButton("Seleccionar Ganador");
                btnGanador.addActionListener(ae -> {
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
                                Object ganadorObj = seleccionado.equals(e.getParticipante1().getNombre())
                                        ? e.getParticipante1()
                                        : e.getParticipante2();

                                String nombreGanador = null;

                                if (ganadorObj instanceof Jugador) {
                                    Jugador ganador = (Jugador) ganadorObj;
                                    torneo.registrarResultados(ganador, true);
                                    nombreGanador = ganador.getNombre();
                                } else if (ganadorObj instanceof Equipo) {
                                    Equipo ganador = (Equipo) ganadorObj;
                                    //torneo.registrarResultados(ganador, true);
                                    nombreGanador = ganador.getNombre();
                                }

                                if (nombreGanador != null) {
                                    JOptionPane.showMessageDialog(frame,
                                            "Ganador registrado: " + nombreGanador);
                                } else {
                                    JOptionPane.showMessageDialog(frame,
                                            "Error: tipo de ganador no soportado.",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                }

                            } else {
                                JOptionPane.showMessageDialog(frame,
                                        "Contraseña incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                });

                panelEnfrentamiento.add(btnGanador, BorderLayout.EAST);
                panelCentral.add(panelEnfrentamiento);
            }
        }

        JScrollPane scroll = new JScrollPane(panelCentral);
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
