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

/**
 * {@code PanelEstadisticas} es un panel gráfico que permite visualizar las estadísticas
 * de los participantes en un torneo, ya sea en formato de liga o eliminación directa.
 * <p>
 * Muestra las estadísticas agrupadas (tabla de posiciones) para torneos de tipo liga,
 * o estadísticas individuales por participante para formatos eliminatorios.
 * </p>
 * <p>Usa componentes Swing como {@link JComboBox}, {@link JTextArea} y
 * {@link JScrollPane}</p>
 */
public class PanelEstadisticas extends PanelFondo {

    private final JFrame frame;
    private final Torneo torneo;

    private JComboBox<Participante> comboParticipantes;
    private JTextArea areaEstadisticas;
    private JButton botonVer;

    /**
     * Crea un nuevo {@code PanelEstadisticas} asociado a un torneo y al frame principal.
     * @param frame  el {@link JFrame} principal de la aplicación.
     * @param torneo el {@link Torneo} del cual se mostrarán las estadísticas.
     */
    public PanelEstadisticas(JFrame frame, Torneo torneo) {
        super(Imagen.cargarImagen("/Fondos/Fondo2.jpg"));
        this.frame = frame;
        this.torneo = torneo;

        inicializarComponentes();
        configurarLayout();
        configurarEventos();
    }

    /**
     * Inicializa los componentes gráficos del panel, como la lista de participantes,
     * el botón para ver estadísticas y el área de texto.
     */
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

    /**
     * Configura el layout del panel, agregando la lista de participantes, botones y área de estadísticas.
     * Si el torneo es de formato liga, muestra automáticamente la tabla completa.
     */
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

    /**
     * Configura los eventos de la interfaz, en particular la acción del botón "Ver estadísticas"
     * para mostrar información del participante seleccionado.
     */
    private void configurarEventos() {
        botonVer.addActionListener((ActionEvent e) -> {
            Participante seleccionado = (Participante) comboParticipantes.getSelectedItem();
            if (seleccionado != null) {
                mostrarEstadisticas(seleccionado);
            }
        });
    }

    /**
     * Muestra las estadísticas de un participante específico.
     * <p>
     * Si el torneo es de formato liga, se muestran todas las estadísticas ordenadas por puntos.
     * Si el torneo es eliminatorio, se muestra la información específica del participante.
     * </p>
     *
     * @param p el participante seleccionado.
     */
    private void mostrarEstadisticas(Participante p) {
        if (torneo.getFormato() == LIGA) {
            mostrarEstadisticasLiga();
        } else {
            mostrarEstadisticasEliminatoria(p);
        }
    }

    /**
     * Muestra la tabla de posiciones y estadísticas acumuladas para torneos en formato liga.
     * <p>
     * Los participantes se ordenan por puntos obtenidos y se despliega la información completa
     * usando el metodo {@code toTablaString()} de {@link EstadisticasFutbol}.
     * </p>
     */
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

    /**
     * Muestra las estadísticas individuales de un participante en torneos de eliminación directa.
     * <p>
     * Recupera el objeto de estadísticas correspondiente desde la tabla de estadísticas
     * del generador {@link Eliminatoria}, y lo representa en pantalla según la disciplina.
     * </p>
     *
     * @param p el participante del cual se mostrarán las estadísticas.
     */
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

        resultado.append("📊 Estadísticas de ").append(p.getNombre().toUpperCase()).append("\n");
        resultado.append("═══════════════════════════════\n");

        switch (disciplina) {
            case "FUTBOL" -> {
                EstadisticasFutbol ef = (EstadisticasFutbol) estadistica;
                resultado.append(ef.toTablaString());
            }
            case "FIFA" -> {
                EstadisticasFifa ef = (EstadisticasFifa) estadistica;
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
                resultado.append(" Disciplina no soportada.");
            }
        }

        resultado.append("\n═══════════════════════════════");

        areaEstadisticas.setText(resultado.toString());
    }




}

