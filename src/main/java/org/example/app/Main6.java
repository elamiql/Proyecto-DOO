package org.example.app;

import org.example.enums.Deporte;
import org.example.enums.Formato;
import org.example.interfaces.Resultado;
import org.example.model.*;

import java.text.Normalizer;
import java.util.*;

public class Main6{
    public static void main(String[] args) {
        // Crear jugadores
        Jugador jugador1 = new Jugador("Lionel Messi", "10");
        Jugador jugador2 = new Jugador("Angel Di Maria", "11");
        Jugador jugador3 = new Jugador("Neymar Jr", "10");
        Jugador jugador4 = new Jugador("Casemiro", "5");

        // Crear lista de jugadores para equipo Argentina
        ArrayList<Jugador> jugadoresArgentina = new ArrayList<>();
        jugadoresArgentina.add(jugador1);
        jugadoresArgentina.add(jugador2);

        // Crear lista de jugadores para equipo Brasil
        ArrayList<Jugador> jugadoresBrasil = new ArrayList<>();
        jugadoresBrasil.add(jugador3);
        jugadoresBrasil.add(jugador4);

        // Crear equipos con sus jugadores
        Equipo argentina = new Equipo("Argentina", "001", jugadoresArgentina);
        Equipo brasil = new Equipo("Brasil", "002", jugadoresBrasil);

        // Crear torneo
        TorneoEquipo amistoso = new TorneoEquipo("Partido Amistoso", Deporte.FUTBOL, "18-07-2025 19:00", Formato.ELIMINATORIA, "123");

        // Inscribir equipos en el torneo
        argentina.inscribirse(amistoso);
        brasil.inscribirse(amistoso);

        // Mostrar participantes en el torneo
        System.out.println("Participantes en " + amistoso.getNombre() + ":");
        for (Equipo equipo : amistoso.getParticipantes()) {
            System.out.println("- " + equipo.getNombre());
            System.out.println("  Jugadores:");
            for (Jugador jugador : equipo.getJugadores()) {
                System.out.println("    * " + jugador.getNombre() + " (N° " + jugador.getNumero() + ")");
            }
        }

        System.out.println();

        // Crear resultado del partido: Argentina 2 - 1 Brasil
        ResultadoFutbol resultado = new ResultadoFutbol(argentina, brasil, 2, 1);

        // Crear estadísticas para cada equipo
        EstadisticasFutbol statsArgentina = new EstadisticasFutbol(argentina);
        EstadisticasFutbol statsBrasil = new EstadisticasFutbol(brasil);

        // Registrar resultado en estadísticas
        if (resultado.esValido()) {
            if (resultado.getGanador() == null) {
                statsArgentina.registrarEmpate();
                statsBrasil.registrarEmpate();
            } else if (resultado.getGanador().equals(argentina)) {
                statsArgentina.registrarVictoria();
                statsBrasil.registrarDerrota();
            } else {
                statsBrasil.registrarVictoria();
                statsArgentina.registrarDerrota();
            }

            // Agregar goles a las estadísticas
            //statsArgentina.agregarGoles(resultado.getGolesLocal(), resultado.getGolesVisitante());
            //statsBrasil.agregarGoles(resultado.getGolesVisitante(), resultado.getGolesLocal());

            // Mostrar resumen y estadísticas
            System.out.println("Resumen del partido:");
            System.out.println(resultado.getResumen());
            System.out.println();

            System.out.println("Estadísticas:");
            System.out.println(statsArgentina);
            System.out.println(statsBrasil);
        } else {
            System.out.println("Resultado inválido.");
        }
    }
}