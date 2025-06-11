package org.example.model;
import java.util.ArrayList;

public class Equipo extends Participante {
    private ArrayList<Jugador> jugadores;

    // Constructor recibe nombre, numero y lista de jugadores
    public Equipo(String nombre, String numero) {
        super(nombre, numero);

    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    @Override
    public void inscribirse(Torneo torneo) {
        torneo.addequipo(this);
    }
}
