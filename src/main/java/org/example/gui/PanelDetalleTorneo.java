package org.example.gui;

import org.example.command.CambiarPanelCommand;
import org.example.model.Equipo;
import org.example.model.Jugador;
import org.example.model.Torneo;
import org.example.model.TorneoIndividual;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PanelDetalleTorneo extends JPanel {

    public PanelDetalleTorneo(JFrame frame, Torneo<?> torneo) {
        setLayout(new BorderLayout(10, 10));

        // Título
        JLabel labelTitulo = new JLabel("Detalle del Torneo", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(labelTitulo, BorderLayout.NORTH);

        // Información del torneo
        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.setFont(new Font("Monospaced", Font.PLAIN, 14));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String datos = String.format("""
                Nombre: %s
                Disciplina: %s
                Formato: %s
                Fecha: %s
                Participantes: %d
                Estado: %s
                """,
                torneo.getNombre(),
                torneo.getDisciplina().getNombre(),
                torneo.getFormato(),
                torneo.getFecha().format(formatter),
                torneo.getParticipantes().size(),
                !torneo.isActivo() ? "Activo (Inscripciones abiertas)" : "Cerrado (Torneo en curso)"
        );

        info.setText(datos);
        add(info, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> {
            frame.setContentPane(new PanelParticipante(frame));
            frame.revalidate();
        });

        JButton btnAccion;
        //Si el torneo aun no a empezado
        if (!torneo.isActivo()) {
            btnAccion = new JButton("Inscribirse");
            btnAccion.addActionListener(e -> {
                if (torneo instanceof TorneoIndividual torneoIndividual) {
                    JPanel panel = new JPanel(new GridLayout(2, 2));
                    JTextField txtNombre = new JTextField();
                    JTextField txtNumero = new JTextField();

                    panel.add(new JLabel("Nombre:"));
                    panel.add(txtNombre);
                    panel.add(new JLabel("Número:"));
                    panel.add(txtNumero);

                    int result = JOptionPane.showConfirmDialog(this, panel, "Inscribirse al Torneo", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        String nombre = txtNombre.getText().trim();
                        String numero = txtNumero.getText().trim();

                        if (!nombre.isEmpty() && !numero.isEmpty()) {
                            Jugador jugador=new Jugador(nombre, numero);
                            jugador.inscribirse(torneo);
                            JOptionPane.showMessageDialog(this, "¡Inscripción exitosa!");
                            new CambiarPanelCommand(frame, new PanelDetalleTorneo(frame, torneo)).execute();

                        } else {
                            JOptionPane.showMessageDialog(this, "Debes completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    String input = JOptionPane.showInputDialog(this, "¿Cuántos integrantes tiene tu equipo?");
                    if (input == null) return; // Cancelado
                    try {
                        int cantidad = Integer.parseInt(input.trim());
                        if (cantidad <= 0) throw new NumberFormatException();

                        java.util.List<Jugador> jugadores = new ArrayList<>();
                        for (int i = 1; i <= cantidad; i++) {
                            JTextField txtNombre = new JTextField();
                            JTextField txtNumero = new JTextField();
                            JPanel panel = new JPanel(new GridLayout(2, 2));
                            panel.add(new JLabel("Nombre del Jugador " + i + ":"));
                            panel.add(txtNombre);
                            panel.add(new JLabel("Número del Jugador " + i + ":"));
                            panel.add(txtNumero);

                            int result = JOptionPane.showConfirmDialog(this, panel, "Integrante " + i, JOptionPane.OK_CANCEL_OPTION);
                            if (result != JOptionPane.OK_OPTION) return;

                            String nombre = txtNombre.getText().trim();
                            String numero = txtNumero.getText().trim();

                            if (nombre.isEmpty() || numero.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Campos incompletos para el integrante " + i, "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            jugadores.add(new Jugador(nombre, numero));
                        }

                        JTextField txtNombreEquipo = new JTextField();
                        JTextField txtNumeroEquipo = new JTextField();
                        JPanel panelEquipo = new JPanel(new GridLayout(2, 2));
                        panelEquipo.add(new JLabel("Nombre del equipo:"));
                        panelEquipo.add(txtNombreEquipo);
                        panelEquipo.add(new JLabel("Número del equipo:"));
                        panelEquipo.add(txtNumeroEquipo);

                        int equipoResult = JOptionPane.showConfirmDialog(this, panelEquipo, "Datos del equipo", JOptionPane.OK_CANCEL_OPTION);
                        if (equipoResult != JOptionPane.OK_OPTION) return;

                        String nombreEquipo = txtNombreEquipo.getText().trim();
                        String numeroEquipo = txtNumeroEquipo.getText().trim();

                        if (nombreEquipo.isEmpty() || numeroEquipo.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Debes ingresar nombre y número del equipo.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        Equipo equipo = new Equipo(nombreEquipo, numeroEquipo, new ArrayList<>(jugadores));
                        equipo.inscribirse(torneo);
                        JOptionPane.showMessageDialog(this, "¡Equipo inscrito con éxito!");
                        new CambiarPanelCommand(frame, new PanelDetalleTorneo(frame, torneo)).execute();

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Número inválido de integrantes.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        } else {//Si ya empezo mostrar enfrentamientos
            btnAccion = new JButton("Ver Enfrentamientos");
            btnAccion.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Aquí se mostrarían los enfrentamientos del torneo.");

            });
        }

        panelBotones.add(btnVolver);
        panelBotones.add(btnAccion);

        add(panelBotones, BorderLayout.SOUTH);
    }
}

