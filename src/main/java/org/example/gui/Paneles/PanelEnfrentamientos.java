package org.example.gui.Paneles;


import org.example.command.CambiarPanelCommand;
import org.example.enums.Formato;
import org.example.gui.Otros.BotonBuilder;
import org.example.gui.Otros.Imagen;
import org.example.interfaces.Resultado;
import org.example.model.Apuesta;
import org.example.model.Enfrentamientos.Enfrentamiento;
import org.example.model.Enfrentamientos.GenerarCalendario;

import org.example.model.Formatos.Eliminatoria;
import org.example.model.Formatos.Liga;
import org.example.model.Participante.Equipo;
import org.example.model.Participante.Jugador;
import org.example.model.Participante.Participante;

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
public class PanelEnfrentamientos extends PanelFondo {

    private JLabel labelPuntos;
    private final JFrame frame;
    private final Torneo<?> torneo;
    private final JPanel panelCentral;
    private final List<Apuesta> apuestas = new java.util.ArrayList<>();
    private static int puntosUsuario = 1000;


    /**
     * Crea el panel de enfrentamientos para el torneo dado.
     *
     * @param frame  la ventana principal donde se inserta el panel.
     * @param torneo el torneo del cual se visualizarán los enfrentamientos.
     */
    public PanelEnfrentamientos(JFrame frame, Torneo<?> torneo) {
        super(Imagen.cargarImagen("/Fondos/Fondo2.jpg"));
        this.frame = frame;
        this.torneo = torneo;
        setLayout(new BorderLayout(10, 10));

        panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setOpaque(false);


        inicializarPanelCentral();
        inicializarPanelAbajo();
        inicializarPanelSuperior();
    }


    /**
     * Inicializa el panel central donde se mostrarán los enfrentamientos.
     */
    private void inicializarPanelCentral() {
        cargarEnfrentamientos();

        JScrollPane scroll = new JScrollPane(panelCentral);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
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
     *
     * @param e   el enfrentamiento a mostrar.
     * @param num el número del enfrentamiento.
     * @return un {@link JPanel} con los datos del enfrentamiento y un botón de acción.
     */
    private JPanel crearPanelEnfrentamiento(Enfrentamiento e, int num) {
        JPanel panelEnfrentamiento = new JPanel(new GridBagLayout());
        panelEnfrentamiento.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelEnfrentamiento.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        String baseTexto = "Partido " + num + ": " +
                e.getParticipante1().getNombre() + " vs " +
                e.getParticipante2().getNombre();

        JLabel lbl = new JLabel(baseTexto);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.PLAIN, 16));

