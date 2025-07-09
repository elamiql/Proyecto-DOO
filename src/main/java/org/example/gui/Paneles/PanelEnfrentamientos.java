package org.example.gui.Paneles;

import org.example.command.CambiarPanelCommand;
import org.example.gui.Otros.BotonBuilder;
import org.example.model.*;
import org.example.model.Enfrentamientos.Enfrentamiento;
import org.example.model.Estadisticas.*;
import org.example.model.Participante.Equipo;
import org.example.model.Participante.Jugador;
import org.example.model.Participante.Participante;
import org.example.model.Participante.ParticipantePlaceholder;
import org.example.model.Resultado.*;
import org.example.model.torneo.Torneo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static org.example.enums.Formato.ELIMINATORIA;
import static org.example.enums.Formato.LIGA;

/**
 * Panel gráfico que muestra los enfrentamientos de un torneo y permite seleccionar a los ganadores.
 *
 * <p>Este panel es capaz de listar todos los enfrentamientos generados en un {@link Torneo}
 * y registrar resultados mediante una interfaz gráfica protegida por contraseña.</p>
 */
public class PanelEnfrentamientos extends JPanel {

    private JLabel labelPuntos;
    private final JFrame frame;
    private final Torneo<?> torneo;
    private final JPanel panelCentral;
    private final List<Apuesta> apuestas = new java.util.ArrayList<>();
    private static int puntosUsuario=1000;


