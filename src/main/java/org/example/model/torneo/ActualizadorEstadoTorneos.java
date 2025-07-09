package org.example.model.torneo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Clase encargada de actualizar periódicamente el estado de los torneos registrados.
 * Cuando un torneo ha alcanzado su fecha de inicio y tiene suficientes participantes,
 * se activa automáticamente y genera su calendario.
 */
public final class ActualizadorEstadoTorneos {

    /** Temporizador que ejecuta la actualización periódica */
    private Timer timer;

    /**
     * Constructor que configura el temporizador con el intervalo especificado.
     * @param intervaloMilisegundos Intervalo de tiempo en milisegundos entre cada actualización.
     */
    public ActualizadorEstadoTorneos(int intervaloMilisegundos) {
        timer = new Timer(intervaloMilisegundos, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarEstados();
            }
        });
    }

    /**
     * Inicia el temporizador para comenzar la actualización periódica de los estados de los torneos.
     */
    public void iniciar() {
        timer.start();
    }

    /**
     * Detiene el temporizador y, por ende, la actualización automática de los torneos.
     */
    public void detener() {
        timer.stop();
    }

    /**
     * Recorre todos los torneos gestionados por {@link GestorTorneos}
     * y actualiza su estado verificando si deben ser activados.
     */
    private void actualizarEstados() {
        for (Torneo<?> torneo : GestorTorneos.obtenerTorneos()) {
            torneo.actualizarEstado();
        }
    }
}
