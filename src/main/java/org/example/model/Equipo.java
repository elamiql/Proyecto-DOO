package org.example.model;

import java.util.ArrayList;

/**
 * Clase que representa un equipo participante en un torneo.
 * Un equipo está compuesto por una lista de jugadores y se comporta como un {@link Participante}.
 */
public class Equipo extends Participante {

    /** Lista de jugadores que componen el equipo */
    private final ArrayList<Jugador> jugadores;

    /**
     * Constructor que inicializa un equipo con su nombre, número identificador y lista de jugadores.
     * @param nombre    Nombre del equipo.
     * @param numero    Número o identificador del equipo.
     * @param jugadores Lista de jugadores que forman el equipo.
     */
    public Equipo(String nombre, String numero, ArrayList<Jugador> jugadores) {
        super(nombre, numero);
        this.jugadores = jugadores;
    }

    /**
     * Agrega un jugador a la lista del equipo.
     * @param jugador Jugador a agregar.
     */
    public void addJugador(Jugador jugador){
        jugadores.add(jugador);
    }

    /**
     * Elimina un jugador de la lista del equipo.
     * @param jugador Jugador a eliminar.
     */
    public void removeJugador(Jugador jugador){
        jugadores.remove(jugador);
    }

    /**
     * Obtiene la lista de jugadores que pertenecen al equipo.
     * @return Lista de jugadores.
     */
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    /**
     * Inscribe el equipo en el torneo especificado.
     * @param torneo Torneo en el que se desea inscribir el equipo.
     */
    @Override
    public void inscribirse(Torneo<?> torneo) {
        torneo.addParticipante(this);
    }
}
