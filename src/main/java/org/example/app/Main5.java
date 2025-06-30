package org.example.app;

import org.example.enums.Deporte;
import org.example.enums.Formato;
import org.example.enums.Videojuegos;
import org.example.gui.Ventana;
import org.example.model.*;

import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

        TorneoEquipo torneo4 = new TorneoEquipo("Fifa World Cup Qatar 2022",
                Deporte.FUTBOL,
                "10-07-2026 11:00",
                Formato.GRUPOS_CON_ELIMINATORIA,
                8,
                2);

        // Grupos del Mundial de Qatar 2022
        String[][] gruposQatar = {
                {"Paises Bajos", "Senegal", "Ecuador", "Qatar"},        // Grupo A
                {"Inglaterra", "EEUU", "Irán", "Gales"},                // Grupo B
                {"Argentina", "Polonia", "México", "Arabia Saudita"},   // Grupo C
                {"Francia", "Australia", "Túnez", "Dinamarca"},         // Grupo D
                {"Japón", "España", "Alemania", "Costa Rica"},          // Grupo E
                {"Marruecos", "Croacia", "Bélgica", "Canadá"},          // Grupo F
                {"Brasil", "Suiza", "Camerún", "Serbia"},               // Grupo G
                {"Portugal", "Corea del Sur", "Uruguay", "Ghana"}       // Grupo H
        };

        // Inscribir equipos al Mundial
        int contador = 1;
        for (String[] grupo : gruposQatar) {
            for (String nombre : grupo) {
                // Crear jugadores ficticios (2 por equipo)
                Jugador j1 = new Jugador(nombre + " Jugador 1", String.format("Q%02d", contador++));
                Jugador j2 = new Jugador(nombre + " Jugador 2", String.format("Q%02d", contador++));

                // Crear equipo e inscribirlo
                Equipo equipo = new Equipo(nombre, nombre.substring(0, Math.min(3, nombre.length())).toUpperCase(), new ArrayList<>(List.of(j1, j2)));
                equipo.inscribirse(torneo4);
            }
        }

        // Agregar torneos a gestor
        GestorTorneos.agregarTorneo(torneo1);
        GestorTorneos.agregarTorneo(torneo2);
        GestorTorneos.agregarTorneo(torneo3);
        GestorTorneos.agregarTorneo(torneo4);


        // Crear jugadores
        Jugador j1 = new Jugador("Alice", "001");
        Jugador j2 = new Jugador("Bob", "002");
        Jugador j3 = new Jugador("Charlie", "003");
        Jugador j4 = new Jugador("Dana", "004");
        Jugador j5 = new Jugador("Eve", "005");
        Jugador j6 = new Jugador("Frank", "006");
        Jugador j7 = new Jugador("Grace", "007");
        Jugador j8 = new Jugador("Henry", "008");
        Jugador j9 = new Jugador("Ivy", "009");
        Jugador j10 = new Jugador("Jack", "010");
        Jugador j11 = new Jugador("Karen", "011");
        Jugador j12 = new Jugador("Leo", "012");
        Jugador j13 = new Jugador("Mia", "013");
        Jugador j14 = new Jugador("Nate", "014");
        Jugador j15 = new Jugador("Olivia", "015");
        Jugador j16 = new Jugador("Paul", "016");

// Crear equipos
        Equipo equipoA = new Equipo("Equipo A", "A01", new ArrayList<>(List.of(j1, j2)));
        Equipo equipoB = new Equipo("Equipo B", "B01", new ArrayList<>(List.of(j3, j4)));
        Equipo equipoC = new Equipo("Equipo C", "C01", new ArrayList<>(List.of(j5, j6)));
        Equipo equipoD = new Equipo("Equipo D", "D01", new ArrayList<>(List.of(j7, j8)));
        Equipo equipoE = new Equipo("Equipo E", "E01", new ArrayList<>(List.of(j9, j10)));
        Equipo equipoF = new Equipo("Equipo F", "F01", new ArrayList<>(List.of(j11, j12)));
        Equipo equipoG = new Equipo("Equipo G", "G01", new ArrayList<>(List.of(j13, j14)));
        Equipo equipoH = new Equipo("Equipo H", "H01", new ArrayList<>(List.of(j15, j16)));

// Crear torneo de prueba
        TorneoEquipo torneoTest = new TorneoEquipo(
                "Torneo Test",
                Deporte.FUTBOL,
                "01-06-2025 10:00",
                Formato.GRUPOS_CON_ELIMINATORIA
        );

// Inscribir equipos
        equipoA.inscribirse(torneoTest);
        equipoB.inscribirse(torneoTest);
        equipoC.inscribirse(torneoTest);
        equipoD.inscribirse(torneoTest);
        equipoE.inscribirse(torneoTest);
        equipoF.inscribirse(torneoTest);
        equipoG.inscribirse(torneoTest);
        equipoH.inscribirse(torneoTest);

// Generar calendario
        torneo4.generarCalendario();
        torneo4.setActivo(false);

// Agregar al gestor
        GestorTorneos.agregarTorneo(torneoTest);
        SwingUtilities.invokeLater(() -> new Ventana());
    }
}

