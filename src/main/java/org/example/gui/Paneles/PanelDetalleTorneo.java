package org.example.gui.Paneles;

import org.example.command.CambiarPanelCommand;
import org.example.gui.Otros.Builder;
import org.example.exceptions.DatosInvalidosException;
import org.example.gui.Otros.Imagen;
import org.example.model.Participante.Equipo;
import org.example.model.Participante.Jugador;
import org.example.model.torneo.Torneo;
import org.example.model.torneo.TorneoIndividual;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Panel gráfico que muestra los detalles de un torneo, permitiendo ver información general
 * y realizar acciones como ver enfrentamientos o inscribirse.
 * <p>Este panel se adapta dinámicamente según el estado del torneo y su tipo (individual o por equipos).</p>
 */
public class PanelDetalleTorneo extends PanelFondo{

    private final JFrame frame;
    private final Torneo<?> torneo;

    /**
     * Crea un nuevo panel de detalle para el torneo proporcionado.
     * @param frame la ventana principal en la que se mostrará el panel.
     * @param torneo el torneo cujos detalles serán mostrados.
     */
    public PanelDetalleTorneo(JFrame frame, Torneo<?> torneo) {
        super(Imagen.cargarImagen("/Fondos/Fondo2.jpg"));
        this.frame = frame;
        this.torneo = torneo;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));


        add(crearAreaInformacion(), BorderLayout.NORTH);
        add(crearPanelBotones(), BorderLayout.SOUTH);
    }



    /**
     * Crea y devuelve un área de desplazamiento con la información general del torneo.
     * @return un {@link JScrollPane} con los detalles del torneo.
     */
    private JPanel crearAreaInformacion() {
        JPanel panelInfo = new JPanel();
        panelInfo.setOpaque(false);
        panelInfo.setLayout(new BorderLayout(10, 10));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        JLabel nombreTorneo = Builder.crearTitulo(torneo.getNombre());



        JLabel formatoDisciplina = new JLabel(
                torneo.getFormato() + " de " + torneo.getDisciplina().getNombre(),
                SwingConstants.CENTER
        );
        formatoDisciplina.setFont(new Font("SansSerif", Font.ITALIC, 20));
        formatoDisciplina.setForeground(Color.LIGHT_GRAY); // Gris claro para contraste
        formatoDisciplina.setBorder(BorderFactory.createEmptyBorder(5, 0, 20, 0));

        JPanel detalles = new JPanel(new GridLayout(0, 2, 15, 15));
        detalles.setOpaque(false);

        detalles.add(crearEtiquetaDestacada("Fecha:"));
        detalles.add(crearEtiqueta(torneo.getFecha().format(formatter)));

        detalles.add(crearEtiquetaDestacada("Participantes:"));
        detalles.add(crearEtiqueta(String.valueOf(torneo.getParticipantes().size())));

        detalles.add(crearEtiquetaDestacada("Estado:"));
        detalles.add(crearEtiqueta(
                torneo.isActivo() ? "Cerrado (Torneo en curso)" : "Activo (Inscripciones abiertas)"
        ));

        JPanel centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);
        centro.add(formatoDisciplina, BorderLayout.NORTH);
        centro.add(detalles, BorderLayout.CENTER);

        panelInfo.add(nombreTorneo, BorderLayout.NORTH);
        panelInfo.add(centro, BorderLayout.CENTER);

        return panelInfo;
    }


    /**
     * Crea una etiqueta (JLabel) con estilo estandar.
     * @param texto El texto que mostrará la etiqueta.
     * @return Una instancia de {@link JLabel} con el texto y estilo aplicado.
     */
    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        etiqueta.setForeground(Color.WHITE);
        return etiqueta;
    }

    /**
     * Crea una etiqueta (JLabel) con estilo destacado para relementos importantes.
     * @param texto El texto que mostrará la etiqueta.
     * @return Una instancia de {@link JLabel} con el texto en estilo destacado.
     */
    private JLabel crearEtiquetaDestacada(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Segoe UI", Font.BOLD, 15));
        etiqueta.setForeground(Color.WHITE);
        return etiqueta;
    }



    /**
     * Crea el panel de botones que permite volver o realizar una acción según el estado del torneo.
     * @return un {@link JPanel} con los botones configurados.
     */
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setOpaque(false);
        JButton btnVolver = Builder.crearBotonVolver(frame, new PanelParticipante(frame));

        if (torneo.isActivo()) {
            JButton btnEnfrentamientos = Builder.crearBoton("Ver Enfrentamientos", new Color(0, 123, 255), () ->
                    new CambiarPanelCommand(frame, new PanelEnfrentamientos(frame, torneo)).execute());

            JButton btnEstadisticas = Builder.crearBoton("Ver Estadísticas", new Color(255, 193, 7), () ->
                    new CambiarPanelCommand(frame, new PanelEstadisticas(frame, torneo)).execute());

            panel.add(btnVolver);
            panel.add(btnEnfrentamientos);
            panel.add(btnEstadisticas);
        } else {
            JButton btnInscribirse = Builder.crearBoton("Inscribirse", new Color(40, 167, 69), this::accionInscribirse);
            panel.add(btnInscribirse);
            panel.add(btnVolver);
        }

        return panel;
    }

    /**
     * Ejecuta la acción correspondiente para inscribir a un participante, diferenciando entre torneo individual o por equipos.
     */
    private void accionInscribirse() {
        if (torneo instanceof TorneoIndividual individual) {
            inscribirJugador(individual);
        } else {
            inscribirEquipo();
        }
    }

    /**
     * Muestra un formulario para inscribir a un jugador en un torneo individual.
     * @param torneoIndividual el torneo individual al que se inscribirá el jugador.
     */
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

    /**
     * Muestra formularios sucesivos para inscribir un equipo con múltiples integrantes al torneo.
     */
    private void inscribirEquipo() {
        String input = JOptionPane.showInputDialog(this, "¿Cuántos integrantes tiene tu equipo?");
        if (input == null) return;
        try {
            int cantidad = Integer.parseInt(input.trim());
            if (cantidad <= 1) throw new NumberFormatException();

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

    /**
     * Valida que el nombre contenga al menos 4 letras.
     * @param nombre el nombre a validar.
     * @throws DatosInvalidosException si el nombre no cumple con el mínimo de letras.
     */
    private void validarNombre(String nombre) {
        long count = nombre.chars().filter(Character::isLetter).count();
        if (count < 4) {
            throw new DatosInvalidosException("El nombre debe contener al menos 4 letras.");
        }
    }

    /**
     * Valida que el número de contacto tenga exactamente 8 dígitos.
     * @param numero el número de contacto a validar.
     * @throws DatosInvalidosException si el número no tiene el formato correcto.
     */
    private void validarNumeroContacto(String numero) {
        if (!numero.matches("\\d{8}")) {
            throw new DatosInvalidosException("El número de contacto debe tener exactamente 8 dígitos.");
        }
    }
}
