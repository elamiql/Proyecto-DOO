package org.example.app;

import org.example.enums.Deporte;
import org.example.enums.Formato;
import org.example.enums.Videojuegos;
import org.example.gui.Ventana;
import org.example.model.GestorTorneos;
import org.example.model.TorneoEquipo;
import org.example.model.TorneoIndividual;

import javax.swing.*;
import java.time.format.DateTimeFormatter;

public class Main5 {
    public static void main(String[] args) {
        // Crear torneos de prueba
        TorneoIndividual torneo1 = new TorneoIndividual(
                "Copa América",
                Deporte.FUTBOL,
                "25-07-2024 18:00",
                Formato.ELIMINATORIA);

        TorneoEquipo torneo2 = new TorneoEquipo(
                "Liga Pro de LOL",
                Videojuegos.LOL,
                "01-08-2025 20:00",
                Formato.LIGA);

        TorneoIndividual torneo3 = new TorneoIndividual(
                "Torneo de Ajedrez",
                Deporte.AJEDREZ,
                "15-08-2025 10:00",
                Formato.GRUPOS_CON_ELIMINATORIA);

        // Agregar torneos a gestor
        GestorTorneos.agregarTorneo(torneo1);
        GestorTorneos.agregarTorneo(torneo2);
        GestorTorneos.agregarTorneo(torneo3);

        SwingUtilities.invokeLater(() -> new Ventana());
    }
}