    /**
     * Crea el panel de enfrentamientos para el torneo dado.
     * @param frame la ventana principal donde se inserta el panel.
     * @param torneo el torneo del cual se visualizarán los enfrentamientos.
     */
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
        inicializarPanelSuperior();
    }

    /**
     * Inicializa el título del panel.
     */
    private void inicializarTitulo() {
        JLabel titulo = new JLabel("Enfrentamientos del Torneo", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);
    }

    /**
     * Inicializa el panel central donde se mostrarán los enfrentamientos.
     */
    private void inicializarPanelCentral() {
        cargarEnfrentamientos();
        JScrollPane scroll = new JScrollPane(panelCentral);
        add(scroll, BorderLayout.CENTER);
    }

    /**
     * Carga los enfrentamientos desde el torneo y los agrega al panel central.
     */
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

    /**
     * Crea el panel que representa un enfrentamiento.
     * @param e el enfrentamiento a mostrar.
     * @param num el número del enfrentamiento.
     * @return un {@link JPanel} con los datos del enfrentamiento y un botón de acción.
     */
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

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setOpaque(false);

        JButton btnGanador = new JButton("Seleccionar Ganador");
        JButton btnApostar = new JButton("Apostar");

        panelBotones.add(btnGanador);
        panelBotones.add(btnApostar);

        panelEnfrentamiento.add(panelBotones, BorderLayout.EAST);

        if (e.getGanador() != null) {
            lbl.setText(baseTexto + " - Ganador: " + obtenerNombreGanador(e.getGanador()));
            btnGanador.setEnabled(false);
            btnApostar.setEnabled(false);
        } else {
            btnGanador.addActionListener(ae -> seleccionarGanador(e));
            btnApostar.addActionListener(ae -> apostarEnEnfrentamiento(e, lbl));
        }

        return panelEnfrentamiento;
    }


    /**
     * Permite seleccionar al ganador de un enfrentamiento con autenticación mediante contraseña.
     * @param e el enfrentamiento sobre el cual se va a registrar el ganador.
     */
    private void seleccionarGanador(Enfrentamiento e) {
        String[] opciones = {
                e.getParticipante1().getNombre(),
                e.getParticipante2().getNombre(),"empate"
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
            Participante ganador=null;
            if (e.getParticipante1().getNombre().equals(seleccionado)) {
                ganador = e.getParticipante1();
            } else if (e.getParticipante2().getNombre().equals(seleccionado)) {
                ganador = e.getParticipante2();
            }
            if (torneo.getFormato() == ELIMINATORIA) {
                if (ganador == null) {
                    JOptionPane.showMessageDialog(frame,
                            "No se puede elegir empate en eliminatoria", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            registrarEstadisticas(e,ganador);
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

    /**
     * Registra al ganador en el enfrentamiento y actualiza enfrentamientos futuros si corresponde.
     * @param e el enfrentamiento a actualizar.
     * @param seleccionado el nombre del participante ganador.
     */
    private void registrarGanador(Enfrentamiento e, String seleccionado) {
        Participante ganador = seleccionado.equals(e.getParticipante1().getNombre())
                ? e.getParticipante1()
                : e.getParticipante2();

        e.setGanador(ganador);

        String nombreGanadorLimpio = limpiarNombreGanador(ganador.getNombre());
        String patron = "Ganador " + e.getParticipante1().getNombre() + " vs " + e.getParticipante2().getNombre();

        actualizarEnfrentamientosPosteriores(patron, nombreGanadorLimpio);

        JOptionPane.showMessageDialog(frame, "Ganador registrado: " + nombreGanadorLimpio);
        for (Apuesta apuesta : apuestas) {
            if (apuesta.getEnfrentamiento().equals(e)) {
                int ganancia = apuesta.resolver(ganador);
                puntosUsuario += ganancia;
                JOptionPane.showMessageDialog(frame, "Resultado de la apuesta: " + ganancia + " puntos");
                break;
            }
        }

        new CambiarPanelCommand(frame, new PanelEnfrentamientos(frame, torneo)).execute();
    }

    private void registrarEstadisticas(Enfrentamiento e,Participante p) {
        if (torneo.getFormato() == LIGA) {
            registrarEstadisticas2(e,p);
        } else {
            registrarEstadisticas1(e,p);
        }
    }
    private void registrarEstadisticas1(Enfrentamiento e, Participante ganador) {
        try {
            String disciplina = torneo.getDisciplina().getNombre();

            Participante p1 = e.getParticipante1();
            Participante p2 = e.getParticipante2();

            switch (disciplina) {

                case "AJEDREZ" -> {
                    ResultadoAjedrez resultadoAjedrez;
                    if (ganador == null) {
                        resultadoAjedrez = new ResultadoAjedrez(p1, p2, 0.5, 0.5);
                    } else if (ganador.equals(p1)) {
                        resultadoAjedrez = new ResultadoAjedrez(p1, p2, 1.0, 0.0);
                    } else {
                        resultadoAjedrez = new ResultadoAjedrez(p1, p2, 0.0, 1.0);
                    }

                    e.setResultado(resultadoAjedrez);

                    new EstadisticasAjedrez(p1).registrarResultado(resultadoAjedrez, p1, true);
                    new EstadisticasAjedrez(p2).registrarResultado(resultadoAjedrez, p2, false);
                }

                case "TENIS" -> {
                    int maxSets = 5;
                    ResultadoTenis resultadoTenis = new ResultadoTenis(p1, p2, maxSets);

                    int setsJ1 = 0;
                    int setsJ2 = 0;
                    int i = 0;

                    while (i < maxSets && setsJ1 < (maxSets / 2 + 1) && setsJ2 < (maxSets / 2 + 1)) {
                        String inputJ1 = JOptionPane.showInputDialog(frame, "Juegos ganados por " + p1.getNombre() + " en set " + (i + 1));
                        String inputJ2 = JOptionPane.showInputDialog(frame, "Juegos ganados por " + p2.getNombre() + " en set " + (i + 1));

                        if (inputJ1 == null || inputJ2 == null) {
                            JOptionPane.showMessageDialog(frame, "Ingreso cancelado.");
                            return;
                        }

                        int juegosJ1 = Integer.parseInt(inputJ1);
                        int juegosJ2 = Integer.parseInt(inputJ2);

                        resultadoTenis.agregarSet(i, juegosJ1, juegosJ2);

                        if (juegosJ1 > juegosJ2) {
                            setsJ1++;
                        } else if (juegosJ2 > juegosJ1) {
                            setsJ2++;
                        }

                        i++;
                    }

                    if (!resultadoTenis.esValido()) {
                        JOptionPane.showMessageDialog(frame, "Resultado inválido: nadie ganó la mayoría de sets.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    e.setResultado(resultadoTenis);

                    new EstadisticasTenis(p1).registrarResultado(resultadoTenis, p1, true);
                    new EstadisticasTenis(p2).registrarResultado(resultadoTenis, p2, false);

                    if (ganador != null) {
                        JOptionPane.showMessageDialog(frame, "Ganador: " + ganador.getNombre());
                    }
                }


                case "TENIS_DE_MESA" -> {
                    int maxSets = 5;
                    ResultadoTenisDeMesa resultadoTT = new ResultadoTenisDeMesa(p1, p2, maxSets);

                    int setsJ1 = 0;
                    int setsJ2 = 0;
                    int i = 0;

                    while (i < maxSets && setsJ1 < (maxSets/2+1)&& setsJ2 < (maxSets/2+1)) {
                        String inputJ1 = JOptionPane.showInputDialog(frame, "Puntos de " + p1.getNombre() + " en set " + (i + 1));
                        String inputJ2 = JOptionPane.showInputDialog(frame, "Puntos de " + p2.getNombre() + " en set " + (i + 1));

                        if (inputJ1 == null || inputJ2 == null) {
                            JOptionPane.showMessageDialog(frame, "Ingreso cancelado.");
                            return;
                        }

                        int puntosJ1 = Integer.parseInt(inputJ1);
                        int puntosJ2 = Integer.parseInt(inputJ2);
                        boolean p = resultadoTT.esSetValido(puntosJ1, puntosJ2);
                        if(!p){
                            JOptionPane.showMessageDialog(frame, "Set invalido.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        resultadoTT.agregarSet(i, puntosJ1, puntosJ2);

                        if (puntosJ1 > puntosJ2) {
                            setsJ1++;
                        } else if (puntosJ2 > puntosJ1) {
                            setsJ2++;
                        }

                        i++;
                    }

                    if (!resultadoTT.esValido()) {
                        JOptionPane.showMessageDialog(frame, "Resultado inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    e.setResultado(resultadoTT);

                    new EstadisticaTenisDeMesa(p1).registrarResultado(resultadoTT, p1, true);
                    new EstadisticaTenisDeMesa(p2).registrarResultado(resultadoTT, p2, false);
                    if (ganador != null) {
                        JOptionPane.showMessageDialog(frame, "Ganador: " + ganador.getNombre());
                    }
                }

                case "LOL" -> {
                    int kills1 = Integer.parseInt(JOptionPane.showInputDialog(frame, "Kills de " + p1.getNombre()));
                    int kills2 = Integer.parseInt(JOptionPane.showInputDialog(frame, "Kills de " + p2.getNombre()));
                    int torres1 = Integer.parseInt(JOptionPane.showInputDialog(frame, "Torres destruidas por " + p1.getNombre()));
                    int torres2 = Integer.parseInt(JOptionPane.showInputDialog(frame, "Torres destruidas por " + p2.getNombre()));
                    int dragones1 = Integer.parseInt(JOptionPane.showInputDialog(frame, "Dragones asesinados por " + p1.getNombre()));
                    int dragones2 = Integer.parseInt(JOptionPane.showInputDialog(frame, "Dragones asesinados por " + p2.getNombre()));
                    int barones1 = Integer.parseInt(JOptionPane.showInputDialog(frame, "Barones asesinados por " + p1.getNombre()));
                    int barones2 = Integer.parseInt(JOptionPane.showInputDialog(frame, "Barones asesinados por " + p2.getNombre()));

                    ResultadoLol resultadoLol = new ResultadoLol(p1, p2);
                    resultadoLol.registrarEstadisticas(kills1, kills2, torres1, torres2, dragones1, dragones2, barones1, barones2, ganador);

                    e.setResultado(resultadoLol);

                    new EstadisticasLol(p1).registrarResultado(resultadoLol, p1, true);
                    new EstadisticasLol(p2).registrarResultado(resultadoLol, p2, false);
                }

                default -> {
                    int goles1 = Integer.parseInt(JOptionPane.showInputDialog(frame, "Goles de " + p1.getNombre()));
                    int goles2 = Integer.parseInt(JOptionPane.showInputDialog(frame, "Goles de " + p2.getNombre()));

                    ResultadoFutbol resultadoFutbol = new ResultadoFutbol(p1, p2, goles1, goles2);

                    if (!resultadoFutbol.esValido()) {
                        JOptionPane.showMessageDialog(frame, "Los goles deben ser números no negativos.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    e.setResultado(resultadoFutbol);

                    new EstadisticasFutbol(p1).registrarResultado(resultadoFutbol, p1, true);
                    new EstadisticasFutbol(p2).registrarResultado(resultadoFutbol, p2, false);
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error al ingresar estadísticas. Verifica los datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarEstadisticas2(Enfrentamiento e, Participante p) {
        try {
            Participante p1 = e.getParticipante1();
            Participante p2 = e.getParticipante2();

            String input1 = JOptionPane.showInputDialog(frame, "Puntos realizados por " + p1.getNombre());
            String input2 = JOptionPane.showInputDialog(frame, "Puntos realizados por " + p2.getNombre());

            if (input1 == null || input2 == null) {
                JOptionPane.showMessageDialog(frame, "Ingreso cancelado.");
                return;
            }

            int puntos1 = Integer.parseInt(input1);
            int puntos2 = Integer.parseInt(input2);

            // Falta logica para estadisticas liga.

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Entrada inválida. Asegúrate de ingresar números enteros.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error inesperado al registrar estadísticas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    /**
     * Actualiza los nombres de los enfrentamientos futuros que dependen del resultado actual.
     * @param patron el texto identificador del enfrentamiento original.
     * @param nombreGanadorLimpio el nombre que reemplazará al patrón.
     */
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

    /**
     * Limpia el nombre del ganador en caso de que esté compuesto por múltiples niveles de "Ganador ...".
     *
     * @param nombre el nombre a limpiar.
     * @return el nombre limpio sin prefijos de "Ganador ".
     */
    private String limpiarNombreGanador(String nombre) {
        String nombreLimpio = nombre;

        while (nombreLimpio.startsWith("Ganador ")) {
            nombreLimpio = nombreLimpio.substring(8);
        }
        return nombreLimpio;
    }

    /**
     * Devuelve el nombre textual del ganador, según su tipo.
     * @param ganador el objeto ganador (jugador o equipo).
     * @return el nombre representativo.
     */
    private String obtenerNombreGanador(Object ganador) {
        if (ganador instanceof Jugador) {
            return ((Jugador) ganador).getNombre();
        } else if (ganador instanceof Equipo) {
            return ((Equipo) ganador).getNombre();
        }
        return ganador.toString();
    }

    /**
     * Inicializa el panel inferior con el botón para volver al detalle del torneo.
     */
    private void inicializarPanelAbajo() {
        JButton btnVolver = BotonBuilder.crearBotonVolver(frame, new PanelDetalleTorneo(frame,torneo));

        JPanel panelAbajo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAbajo.setOpaque(false);
        panelAbajo.add(btnVolver);
        add(panelAbajo, BorderLayout.SOUTH);
    }

    /**
     * Inicializa el panel superior de la interfaz, que muestra la cantidad
     * actual de puntos del usuario. Este panel se posiciona en la parte superior
     * del componente (BorderLayout.PAGE_START).
     */
    private void inicializarPanelSuperior() {
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setOpaque(false);

        labelPuntos = new JLabel("Puntos: " + puntosUsuario);
        panelSuperior.add(labelPuntos);

        add(panelSuperior, BorderLayout.PAGE_START);
    }

    /**
     * Permite al usuario realizar una apuesta en un enfrentamiento específico.
     *<p>
     * El metodo verifica si el enfrentamiento ya tiene un ganador (en cuyo caso no se puede apostar y si ya se ha realizado
     * una apuesta en el mismo enfrentamiento (no se permite duplicar).
     *</p>
     * Si pasa estas validaciones, se muestra un diálogo para que el usuario seleccione
     * al participante por el que desea apostar y luego ingrese el monto.
     * Si la apuesta es válida, se descuenta el monto de los puntos del usuario,
     * se actualiza la etiqueta correspondiente y se registra la apuesta.
     *
     * @param e el {@link Enfrentamiento} en el que se desea apostar
     * @param lbl un {@link JLabel} asociado al enfrentamiento.
     */
    private void apostarEnEnfrentamiento(Enfrentamiento e, JLabel lbl) {
        if (e.getGanador() != null) {
            JOptionPane.showMessageDialog(frame, "Este enfrentamiento ya tiene un ganador.");
            return;
        }

        // NUEVO: Verificar si ya se apostó
        for (Apuesta a : apuestas) {
            if (a.getEnfrentamiento().equals(e)) {
                JOptionPane.showMessageDialog(frame, "Ya realizaste una apuesta en este enfrentamiento.");
                return;
            }
        }

        String[] opciones = {
                e.getParticipante1().getNombre(),
                e.getParticipante2().getNombre()
        };

        String seleccionado = (String) JOptionPane.showInputDialog(
                frame,
                "¿A quién deseas apostar?",
                "Apuesta",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (seleccionado == null) return;

        String input = JOptionPane.showInputDialog(frame, "¿Cuántos puntos deseas apostar?");
        if (input == null) return;

        try {
            int monto = Integer.parseInt(input);
            if (monto <= 0 || monto > puntosUsuario) {
                JOptionPane.showMessageDialog(frame, "Monto inválido. Tienes " + puntosUsuario + " puntos.");
                return;
            }

            Participante apostado = e.getParticipante1().getNombre().equals(seleccionado)
                    ? e.getParticipante1()
                    : e.getParticipante2();

            Apuesta apuesta = new Apuesta(e, apostado, monto);
            apuestas.add(apuesta);
            puntosUsuario -= monto;
            labelPuntos.setText("Puntos: " + puntosUsuario);

            JOptionPane.showMessageDialog(frame, "Apuesta realizada con éxito.");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Entrada inválida. Debe ser un número.");
        }
    }

}