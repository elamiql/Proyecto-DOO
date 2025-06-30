package org.example.model;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ActualizadorEstadoTorneos {

    private Timer timer;

    public ActualizadorEstadoTorneos(int intervaloMilisegundos) {
        timer = new Timer(intervaloMilisegundos, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarEstados();
            }
        });
    }

    public void iniciar() {
        timer.start();
    }

    public void detener() {
        timer.stop();
    }

    private void actualizarEstados() {
        for (Torneo<?> torneo : GestorTorneos.obtenerTorneos()) {
            torneo.actualizarEstado();
        }
    }
}
