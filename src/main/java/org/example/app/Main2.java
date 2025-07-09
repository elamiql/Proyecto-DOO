package org.example.app;

import org.example.enums.*;
import org.example.model.Enfrentamientos.Enfrentamiento;
import org.example.model.Enfrentamientos.GenerarCalendario;
import org.example.model.Formatos.Eliminatoria;
import org.example.model.Participante.Equipo;
import org.example.model.Participante.Participante;
import org.example.model.torneo.TorneoEquipo;

import java.util.*;

public class Main2 {
    public static void main(String[] args) {

        ArrayList<Equipo> equiposLiga = new ArrayList<>() {{
            add(new Equipo("PSG", "001", new ArrayList<>()));
            add(new Equipo("Liverpool", "002", new ArrayList<>()));
            add(new Equipo("Club Brujas", "018", new ArrayList<>()));
            add(new Equipo("Aston Villa", "012", new ArrayList<>()));
            add(new Equipo("Real Madrid", "003", new ArrayList<>()));
            add(new Equipo("Atletico Madrid", "005", new ArrayList<>()));
            add(new Equipo("PSV", "009", new ArrayList<>()));
            add(new Equipo("Arsenal", "014", new ArrayList<>()));
            add(new Equipo("Benfica", "010", new ArrayList<>()));
            add(new Equipo("Barcelona", "004", new ArrayList<>()));
            add(new Equipo("Borussia Dortmund", "017", new ArrayList<>()));
            add(new Equipo("Lille", "016", new ArrayList<>()));
            add(new Equipo("Bayern Munich", "006", new ArrayList<>()));
            add(new Equipo("Leverkusen", "011", new ArrayList<>()));
            add(new Equipo("Feyenoord", "008", new ArrayList<>()));
            add(new Equipo("Inter de Milan", "007", new ArrayList<>()));
        }};

        TorneoEquipo torneo = new TorneoEquipo(
                "Champions 2025",
                Deporte.FUTBOL,
                "01-07-2025 20:00",
                Formato.ELIMINATORIA,
                "pene"
        );

        for (Equipo equipo : equiposLiga) {
            torneo.addParticipante(equipo);
        }

        GenerarCalendario<Equipo> generador = new Eliminatoria<>(torneo.getParticipantes(), true);
        generador.generarCalendario();

        List<List<Enfrentamiento>> rondas = generador.getRondasEliminatorias();
        Random rnd = new Random();

        System.out.println("=== Simulación del torneo ===");

        for (int i = 0; i < rondas.size(); i++) {
            List<Enfrentamiento> ronda = rondas.get(i);
            System.out.println("\n--- Ronda " + (i + 1) + " ---");
            for (int j = 0; j < ronda.size(); j++) {
                Enfrentamiento partido = ronda.get(j);
                Participante p1 = partido.getParticipante1();
                Participante p2 = partido.getParticipante2();

                // Caso bye
                if (p2 == null) {
                    partido.setGanador(p1);
                    System.out.println("Partido " + (j + 1) + ": " + p1.getNombre() + " pasa automáticamente (bye)");
                } else {
                    Participante ganador = rnd.nextBoolean() ? p1 : p2;
                    partido.setGanador(ganador);
                    System.out.println("Partido " + (j + 1) + ": " + p1.getNombre() + " vs " + p2.getNombre()
                            + " --> Ganador: " + ganador.getNombre());
                }

                // Reemplazar al ganador en la siguiente ronda si existe
                if (i + 1 < rondas.size()) {
                    List<Enfrentamiento> siguienteRonda = rondas.get(i + 1);
                    int indexEnSiguiente = j / 2;
                    Enfrentamiento enfrentamientoSiguiente = siguienteRonda.get(indexEnSiguiente);

                    if (j % 2 == 0) {
                        enfrentamientoSiguiente.setParticipante1(partido.getGanador());
                    } else {
                        enfrentamientoSiguiente.setParticipante2(partido.getGanador());
                    }
                }
            }
        }

        // Mostrar ganador final
        Enfrentamiento finalMatch = rondas.get(rondas.size() - 1).get(0);
        System.out.println("\n=== Campeón del torneo ===");
        System.out.println("🏆 " + finalMatch.getGanador().getNombre() + " 🏆");
    }
}
