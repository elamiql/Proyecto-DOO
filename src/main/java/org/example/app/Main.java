package org.example.app;

import org.example.enums.Deporte;
import org.example.enums.Formato;
import org.example.model.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Crear algunos jugadores para los equipos
        Jugador j1 = new Jugador("Juan Pérez", "10");
        Jugador j2 = new Jugador("Carlos Díaz", "11");
        Jugador j3 = new Jugador("Pedro Gómez", "9");
        Jugador j4 = new Jugador("Luis Martínez", "7");

        // Crear equipos con jugadores
        ArrayList<Jugador> jugadoresEquipo1 = new ArrayList<>();
        jugadoresEquipo1.add(j1);

        ArrayList<Jugador> jugadoresEquipo2 = new ArrayList<>();
        jugadoresEquipo2.add(j2);

        ArrayList<Jugador> jugadoresEquipo3 = new ArrayList<>();
        jugadoresEquipo3.add(j3);

        ArrayList<Jugador> jugadoresEquipo4 = new ArrayList<>();
        jugadoresEquipo4.add(j4);

        Equipo equipo1 = new Equipo("Colo Colo", "001", jugadoresEquipo1);
        Equipo equipo2 = new Equipo("Universidad de Chile", "002", jugadoresEquipo2);
        Equipo equipo3 = new Equipo("Universidad Católica", "003", jugadoresEquipo3);
        Equipo equipo4 = new Equipo("Audax Italiano", "004", jugadoresEquipo4);

        // Crear lista de equipos
        ArrayList<Equipo> equiposLiga = new ArrayList<>();
        equiposLiga.add(equipo1);
        equiposLiga.add(equipo2);
        equiposLiga.add(equipo3);
        equiposLiga.add(equipo4);

        // Crear torneo de equipos
        TorneoEquipo torneoLiga = new TorneoEquipo(
                "Liga Chilena 2025",
                Deporte.FUTBOL,
                "01-07-2025 20:00",
                Formato.LIGA
        );

        // Agregar equipos al torneo
        for (Equipo e : equiposLiga) {
            torneoLiga.addParticipante(e);
        }

        // Generar calendario
        torneoLiga.generarCalendario();

        // Si quieres, imprime los participantes
        System.out.println("\nEquipos inscritos:");
        for (Equipo e : torneoLiga.getEquipos()) {
            System.out.println("- " + e.getNombre());
        }
    }
}
