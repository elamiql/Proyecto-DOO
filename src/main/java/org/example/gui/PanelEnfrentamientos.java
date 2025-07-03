package org.example.gui;

import org.example.command.CambiarPanelCommand;
import org.example.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelEnfrentamientos extends JPanel {
    private final JFrame frame;
    private final Torneo<?> torneo;

    public PanelEnfrentamientos(JFrame frame, Torneo<?> torneo) {
        this.frame = frame;
        this.torneo = torneo;

        setLayout(new BorderLayout(10, 10));

        add(crearTitulo(), BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);
        add(crearPanelAbajo(), BorderLayout.SOUTH);
    }

    private JLabel crearTitulo() {
        JLabel titulo = new JLabel("Enfrentamientos del Torneo", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        return titulo;
    }

    private JScrollPane crearPanelCentral() {
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        List<Enfrentamiento> enfrentamientos = torneo.getEnfrentamientos();

        if (enfrentamientos.isEmpty()) {
            panelCentral.add(new JLabel("Aún no se han generado enfrentamientos."));
        } else {
            int num = 1;
            for (Enfrentamiento e : enfrentamientos) {
                panelCentral.add(crearPanelEnfrentamiento(num++, e));
            }
        }
        return new JScrollPane(panelCentral);
    }

    private JPanel crearPanelEnfrentamiento(int num, Enfrentamiento e) {
        JPanel panel = new JPanel(new BorderLayout());
        String baseTexto = "Partido " + num + ": " + e.getParticipante1().getNombre() + " vs " + e.getParticipante2().getNombre();
        JLabel lbl = new JLabel(baseTexto);

        JButton btn = crearBotonSeleccionGanador(e, lbl, baseTexto);
        panel.add(lbl, BorderLayout.CENTER);
        panel.add(btn, BorderLayout.EAST);

        if (e.getGanador() != null) {
            lbl.setText(baseTexto + " - Ganador: " + obtenerNombreGanador(e.getGanador()));
            btn.setEnabled(false);
        }

        return panel;
    }

    private JButton crearBotonSeleccionGanador(Enfrentamiento e, JLabel lbl, String baseTexto) {
        JButton btn = new JButton("Seleccionar Ganador");

        btn.addActionListener(ae -> {
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

            if (seleccionado == null) return;

            JPasswordField passwordField = new JPasswordField();
            int confirmacion = JOptionPane.showConfirmDialog(
                    frame,
                    passwordField,
                    "Ingresa la contraseña para confirmar",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (confirmacion != JOptionPane.OK_OPTION) return;

            String claveIngresada = new String(passwordField.getPassword());
            if (!claveIngresada.equals(torneo.getContraseña())) {
                JOptionPane.showMessageDialog(frame, "Contraseña incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Participante ganador = seleccionado.equals(e.getParticipante1().getNombre())
                    ? e.getParticipante1()
                    : e.getParticipante2();

            e.setGanador(ganador);

            // Reemplaza cualquier ocurrencia de "A vs B" en nombres compuestos
            String marcadorVs = e.getParticipante1().getNombre() + " vs " + e.getParticipante2().getNombre();
            reemplazarGanadorPorSubstring(torneo.getEnfrentamientos(), marcadorVs, ganador);

            JOptionPane.showMessageDialog(frame, "Ganador registrado: " + seleccionado);
            new CambiarPanelCommand(frame, new PanelEnfrentamientos(frame, torneo)).execute();
        });

        return btn;
    }

    private void reemplazarGanadorPorSubstring(List<Enfrentamiento> enfrentamientos, String marcadorVs, Participante ganador) {
        for (Enfrentamiento enf : enfrentamientos) {
            if (enf.getParticipante1() != null && enf.getParticipante1().getNombre().contains(marcadorVs)) {
                String nuevoNombre = enf.getParticipante1().getNombre().replace(marcadorVs, ganador.getNombre());
                enf.setParticipante1(new ParticipantePlaceholder(nuevoNombre));
            }
            if (enf.getParticipante2() != null && enf.getParticipante2().getNombre().contains(marcadorVs)) {
                String nuevoNombre = enf.getParticipante2().getNombre().replace(marcadorVs, ganador.getNombre());
                enf.setParticipante2(new ParticipantePlaceholder(nuevoNombre));
            }
        }
    }

    private String obtenerNombreGanador(Object ganador) {
        if (ganador instanceof Jugador) {
            return ((Jugador) ganador).getNombre();
        } else if (ganador instanceof Equipo) {
            return ((Equipo) ganador).getNombre();
        }
        return ganador.toString(); // También cubre ParticipantePlaceholder
    }

    private JPanel crearPanelAbajo() {
        JPanel abajo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> {
            new CambiarPanelCommand(frame, new PanelDetalleTorneo(frame, torneo)).execute();
        });
        abajo.add(btnVolver);
        return abajo;
    }
}
