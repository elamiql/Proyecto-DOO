package org.example;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        Torneo torneo = new Torneo("Los insanos cup", "pelota", jugadores, "10-03-2025 13:14", "brackets");

        torneo.addparticipante(new Jugador("Pepe", "98"));
        torneo.addparticipante(new Jugador("Ana", "32"));
        torneo.addparticipante(new Jugador("Luis", "77"));

        Sistema.iniciarTorneo(torneo);
        Sistema.GenerarBrackets(torneo);
    }
}
