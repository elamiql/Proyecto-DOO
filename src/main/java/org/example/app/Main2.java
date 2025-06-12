package org.example.app;

import org.example.enums.*;
import org.example.model.*;
import java.util.*;


public class Main2 {
    public static void main(String[] args) {
        // Crear lista con los 16 equipos de la liga chilena
        ArrayList<Equipo> equiposLiga = new ArrayList<>() {{
            add(new Equipo("Barcelona", "001", new ArrayList<>()));
            add(new Equipo("Liverpool", "002", new ArrayList<>()));
            add(new Equipo("Inter de milan", "018", new ArrayList<>()));
            add(new Equipo("Borussia Dortmund", "012", new ArrayList<>()));
            add(new Equipo("Bayern Munich", "003", new ArrayList<>()));
            add(new Equipo("Club brujas", "005", new ArrayList<>()));
            add(new Equipo("Paris Saint Germain", "009", new ArrayList<>()));
            add(new Equipo("Aston Villa", "014", new ArrayList<>()));
            add(new Equipo("Real Madrid", "010", new ArrayList<>()));
            add(new Equipo("Atletico Madrid", "004", new ArrayList<>()));
            add(new Equipo("Arsenal", "017", new ArrayList<>()));
            add(new Equipo("Lille", "016", new ArrayList<>()));
            add(new Equipo("PSV", "006", new ArrayList<>()));
            add(new Equipo("Feyenoord", "011", new ArrayList<>()));
            add(new Equipo("Leverkusen", "008", new ArrayList<>()));
            add(new Equipo("Benfica", "007", new ArrayList<>()));
        }};

        // Crear torneo con formato ELIMINATORIA
        TorneoEquipo torneo = new TorneoEquipo(
                "Champions 2025",
                Deporte.FUTBOL,
                "01-07-2025 20:00",
                Formato.ELIMINATORIA
        );

        // Inscribir equipos
        for (Equipo equipo : equiposLiga) {
            torneo.addParticipante(equipo);
        }

        GenerarCalendario<Equipo> generador = new GenerarCalendario<>(torneo.getParticipantes(), torneo.getFormato());

        // Generar el bracket
        List<List<Enfrentamiento>> bracket = generador.generarBracket(equiposLiga);

        // Imprimir el bracket completo
        generador.crearYGuardarBracket();
        generador.imprimirBracket();

        // Simulación simple: elegir aleatoriamente ganador de cada partido en la primera ronda
        Random rnd = new Random();
        List<Enfrentamiento> rondaActual = bracket.get(0);
        ArrayList<Equipo> ganadoresPrimeraRonda = new ArrayList<>();
        System.out.println("=== Simulación: Ganadores primera ronda ===");
        int partidoNum = 1;
        for (Enfrentamiento partido : rondaActual) {
            Equipo p1 = (Equipo) partido.getParticipante1();
            Equipo p2 = (Equipo) partido.getParticipante2();

            // Si hay bye, pasa automáticamente
            if (p2 == null) {
                System.out.println("Partido " + partidoNum++ + ": " + p1.getNombre() + " pasa automáticamente (bye)");
                ganadoresPrimeraRonda.add(p1);
                continue;
            }

            // Elegir aleatoriamente ganador
            Equipo ganador = rnd.nextBoolean() ? p1 : p2;
            System.out.println("Partido " + partidoNum++ + ": " + p1.getNombre() + " vs " + p2.getNombre() +
                    " --> Ganador: " + ganador.getNombre());
            ganadoresPrimeraRonda.add(ganador);
        }
        // Aquí podrías continuar simulando rondas siguientes usando ganadoresPrimeraRonda y así sucesivamente...

    }
}
