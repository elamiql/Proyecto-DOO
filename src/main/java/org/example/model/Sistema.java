package org.example.model;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.ArrayList;

public class Sistema {

    public Sistema() {
    }

    public static void iniciarTorneo(Torneo torneo) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime fechaTorneo = torneo.getFecha();

        if (!torneo.isActivo() && (ahora.isEqual(fechaTorneo) || ahora.isAfter(fechaTorneo))) {
            torneo.setActivo(true);
            System.out.println("El torneo " + torneo.getNombre() + " ha comenzado.");
        } else if (torneo.isActivo()) {
            System.out.println("El torneo ya está activo.");
        } else {
            System.out.println("El torneo todavía no puede iniciar. Fecha programada: " + fechaTorneo);
        }
    }

    public static void GenerarBrackets(Torneo torneo) {
        ArrayList<Jugador> jugadores = torneo.getParticipantes();

        // Verificar si hay jugadores suficientes
        if (jugadores == null || jugadores.size() < 2) {
            System.out.println("No hay suficientes jugadores para generar brackets.");
            return;
        }

        // Mezclar aleatoriamente la lista
        Collections.shuffle(jugadores);

        System.out.println("Brackets generados aleatoriamente:");

        for (int i = 0; i < jugadores.size() - 1; i += 2) {
            Jugador j1 = jugadores.get(i);
            Jugador j2 = jugadores.get(i + 1);
            System.out.println(j1.getNombre() + " vs " + j2.getNombre());
        }

        // Si hay un número impar, último jugador pasa automáticamente
        if (jugadores.size() % 2 != 0) {
            Jugador libre = jugadores.get(jugadores.size() - 1);
            System.out.println(libre.getNombre() + " pasa automáticamente a la siguiente ronda.");
        }
    }
}

