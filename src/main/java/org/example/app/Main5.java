package org.example.app;

import org.example.enums.Deporte;
import org.example.enums.Formato;
import org.example.gui.Ventana;
import org.example.model.*;

import javax.swing.*;
import java.util.*;

public class Main5 {
    public static void main(String[] args) {
        // Crear jugadores de ejemplo para equipos de prueba
        Jugador j1 = new Jugador("Alice", "001");
        Jugador j2 = new Jugador("Bob", "002");
        Jugador j3 = new Jugador("Charlie", "003");
        Jugador j4 = new Jugador("Dana", "004");
        Jugador j5 = new Jugador("Eve", "005");
        Jugador j6 = new Jugador("Frank", "006");
        Jugador j7 = new Jugador("Grace", "007");
        Jugador j8 = new Jugador("Henry", "008");

        // Crear equipos de prueba
        Equipo equipoA = new Equipo("Equipo A", "A01", new ArrayList<>(List.of(j1, j2)));
        Equipo equipoB = new Equipo("Equipo B", "B01", new ArrayList<>(List.of(j3, j4)));
        Equipo equipoC = new Equipo("Equipo C", "C01", new ArrayList<>(List.of(j5, j6)));
        Equipo equipoD = new Equipo("Equipo D", "D01", new ArrayList<>(List.of(j7, j8)));

        // Crear torneo eliminatoria
        TorneoEquipo torneoTest = new TorneoEquipo(
                "Torneo Test",
                Deporte.FUTBOL,
                "01-06-2025 10:00",
                Formato.ELIMINATORIA,
                "admin123"
        );

        // Inscribir equipos
        equipoA.inscribirse(torneoTest);
        equipoB.inscribirse(torneoTest);
        equipoC.inscribirse(torneoTest);
        equipoD.inscribirse(torneoTest);

        // Crear lista de equipos para torneo Champions
        ArrayList<Equipo> equiposLiga = new ArrayList<>(Arrays.asList(
                new Equipo("PSG", "001", new ArrayList<>()),
                new Equipo("Liverpool", "002", new ArrayList<>()),
                new Equipo("Club Brujas", "018", new ArrayList<>()),
                new Equipo("Aston Villa", "012", new ArrayList<>()),
                new Equipo("Real Madrid", "003", new ArrayList<>()),
                new Equipo("Atletico Madrid", "005", new ArrayList<>()),
                new Equipo("PSV", "009", new ArrayList<>()),
                new Equipo("Arsenal", "014", new ArrayList<>()),
                new Equipo("Benfica", "010", new ArrayList<>()),
                new Equipo("Barcelona", "004", new ArrayList<>()),
                new Equipo("Borussia Dortmund", "017", new ArrayList<>()),
                new Equipo("Lille", "016", new ArrayList<>()),
                new Equipo("Bayern Munich", "006", new ArrayList<>()),
                new Equipo("Leverkusen", "011", new ArrayList<>()),
                new Equipo("Feyenoord", "008", new ArrayList<>()),
                new Equipo("Inter de Milan", "007", new ArrayList<>())
        ));

        // Crear torneo Champions
        TorneoEquipo torneoChampions = new TorneoEquipo(
                "Champions 2025",
                Deporte.FUTBOL,
                "01-07-2025 20:00",
                Formato.ELIMINATORIA,
                "admin123"
        );

        // Inscribir equipos al torneo Champions
        for (Equipo equipo : equiposLiga) {
            torneoChampions.addParticipante(equipo);
        }

        // Generar calendario eliminatoria para Champions
        GenerarCalendario<Equipo> generador = new Eliminatoria<>(torneoChampions.getParticipantes(), true);
        generador.generarCalendario();

        // Asignar enfrentamientos generados al torneoChampions (si tu clase TorneoEquipo tiene este método)
        torneoChampions.setEnfrentamientos(generador.getEnfrentamientos());

        // Agregar torneos al gestor
        GestorTorneos.agregarTorneo(torneoTest);
        GestorTorneos.agregarTorneo(torneoChampions);

        // Mostrar datos para verificar
        System.out.println("Torneo Test - Número de participantes: " + torneoTest.getParticipantes().size());
        System.out.println("Torneo Champions - Número de participantes: " + torneoChampions.getParticipantes().size());

        // Lanzar ventana GUI
        SwingUtilities.invokeLater(() -> new Ventana());
    }
}
