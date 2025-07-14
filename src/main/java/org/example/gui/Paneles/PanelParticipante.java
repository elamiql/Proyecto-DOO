package org.example.gui.Paneles;

import org.example.command.CambiarPanelCommand;
import org.example.enums.Deporte;
import org.example.enums.Videojuegos;
import org.example.enums.Formato;
import org.example.gui.Otros.BotonBuilder;
import org.example.gui.Otros.Imagen;
import org.example.model.torneo.GestorTorneos;
import org.example.model.torneo.Torneo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Panel gráfico que muestra al participante la lista de torneos disponibles con opciones de filtrado.
 *
 * <p>Permite filtrar por estado (empezado/por empezar), disciplina (deporte/videojuego) y formato (liga, eliminación, etc.).
 * Desde este panel se puede acceder a la vista de detalles e inscripción a un torneo.</p>
 *
 * <p>Este panel extiende {@link PanelFondo} para mostrar una imagen de fondo personalizada.</p>
 */
public class PanelParticipante extends PanelFondo {

    private final JFrame frame;
    private JPanel panelLista;
    private JScrollPane scrollPane;
    private JComboBox<String> filtroEstadoCombo;
    private JComboBox<String> filtroDisciplinaCombo;
    private JComboBox<String> filtroFormatoCombo;

    /**
     * Crea un nuevo panel para que los participantes visualicen y se inscriban en torneos.
     *
     * @param frame ventana principal donde se mostrará el panel.
     */
    public PanelParticipante(JFrame frame) {
        super(Imagen.cargarImagen("/Fondos/Fondo2.jpg"));
        this.frame = frame;
        setLayout(new BorderLayout(10, 10));

        inicializarPanelSuperior();
        inicializarPanelLista();
        inicializarPanelBotones();

        cargarTorneos();
    }

    /**
     * Inicializa el panel superior con los filtros de búsqueda.
     */
    private void inicializarPanelSuperior() {
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setOpaque(false);

        filtroEstadoCombo = BotonBuilder.crearComboBox(new String[]{"Todos", "Por empezar", "Empezados"});
        filtroDisciplinaCombo = BotonBuilder.crearComboBox(getDisciplinas());
        filtroFormatoCombo = BotonBuilder.crearComboBox(getFormatos());

        filtroEstadoCombo.addActionListener(e -> cargarTorneos());
        filtroDisciplinaCombo.addActionListener(e -> cargarTorneos());
        filtroFormatoCombo.addActionListener(e -> cargarTorneos());

        JLabel lblEstado = new JLabel("Estado:");
        JLabel lblDisciplina = new JLabel("Disciplina:");
        JLabel lblFormato = new JLabel("Formato:");

        lblEstado.setForeground(Color.WHITE);
        lblDisciplina.setForeground(Color.WHITE);
        lblFormato.setForeground(Color.WHITE);

        panelSuperior.add(lblEstado);
        panelSuperior.add(filtroEstadoCombo);
        panelSuperior.add(lblDisciplina);
        panelSuperior.add(filtroDisciplinaCombo);
        panelSuperior.add(lblFormato);
        panelSuperior.add(filtroFormatoCombo);

        add(panelSuperior, BorderLayout.NORTH);
    }


    /**
     * Inicializa el panel central donde se mostrarán los torneos filtrados.
     */
    private void inicializarPanelLista() {
        panelLista = new JPanel();
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));
        panelLista.setOpaque(false);

        scrollPane = new JScrollPane(panelLista);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Inicializa el panel inferior con el botón de volver.
     */
    private void inicializarPanelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setOpaque(false);

        JButton btnVolver = BotonBuilder.crearBotonVolver(frame, new PanelPrincipal(frame));
        panelBotones.add(btnVolver);

        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Carga y muestra los torneos disponibles aplicando los filtros seleccionados.
     */
    private void cargarTorneos() {
        panelLista.removeAll();

        List<Torneo<?>> torneos = GestorTorneos.obtenerTorneos();

        String filtroEstado = (String) filtroEstadoCombo.getSelectedItem();
        String filtroDisciplina = (String) filtroDisciplinaCombo.getSelectedItem();
        String filtroFormato = (String) filtroFormatoCombo.getSelectedItem();


        if (filtroEstado != null) {
            switch (filtroEstado) {
                case "Por empezar" ->
                        torneos = torneos.stream().filter(t -> !t.isActivo()).collect(Collectors.toList());
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

            String estado = torneo.isActivo() ? "Activo" : "Por empezar";
            String htmlTexto = "<html>" +
                    "<div style='font-family:Segoe UI; color:white;'>" +
                    "<b style='font-size:14pt; color:#00bfff;'>" + torneo.getNombre() + "</b><br>" +
                    "<span style='font-size:11pt;'>Disciplina: <b>" + torneo.getDisciplina().getNombre() + "</b></span><br>" +
                    "<span>Formato: <b>" + torneo.getFormato().name() + "</b></span><br>" +
                    "<span>Estado: <b style='color:" + (torneo.isActivo() ? "#4CAF50" : "#f44336") + ";'>" + estado + "</b></span><br>" +
                    "</div>" +
                    "</html>";

            JLabel etiqueta = new JLabel(htmlTexto);

            JButton btnVer = BotonBuilder.crearBoton("Ver", new Color(0, 153, 204),
                    () -> new CambiarPanelCommand(frame, new PanelDetalleTorneo(frame, torneo)).execute());

            panelTorneo.add(etiqueta, BorderLayout.CENTER);
            panelTorneo.add(btnVer, BorderLayout.EAST);
            panelTorneo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

            panelLista.add(panelTorneo);
        }
    }

    /**
     * Obtiene un arreglo de todas las disciplinas disponibles para el filtro.
     * @return un arreglo de nombres de disciplinas (deportes y videojuegos).
     */
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

    /**
     * Obtiene un arreglo de todos los formatos disponibles para el filtro.
     * @return un arreglo con "Todos los formatos" seguido de los valores de {@link Formato}.
     */
    private String[] getFormatos() {
        String[] valores = new String[Formato.values().length + 1];
        valores[0] = "Todos los formatos";
        for (int i = 0; i < Formato.values().length; i++) {
            valores[i + 1] = Formato.values()[i].name();
        }
        return valores;
    }
}