        panelEnfrentamiento.add(lbl, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        panelBotones.setOpaque(false);

        JButton btnGanador = new JButton("Seleccionar Ganador");
        JButton btnApostar = new JButton("Apostar");

        panelBotones.add(btnGanador);
        panelBotones.add(btnApostar);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;

        panelEnfrentamiento.add(panelBotones, gbc);

        if (e.isFinalizado()) {
            if (e.getGanador() != null) {
                lbl.setText(baseTexto + " - Ganador: " + obtenerNombreGanador(e.getGanador()));
            } else {
                lbl.setText(baseTexto + " - Resultado: Empate");
            }
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
     *
     * @param e el enfrentamiento sobre el cual se va a registrar el ganador.
     */
    private void seleccionarGanador(Enfrentamiento e) {
        String[] opciones = {
                e.getParticipante1().getNombre(),
                e.getParticipante2().getNombre(), "empate"
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
            Participante ganador = null;
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
            boolean exito = registrarEstadisticas(e, ganador);
            if (!exito) return;

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
                    GenerarCalendario<?> generador = torneo.getGeneradorActivo();
                    if (torneo.getFormato() == Formato.LIGA) {
                        if (generador instanceof Liga<?> liga) {
                            liga.actualizarEstadisticasDesdeResultados();
                        }
                    } else {
                        if (generador instanceof Eliminatoria<?, ?, ?> eliminatoria) {
                            eliminatoria.actualizarEstadisticasDesdeResultados();

                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Contraseña incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Registra al ganador en el enfrentamiento y actualiza enfrentamientos futuros si corresponde.
     *
     * @param e            el enfrentamiento a actualizar.
     * @param seleccionado el nombre del participante ganador.
     */
    private void registrarGanador(Enfrentamiento e, String seleccionado) {
        Participante ganador = null;

        if (seleccionado.equals("empate")) {
            e.setGanador(null); // Registrar como empate
            JOptionPane.showMessageDialog(frame, "Se registró un empate.");
        } else {
            ganador = seleccionado.equals(e.getParticipante1().getNombre())
                    ? e.getParticipante1()
                    : e.getParticipante2();

            e.setGanador(ganador); // Registrar al ganador normalmente

            // Crear el patrón para buscar en enfrentamientos posteriores
            String patron = "Ganador " + e.getParticipante1().getNombre() + " vs " + e.getParticipante2().getNombre();

            // CAMBIO PRINCIPAL: Pasar el participante ganador real en lugar del nombre limpio
            actualizarEnfrentamientosPosteriores(patron, ganador);

            String nombreGanadorLimpio = limpiarNombreGanador(ganador.getNombre());
            JOptionPane.showMessageDialog(frame, "Ganador registrado: " + nombreGanadorLimpio);
        }

        // Resolver apuestas si corresponde
        for (Apuesta apuesta : apuestas) {
            if (apuesta.getEnfrentamiento().equals(e)) {
                int ganancia = apuesta.resolver(ganador); // puede ser null si fue empate
                puntosUsuario += ganancia;
                JOptionPane.showMessageDialog(frame, "Resultado de la apuesta: " + ganancia + " puntos");
                break;
            }
        }

        new CambiarPanelCommand(frame, new PanelEnfrentamientos(frame, torneo)).execute();
    }


    private boolean registrarEstadisticas(Enfrentamiento e, Participante p) {
        if (torneo.getFormato() == LIGA) {
            return registrarEstadisticas2(e);
        } else {
            return registrarEstadisticas1(e, p);
        }
    }

    private boolean registrarEstadisticas1(Enfrentamiento e, Participante ganador) {
        try {
            String disciplina = torneo.getDisciplina().getNombre();

            Participante p1 = e.getParticipante1();
            Participante p2 = e.getParticipante2();
            Resultado resultado;

            switch (disciplina) {
                case "FUTBOL" -> {
                    int goles1 = Integer.parseInt(JOptionPane.showInputDialog("Goles de " + p1.getNombre()));
                    int goles2 = Integer.parseInt(JOptionPane.showInputDialog("Goles de " + p2.getNombre()));
                    resultado = new ResultadoFutbol(p1, p2, goles1, goles2);
                    if (!resultado.esValido()) {
                        JOptionPane.showMessageDialog(null, "Goles inválidos.");
                        return false;
                    }
                }

                case "AJEDREZ" -> {
                    if (ganador == null) {
                        resultado = new ResultadoAjedrez(p1, p2, 0.5, 0.5);
                    } else if (ganador.equals(p1)) {
                        resultado = new ResultadoAjedrez(p1, p2, 1.0, 0.0);
                    } else {
                        resultado = new ResultadoAjedrez(p1, p2, 0.0, 1.0);
                    }
                }

                case "TENIS" -> {
                    int maxSets = 5;
                    ResultadoTenis r = new ResultadoTenis(p1, p2, maxSets);
                    int setsP1 = 0, setsP2 = 0;

                    for (int i = 0; i < maxSets; i++) {
                        int j1 = Integer.parseInt(JOptionPane.showInputDialog("Juegos " + p1.getNombre() + " en set " + (i + 1)));
                        int j2 = Integer.parseInt(JOptionPane.showInputDialog("Juegos " + p2.getNombre() + " en set " + (i + 1)));
                        r.agregarSet(i, j1, j2);
                        if (j1 > j2) setsP1++;
                        else if (j2 > j1) setsP2++;

                        if (setsP1 > maxSets / 2 || setsP2 > maxSets / 2) break;
                    }

                    if (!r.esValido()) {
                        JOptionPane.showMessageDialog(null, "Resultado de tenis inválido.");
                        return false;
                    }

                    resultado = r;
                }

                case "TENIS_DE_MESA" -> {
                    int maxSets = 5;
                    ResultadoTenisDeMesa r = new ResultadoTenisDeMesa(p1, p2, maxSets);
                    int setsP1 = 0, setsP2 = 0;

                    for (int i = 0; i < maxSets; i++) {
                        int p1Set = Integer.parseInt(JOptionPane.showInputDialog("Puntos de " + p1.getNombre() + " en set " + (i + 1)));
                        int p2Set = Integer.parseInt(JOptionPane.showInputDialog("Puntos de " + p2.getNombre() + " en set " + (i + 1)));

                        if (!r.esSetValido(p1Set, p2Set)) {
                            JOptionPane.showMessageDialog(null, "Set inválido.");
                            return false;
                        }

                        r.agregarSet(i, p1Set, p2Set);
                        if (p1Set > p2Set) setsP1++;
                        else if (p2Set > p1Set) setsP2++;

                        if (setsP1 > maxSets / 2 || setsP2 > maxSets / 2) break;
                    }

                    if (!r.esValido()) {
                        JOptionPane.showMessageDialog(null, "Resultado de Tenis de Mesa inválido.");
                        return false;
                    }

                    resultado = r;
                }

                case "LOL" -> {
                    int kills1 = Integer.parseInt(JOptionPane.showInputDialog("Kills de " + p1.getNombre()));
                    int kills2 = Integer.parseInt(JOptionPane.showInputDialog("Kills de " + p2.getNombre()));
                    int torres1 = Integer.parseInt(JOptionPane.showInputDialog("Torres destruidas por " + p1.getNombre()));
                    int torres2 = Integer.parseInt(JOptionPane.showInputDialog("Torres destruidas por " + p2.getNombre()));
                    int dragones1 = Integer.parseInt(JOptionPane.showInputDialog("Dragones de " + p1.getNombre()));
                    int dragones2 = Integer.parseInt(JOptionPane.showInputDialog("Dragones de " + p2.getNombre()));
                    int barones1 = Integer.parseInt(JOptionPane.showInputDialog("Barones de " + p1.getNombre()));
                    int barones2 = Integer.parseInt(JOptionPane.showInputDialog("Barones de " + p2.getNombre()));

                    ResultadoLol r = new ResultadoLol(p1, p2);
                    r.registrarEstadisticas(kills1, kills2, torres1, torres2, dragones1, dragones2, barones1, barones2, ganador);
                    resultado = r;
                }

                default -> {
                    JOptionPane.showMessageDialog(null, "Disciplina no reconocida.");
                    return false;
                }
            }

            e.registrarResultado(resultado);

            System.out.println(e.getResultado());
            return true;

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor ingresa solo números.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar estadísticas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }


    private boolean registrarEstadisticas2(Enfrentamiento e) {
        try {
            Participante p1 = e.getParticipante1();
            Participante p2 = e.getParticipante2();

            String input1 = JOptionPane.showInputDialog(frame, "Puntos realizados por " + p1.getNombre());
            String input2 = JOptionPane.showInputDialog(frame, "Puntos realizados por " + p2.getNombre());

            if (input1 == null || input2 == null) {
                JOptionPane.showMessageDialog(frame, "Ingreso cancelado.");
                return false;
            }

            int puntos1 = Integer.parseInt(input1);
            int puntos2 = Integer.parseInt(input2);

            ResultadoFutbol resultado = new ResultadoFutbol(p1, p2, puntos1, puntos2);
            e.registrarResultado(resultado);
            return true;

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Entrada inválida. Asegúrate de ingresar números enteros.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error inesperado al registrar estadísticas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }



    /**
     * Actualiza los nombres de los enfrentamientos futuros que dependen del resultado actual.
     * @param patron el texto identificador del enfrentamiento original.
     *
     */
    private void actualizarEnfrentamientosPosteriores(String patron, Participante ganadorReal) {
        List<Enfrentamiento> enfrentamientos = torneo.getEnfrentamientos();


        for (Enfrentamiento enf : enfrentamientos) {
            boolean actualizado = false;


            if (enf.getParticipante1() != null) {
                String nombreActual1 = enf.getParticipante1().getNombre();
                if (nombreActual1.equals(patron) || nombreActual1.contains(patron)) {
                    System.out.println("Reemplazando participante 1: " + nombreActual1 + " -> " + ganadorReal.getNombre());
                    enf.setParticipante1(ganadorReal);
                    actualizado = true;
                }
            }

            if (enf.getParticipante2() != null) {
                String nombreActual2 = enf.getParticipante2().getNombre();
                if (nombreActual2.equals(patron) || nombreActual2.contains(patron)) {
                    System.out.println("Reemplazando participante 2: " + nombreActual2 + " -> " + ganadorReal.getNombre());
                    enf.setParticipante2(ganadorReal);
                    actualizado = true;
                }
            }

            if (actualizado) {
                System.out.println("Enfrentamiento actualizado: " + enf.getParticipante1().getNombre() + " vs " + enf.getParticipante2().getNombre());
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
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setOpaque(false);


        JLabel titulo = new JLabel("Enfrentamientos del Torneo", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(new Color(0x2E86C1));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelSuperior.add(titulo);

        JPanel barraPuntos = new JPanel(new FlowLayout(FlowLayout.LEFT));
        barraPuntos.setOpaque(false);

        labelPuntos = new JLabel("Puntos: " + puntosUsuario);
        labelPuntos.setForeground(Color.WHITE);
        barraPuntos.add(labelPuntos);

        panelSuperior.add(barraPuntos);

        add(panelSuperior, BorderLayout.NORTH);
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