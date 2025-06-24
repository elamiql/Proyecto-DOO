package org.example.model;

import java.util.ArrayList;

public class Equipo extends Participante {
    private final ArrayList<Jugador> jugadores;

    // Constructor recibe nombre, numero y lista de jugadores
    public Equipo(String nombre, String numero, ArrayList<Jugador> jugadores) {
        super(nombre, numero);
        this.jugadores = jugadores;
    }

    public void addJugador(Jugador jugador){
        jugadores.add(jugador);
    }

    public void removeJugador(Jugador jugador){
        jugadores.remove(jugador);
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    @Override
    public void inscribirse(Torneo<?> torneo) {
        torneo.addEquipo(this);
    }
}
