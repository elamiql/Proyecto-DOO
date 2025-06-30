package org.example.app;

import org.example.enums.Deporte;
import org.example.enums.Formato;
import org.example.enums.Videojuegos;
import org.example.gui.Ventana;
import org.example.model.*;

import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Main5 {
    public static void main(String[] args) {
        // Crear torneos de prueba
        TorneoIndividual torneo1 = new TorneoIndividual(
                "Copa América",
                Deporte.FUTBOL,
                "29-06-2025 20:09",
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
        // Crear algunos jugadores para el equipo
        Jugador jugador1 = new Jugador("Juan", "10");
        Jugador jugador2 = new Jugador("Pedro", "11");

// Crear lista de jugadores
        ArrayList<Jugador> listaJugadores = new ArrayList<>();
        listaJugadores.add(jugador1);
        listaJugadores.add(jugador2);

// Crear un equipo con nombre y número
        Equipo equipo = new Equipo("Los Invencibles", "99", listaJugadores);

// Mostrar cantidad antes de inscribir
        System.out.println("Antes de inscribir equipo: " + torneo2.getParticipantes().size());

// Inscribir equipo en torneo de equipos
        equipo.inscribirse(torneo2);

        System.out.println("Después de inscribir equipo: " + torneo2.getParticipantes().size());
        SwingUtilities.invokeLater(() -> new Ventana());
    }
}

