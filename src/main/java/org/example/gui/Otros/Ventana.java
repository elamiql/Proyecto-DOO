package org.example.gui.Otros;

import org.example.gui.Paneles.PanelPrincipal;
import org.example.model.torneo.ActualizadorEstadoTorneos;

import javax.swing.*;

/**
 * Clase principal de la interfaz gráfica que representa la ventana del sistema de torneos.
 *
 * <p>Esta ventana configura y lanza la interfaz de usuario inicial cargando el {@link PanelPrincipal}
 * como contenido base, e inicia un hilo de actualización periódica del estado de los torneos mediante
 * la clase {@link ActualizadorEstadoTorneos}.</p>
 * <p>Hereda de {@link JFrame} para construir una ventana estándar de Swing.</p>
 */
public class Ventana extends JFrame {

    /**
     * Crea e inicializa la ventana principal del sistema de torneos.
     *
     * <p>
     * Establece el título de la ventana.
     * </p>
     * Inicia el actualizador periódico de estados de torneos.
     * <p>
     * Configura dimensiones, posición y comportamiento al cerrar.
     * </p>
     * Establece {@link PanelPrincipal} como panel inicial.
     *
     */
    public Ventana(){
        super("Sistema de torneos");

        // Inicia el actualizador que actualiza el estado de los torneos cada 1 segundo.
        ActualizadorEstadoTorneos actualizador = new ActualizadorEstadoTorneos(1_000);
        actualizador.iniciar();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);

        // Carga el panel principal
        setContentPane(new PanelPrincipal(this));
        setVisible(true);
    }
}
