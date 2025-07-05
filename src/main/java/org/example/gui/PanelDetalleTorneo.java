package org.example.gui;

import org.example.command.CambiarPanelCommand;
import org.example.model.*;
import org.example.exceptions.DatosInvalidosException;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PanelDetalleTorneo extends JPanel {

    private final JFrame frame;
    private final Torneo<?> torneo;

    public PanelDetalleTorneo(JFrame frame, Torneo<?> torneo) {
        this.frame = frame;
        this.torneo = torneo;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));

        add(crearTitulo(), BorderLayout.NORTH);
        add(crearAreaInformacion(), BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);
    }

    private JLabel crearTitulo() {
        JLabel titulo = new JLabel("Detalle del Torneo", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(new Color(40, 40, 40));
        return titulo;
    }

    private JScrollPane crearAreaInformacion() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 15));
        area.setBackground(Color.WHITE);
        area.setMargin(new Insets(10, 10, 10, 10));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String info = String.format("""
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
                torneo.isActivo() ? "Cerrado (Torneo en curso)" : "Activo (Inscripciones abiertas)"
        );

        area.setText(info);
        return new JScrollPane(area);
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(Color.WHITE);

        JButton btnVolver = BotonBuilder.crearBoton("← Volver", new Color(200, 55, 60), () ->
                new CambiarPanelCommand(frame, new PanelParticipante(frame)).execute()
        );

        JButton btnAccion = torneo.isActivo()
                ? BotonBuilder.crearBoton("Ver Enfrentamientos", new Color(0, 123, 255), () ->
                new CambiarPanelCommand(frame, new PanelEnfrentamientos(frame, torneo)).execute())
                : BotonBuilder.crearBoton("Inscribirse", new Color(40, 167, 69), this::accionInscribirse);

        panel.add(btnVolver);
        panel.add(btnAccion);
        return panel;
    }

    private void accionInscribirse() {
        if (torneo instanceof TorneoIndividual individual) {
            inscribirJugador(individual);
        } else {
            inscribirEquipo();
        }
    }

    private void inscribirJugador(TorneoIndividual torneoIndividual) {
        JTextField txtNombre = new JTextField();
        JTextField txtNumero = new JTextField();
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Número de contacto:"));
        panel.add(txtNumero);

        int result = JOptionPane.showConfirmDialog(this, panel, "Inscribirse al Torneo", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String nombre = txtNombre.getText().trim();
                String numero = txtNumero.getText().trim();

                if (nombre.isEmpty() || numero.isEmpty()) {
                    throw new DatosInvalidosException("Debes completar todos los campos.");
                }

                validarNombre(nombre);
                validarNumeroContacto(numero);

                Jugador jugador = new Jugador(nombre, numero);
                jugador.inscribirse(torneoIndividual);
                JOptionPane.showMessageDialog(this, "¡Inscripción exitosa!");
                new CambiarPanelCommand(frame, new PanelDetalleTorneo(frame, torneo)).execute();

            } catch (DatosInvalidosException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void inscribirEquipo() {
        String input = JOptionPane.showInputDialog(this, "¿Cuántos integrantes tiene tu equipo?");
        if (input == null) return;
        try {
            int cantidad = Integer.parseInt(input.trim());
            if (cantidad <= 0) throw new NumberFormatException();

            ArrayList<Jugador> jugadores = new ArrayList<>();
            for (int i = 1; i <= cantidad; i++) {
                JTextField txtNombre = new JTextField();
                JTextField txtNumero = new JTextField();
                JPanel panel = new JPanel(new GridLayout(2, 2));
                panel.add(new JLabel("Nombre del Jugador " + i + ":"));
                panel.add(txtNombre);
                panel.add(new JLabel("Número de contacto del Jugador " + i + ":"));
                panel.add(txtNumero);

                int result = JOptionPane.showConfirmDialog(this, panel, "Integrante " + i, JOptionPane.OK_CANCEL_OPTION);
                if (result != JOptionPane.OK_OPTION) return;

                String nombre = txtNombre.getText().trim();
                String numero = txtNumero.getText().trim();

                if (nombre.isEmpty() || numero.isEmpty()) {
                    throw new DatosInvalidosException("Campos incompletos para el integrante " + i);
                }

                validarNombre(nombre);
                validarNumeroContacto(numero);
                jugadores.add(new Jugador(nombre, numero));
            }

            JTextField txtNombreEquipo = new JTextField();
            JTextField txtNumeroEquipo = new JTextField();
            JPanel panelEquipo = new JPanel(new GridLayout(2, 2));
            panelEquipo.add(new JLabel("Nombre del equipo:"));
            panelEquipo.add(txtNombreEquipo);
            panelEquipo.add(new JLabel("Número de contacto del equipo:"));
            panelEquipo.add(txtNumeroEquipo);

            int equipoResult = JOptionPane.showConfirmDialog(this, panelEquipo, "Datos del equipo", JOptionPane.OK_CANCEL_OPTION);
            if (equipoResult != JOptionPane.OK_OPTION) return;

            String nombreEquipo = txtNombreEquipo.getText().trim();
            String numeroEquipo = txtNumeroEquipo.getText().trim();

            if (nombreEquipo.isEmpty() || numeroEquipo.isEmpty()) {
                throw new DatosInvalidosException("Debes ingresar nombre y número del equipo.");
            }

            validarNombre(nombreEquipo);
            validarNumeroContacto(numeroEquipo);

            Equipo equipo = new Equipo(nombreEquipo, numeroEquipo, jugadores);
            equipo.inscribirse(torneo);
            JOptionPane.showMessageDialog(this, "¡Equipo inscrito con éxito!");
            new CambiarPanelCommand(frame, new PanelDetalleTorneo(frame, torneo)).execute();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Número inválido de integrantes.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (DatosInvalidosException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Validaciones
    private void validarNombre(String nombre) {
        long count = nombre.chars().filter(Character::isLetter).count();
        if (count < 4) {
            throw new DatosInvalidosException("El nombre debe contener al menos 4 letras.");
        }
    }

    private void validarNumeroContacto(String numero) {
        if (!numero.matches("\\d{8}")) {
            throw new DatosInvalidosException("El número de contacto debe tener exactamente 8 dígitos.");
        }
    }
}
